package xxt.kareluo.imaging.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import xxt.kareluo.imaging.XXTIMGTextEditDialog;
import xxt.kareluo.imaging.core.XXTIMGText;

/**
 * Created by felix on 2017/11/14 下午7:27.
 */
public class XXTIMGStickerTextView extends XXTIMGStickerView implements XXTIMGTextEditDialog.Callback {

    private static final String TAG = "IMGStickerTextView";

    private TextView mTextView;

    private XXTIMGText mText;

    private XXTIMGTextEditDialog mDialog;

    private static float mBaseTextSize = 22f;

    private static final int PADDING = 26;

    public XXTIMGStickerTextView(Context context) {
        this(context, null, 0);
    }

    public XXTIMGStickerTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XXTIMGStickerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onInitialize(Context context) {
        super.onInitialize(context);
    }

    @Override
    public View onCreateContentView(Context context) {
        mTextView = new TextView(context);
        mTextView.setTextSize(mBaseTextSize);
        mTextView.setPadding(PADDING, PADDING, PADDING, PADDING);
        mTextView.setTextColor(Color.RED);

        return mTextView;
    }

    public void setText(XXTIMGText text) {
        mText = text;
        if (mText != null && mTextView != null) {
            mTextView.setText(mText.getText());
            mTextView.setTextColor(mText.getColor());
        }
    }

    public XXTIMGText getText() {
        return mText;
    }

    @Override
    public void onContentTap() {
        XXTIMGTextEditDialog dialog = getDialog();
        dialog.setText(mText);
        dialog.show();
    }

    private XXTIMGTextEditDialog getDialog() {
        if (mDialog == null) {
            mDialog = new XXTIMGTextEditDialog(getContext(), this);
        }
        return mDialog;
    }

    @Override
    public void onText(XXTIMGText text) {
        mText = text;
        if (mText != null && mTextView != null) {
            mTextView.setText(mText.getText());
            mTextView.setTextColor(mText.getColor());
        }
    }
}