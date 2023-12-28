package xxt.kareluo.imaging.gallery;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import xxt.kareluo.imaging.XXTIMGGalleryActivity;
import xxt.kareluo.imaging.gallery.model.XXTIMGImageViewModel;

/**
 * Created by felix on 2018/1/4 下午2:26.
 */

public class XXTIMGScanTask extends AsyncTask<Void, List<XXTIMGImageViewModel>, Map<String, List<XXTIMGImageViewModel>>> {

    private WeakReference<XXTIMGGalleryActivity> mActivity;

    public XXTIMGScanTask(XXTIMGGalleryActivity activity) {
        this.mActivity = new WeakReference<>(activity);
    }

    @Override
    protected Map<String, List<XXTIMGImageViewModel>> doInBackground(Void... contexts) {
        if (mActivity != null && mActivity.get() != null) {
            return XXTIMGScanner.getImages14(mActivity.get(), 64, new XXTIMGScanner.Callback() {
                @Override
                public void onImages(List<XXTIMGImageViewModel> images) {
                    publishProgress(images);
                }
            });
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(List<XXTIMGImageViewModel>[] values) {
        if (mActivity != null) {
            XXTIMGGalleryActivity activity = mActivity.get();
            if (activity != null) {
                if (values != null && values.length > 0) {
                    activity.onQuicklyImages(values[0]);
                }
            }
        }
    }

    @Override
    protected void onPostExecute(Map<String, List<XXTIMGImageViewModel>> images) {
        if (mActivity != null) {
            XXTIMGGalleryActivity activity = mActivity.get();
            if (activity != null) {
                activity.onImages(images);
            }
        }
    }
}