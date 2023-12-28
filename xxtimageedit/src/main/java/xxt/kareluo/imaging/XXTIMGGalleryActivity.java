package xxt.kareluo.imaging;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import xxt.kareluo.imaging.gallery.XXTIMGGalleryMenuWindow;
import xxt.kareluo.imaging.gallery.XXTIMGScanTask;
import xxt.kareluo.imaging.gallery.XXTIMGScanner;
import xxt.kareluo.imaging.gallery.model.XXTIMGChooseMode;
import xxt.kareluo.imaging.gallery.model.XXTIMGImageInfo;
import xxt.kareluo.imaging.gallery.model.XXTIMGImageViewModel;
import xxt.kareluo.imaging.widget.XXTIMGGalleryHolderCallback;

/**
 * Created by felix on 2017/11/14 上午11:30.
 */

public class XXTIMGGalleryActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private XXTIMGChooseMode mGalleryMode;

    private TextView mAlbumFolderView;

    private View mFooterView;

    private XXTIMGGalleryMenuWindow mGalleryMenuWindow;

    private Map<String, List<XXTIMGImageViewModel>> mImages;

    private List<XXTIMGImageViewModel> mChooseImages = new ArrayList<>();

    private static final String EXTRA_IMAGES = "IMAGES";

    private static final String EXTRA_CHOOSE_MODE = "CHOOSE_MODE";

    private static final int[] ATTRS = new int[]{
            R.attr.image_gallery_span_count,
            R.attr.image_gallery_select_shade
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xxt_image_gallery_activity);

        mGalleryMode = getIntent().getParcelableExtra(EXTRA_CHOOSE_MODE);
        if (mGalleryMode == null) {
            mGalleryMode = new XXTIMGChooseMode();
        }

        mRecyclerView = findViewById(R.id.rv_images);
        mRecyclerView.setAdapter(mAdapter = new ImageAdapter());

        new XXTIMGScanTask(this).execute();

        mFooterView = findViewById(R.id.layout_footer);

        mAlbumFolderView = findViewById(R.id.tv_album_folder);
        mAlbumFolderView.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.xxt_image_menu_gallery, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.image_menu_done) {
            onDone();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onImages(Map<String, List<XXTIMGImageViewModel>> images) {
        mImages = images;
        if (images != null) {
            mAdapter.setModels(images.get(XXTIMGScanner.ALL_IMAGES));
            mAdapter.notifyDataSetChanged();

            XXTIMGGalleryMenuWindow window = getGalleryMenuWindow();
            Set<String> keys = images.keySet();
            List<String> items = new ArrayList<>(keys);
            if (!items.isEmpty() && !XXTIMGScanner.ALL_IMAGES.equals(items.get(0))) {
                items.remove(XXTIMGScanner.ALL_IMAGES);
                items.add(0, XXTIMGScanner.ALL_IMAGES);
            }
            window.setMenuItems(items);
        }
    }

    public void onQuicklyImages(List<XXTIMGImageViewModel> images) {
        mAdapter.setModels(images);
        mAdapter.notifyDataSetChanged();
    }

    private void onImageCheckClick(int position) {
        XXTIMGImageViewModel item = mAdapter.getItem(position);
        if (item != null) {
            if (!item.isSelected()) {
                if (mChooseImages.size() >= mGalleryMode.getMaxChooseCount()) {
                    // TODO 达到最大限度
                    mAdapter.notifyItemChanged(position, true);
                    return;
                }
            }

            item.toggleSelected();
            if (item.isSelected()) {
                mChooseImages.add(item);
            } else {
                mChooseImages.remove(item);
            }

            mAdapter.notifyItemChanged(position, true);
        }
    }

    private void onImageClick(int position) {
        XXTIMGImageViewModel item = mAdapter.getItem(position);
        if (item != null) {
            if (mGalleryMode.isSingleChoose()) {
                mChooseImages.clear();
                item.setSelected(true);
                mChooseImages.add(item);
                onDone();
            }
        }
    }

    private void onDone() {
        ArrayList<XXTIMGImageInfo> infos = new ArrayList<>();
        for (XXTIMGImageViewModel model : mChooseImages) {
            infos.add(new XXTIMGImageInfo(model));
        }
        setResult(RESULT_OK, new Intent().putParcelableArrayListExtra(EXTRA_IMAGES, infos));
        finish();
    }

    private XXTIMGGalleryMenuWindow getGalleryMenuWindow() {
        if (mGalleryMenuWindow == null) {
            mGalleryMenuWindow = new XXTIMGGalleryMenuWindow(this);
        }
        return mGalleryMenuWindow;
    }

    public static ArrayList<XXTIMGImageInfo> getImageInfos(Intent intent) {
        if (intent != null) {
            return intent.getParcelableArrayListExtra(EXTRA_IMAGES);
        }
        return null;
    }

    public static Intent newIntent(Context context, XXTIMGChooseMode mode) {
        return new Intent(context, XXTIMGGalleryActivity.class)
                .putExtra(EXTRA_CHOOSE_MODE, mode);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_album_folder) {
            showGalleryMenu();
        }
    }

    private void showGalleryMenu() {
        XXTIMGGalleryMenuWindow window = getGalleryMenuWindow();
        if (window != null) {
            window.show(mFooterView);
        }
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder>
            implements XXTIMGGalleryHolderCallback {

        private List<XXTIMGImageViewModel> models;

        private void setModels(List<XXTIMGImageViewModel> models) {
            this.models = models;
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(getLayoutInflater().inflate(
                    R.layout.xxt_image_layout_image, parent, false), this);
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            holder.update(models.get(position), mGalleryMode);
        }

        @Override
        public int getItemCount() {
            return models != null ? models.size() : 0;
        }

        private XXTIMGImageViewModel getItem(int position) {
            if (position >= 0 && position < getItemCount()) {
                return models.get(position);
            }
            return null;
        }

        @Override
        public void onViewHolderClick(RecyclerView.ViewHolder holder) {
            onImageClick(holder.getAdapterPosition());
        }

        @Override
        public void onCheckClick(RecyclerView.ViewHolder holder) {
            onImageCheckClick(holder.getAdapterPosition());
        }
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private CheckBox mCheckBox;

        private SimpleDraweeView mImageView;

        private XXTIMGGalleryHolderCallback mCallback;

        private static Drawable FORE_DRAWABLE = null;

        private ImageViewHolder(View itemView, XXTIMGGalleryHolderCallback callback) {
            super(itemView);
            mCallback = callback;

            mCheckBox = itemView.findViewById(R.id.cb_box);
            mImageView = itemView.findViewById(R.id.sdv_image);

            mCheckBox.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        private void update(XXTIMGImageViewModel model, XXTIMGChooseMode mode) {
            mCheckBox.setChecked(model.isSelected());
            mCheckBox.setVisibility(mode.isSingleChoose() ? View.GONE : View.VISIBLE);

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(model.getUri())
                    .setLocalThumbnailPreviewsEnabled(true)
                    .setResizeOptions(new ResizeOptions(300, 300))
                    .setRotationOptions(RotationOptions.autoRotate())
                    .build();

            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(mImageView.getController())
                    .setImageRequest(request)
                    .build();

            mImageView.setController(controller);
        }

        @Override
        public void onClick(View v) {
            if (mCallback != null) {
                if (v.getId() == R.id.cb_box) {
                    mCallback.onCheckClick(this);
                } else {
                    mCallback.onViewHolderClick(this);
                }
            }
        }
    }
}
