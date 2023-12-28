package me.kareluo.imaging.core.sticker;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by felix on 2017/11/16 下午5:54.
 */

public interface XXTIMGStickerPortrait {

    boolean show();

    boolean remove();

    boolean dismiss();

    boolean isShowing();

    RectF getFrame();

//    RectF getAdjustFrame();
//
//    RectF getDeleteFrame();

    void onSticker(Canvas canvas);

    void registerCallback(XXTIMGSticker.Callback callback);

    void unregisterCallback(XXTIMGSticker.Callback callback);

    interface Callback {

        <V extends View & XXTIMGSticker> void onDismiss(V stickerView);

        <V extends View & XXTIMGSticker> void onShowing(V stickerView);

        <V extends View & XXTIMGSticker> boolean onRemove(V stickerView);
    }
}
