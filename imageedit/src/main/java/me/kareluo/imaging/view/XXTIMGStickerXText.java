package me.kareluo.imaging.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import me.kareluo.imaging.core.XXTIMGText;
import me.kareluo.imaging.core.sticker.XXTIMGStickerX;

/**
 * Created by felix on 2017/12/11 下午2:49.
 */

public class XXTIMGStickerXText extends XXTIMGStickerX {

    private XXTIMGText mText;

    private StaticLayout mTextLayout;

    private TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    public XXTIMGStickerXText(XXTIMGText text) {

        mTextPaint.setTextSize(22);

        setText(text);
    }

    public void setText(XXTIMGText text) {
        mText = text;

        mTextPaint.setColor(text.getColor());
        mTextLayout = new StaticLayout(text.getText(), mTextPaint,
                Math.round(Resources.getSystem().getDisplayMetrics().widthPixels * 0.8f),
                Layout.Alignment.ALIGN_NORMAL, 1f, 0, false);

        float width = 0f;
        for (int i = 0; i < mTextLayout.getLineCount(); i++) {
            width = Math.max(width, mTextLayout.getLineWidth(i));
        }

        onMeasure(width, mTextLayout.getHeight());
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mFrame.left, mFrame.top);
        mTextLayout.draw(canvas);
        canvas.restore();
    }
}
