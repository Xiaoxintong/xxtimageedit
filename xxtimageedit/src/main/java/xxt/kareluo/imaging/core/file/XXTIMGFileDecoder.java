package xxt.kareluo.imaging.core.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by felix on 2017/12/26 下午3:07.
 */

public class XXTIMGFileDecoder extends XXTIMGDecoder {

    public XXTIMGFileDecoder(String path) {
        super(path);
    }

    @Override
    public Bitmap decode(BitmapFactory.Options options) {
        String path = getPath();

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        File file = new File(path);
        if (file.exists()) {
            return BitmapFactory.decodeFile(path, options);
        }

        return null;
    }
}
