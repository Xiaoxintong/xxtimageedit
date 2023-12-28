package xxt.kareluo.imaging.core.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by felix on 2017/12/26 下午2:54.
 */

public abstract class XXTIMGDecoder {

    private String path;


    public XXTIMGDecoder(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap decode() {
        return decode(null);
    }

    public abstract Bitmap decode(BitmapFactory.Options options);

}
