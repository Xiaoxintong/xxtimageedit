package xxt.kareluo.imaging.gallery.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felix on 2017/12/26 上午11:09.
 */

public class XXTIMGImageInfo implements Parcelable {

    private long size;

    private int width;

    private int height;

    private boolean isOriginal;

    private boolean isEdited;

    private Uri uri;

    protected XXTIMGImageInfo(Parcel in) {
        size = in.readLong();
        width = in.readInt();
        height = in.readInt();
        isOriginal = in.readByte() != 0;
        isEdited = in.readByte() != 0;
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public XXTIMGImageInfo(xxt.kareluo.imaging.gallery.model.XXTIMGImageViewModel model) {
        this.size = model.getSize();
        this.width = model.getWidth();
        this.height = model.getHeight();
        this.isOriginal = model.isOriginal();
        this.isEdited = false;
        this.uri = model.getUri();
    }

    public static final Creator<XXTIMGImageInfo> CREATOR = new Creator<XXTIMGImageInfo>() {
        @Override
        public XXTIMGImageInfo createFromParcel(Parcel in) {
            return new XXTIMGImageInfo(in);
        }

        @Override
        public XXTIMGImageInfo[] newArray(int size) {
            return new XXTIMGImageInfo[size];
        }
    };

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(size);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeByte((byte) (isOriginal ? 1 : 0));
        dest.writeByte((byte) (isEdited ? 1 : 0));
        dest.writeParcelable(uri, flags);
    }
}
