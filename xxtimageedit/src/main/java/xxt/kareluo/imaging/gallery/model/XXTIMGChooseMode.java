package xxt.kareluo.imaging.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felix on 2018/1/4 下午2:28.
 */

public class XXTIMGChooseMode implements Parcelable {

    private boolean isOriginal = false;

    private boolean isOriginalChangeable = true;

    private boolean isSingleChoose = false;

    private int maxChooseCount = 9;

    public XXTIMGChooseMode() {

    }

    protected XXTIMGChooseMode(Parcel in) {
        isOriginal = in.readByte() != 0;
        isOriginalChangeable = in.readByte() != 0;
        isSingleChoose = in.readByte() != 0;
        maxChooseCount = in.readInt();
    }

    public static final Creator<XXTIMGChooseMode> CREATOR = new Creator<XXTIMGChooseMode>() {
        @Override
        public XXTIMGChooseMode createFromParcel(Parcel in) {
            return new XXTIMGChooseMode(in);
        }

        @Override
        public XXTIMGChooseMode[] newArray(int size) {
            return new XXTIMGChooseMode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isOriginal ? 1 : 0));
        dest.writeByte((byte) (isOriginalChangeable ? 1 : 0));
        dest.writeByte((byte) (isSingleChoose ? 1 : 0));
        dest.writeInt(maxChooseCount);
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public boolean isOriginalChangeable() {
        return isOriginalChangeable;
    }

    public boolean isSingleChoose() {
        return isSingleChoose;
    }

    public int getMaxChooseCount() {
        return maxChooseCount;
    }

    public static class Builder {

        XXTIMGChooseMode mode;

        public Builder() {
            mode = new XXTIMGChooseMode();
        }

        public Builder setOriginal(boolean original) {
            mode.isOriginal = original;
            return this;
        }

        public Builder setOriginalChangeable(boolean originalChangeable) {
            mode.isOriginalChangeable = originalChangeable;
            return this;
        }

        public Builder setSingleChoose(boolean single) {
            mode.isSingleChoose = single;
            if (single) {
                mode.maxChooseCount = 1;
            }
            return this;
        }

        public Builder setMaxChooseCount(int maxChooseCount) {
            mode.maxChooseCount = maxChooseCount;
            if (mode.isSingleChoose) {
                mode.maxChooseCount = Math.min(1, maxChooseCount);
            }
            return this;
        }

        public XXTIMGChooseMode build() {
            return mode;
        }
    }
}
