package xxt.kareluo.imaging.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import xxt.kareluo.imaging.R;

/**
 * Created by felix on 2017/12/21 下午10:58.
 */

public class XXTIMGStickerImageView extends XXTIMGStickerView {

    private ImageView mImageView;

    public XXTIMGStickerImageView(Context context) {
        super(context);
    }

    public XXTIMGStickerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XXTIMGStickerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View onCreateContentView(Context context) {
        mImageView = new ImageView(context);
        mImageView.setImageResource(R.mipmap.ic_launcher);
        return mImageView;
    }
}
