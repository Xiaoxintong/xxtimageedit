package me.kareluo.imaging.core.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by felix on 2017/12/26 下午2:57.
 */

public class XXTIMGAssetFileDecoder extends XXTIMGDecoder {

    private Context mContext;

    public XXTIMGAssetFileDecoder(Context context, String path) {
        super(path);
        mContext = context;
    }

    public Bitmap decode(BitmapFactory.Options options) {

        String path = getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        path = path.substring(1);

        try {
            InputStream iStream = mContext.getAssets().open(path);
            return BitmapFactory.decodeStream(iStream, null, options);
        } catch (IOException ignore) {

        }

        return null;
    }
}
