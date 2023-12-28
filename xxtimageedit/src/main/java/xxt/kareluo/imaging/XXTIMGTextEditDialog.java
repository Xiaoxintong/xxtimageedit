package xxt.kareluo.imaging;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.RadioGroup;

import xxt.kareluo.imaging.core.XXTIMGText;
import xxt.kareluo.imaging.view.XXTIMGColorGroup;

/**
 * Created by felix on 2017/12/1 上午11:21.
 */

public class XXTIMGTextEditDialog extends Dialog implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "IMGTextEditDialog";

    private EditText mEditText;

    private Callback mCallback;

    private XXTIMGText mDefaultText;

    private XXTIMGColorGroup mColorGroup;

    private Context context;

    public XXTIMGTextEditDialog(Context context, Callback callback) {
        super(context, R.style.XXTImageTextDialog);
        setContentView(R.layout.xxt_image_text_dialog);
        mCallback = callback;
        this.context = context;
        Window window = getWindow();
        if (window != null) {
            window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mColorGroup = findViewById(R.id.xxt_cg_colors);
        mColorGroup.setOnCheckedChangeListener(this);
        mEditText = findViewById(R.id.et_text);

        mEditText.setTextSize(22);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_done).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mDefaultText != null) {
            mEditText.setText(mDefaultText.getText());
            mEditText.setTextColor(mDefaultText.getColor());
            if (!mDefaultText.isEmpty()) {
                mEditText.setSelection(mEditText.length());
            }
            mDefaultText = null;
        } else  {
            mEditText.setText("");
        }

        // 设置默认红色
        mColorGroup.setCheckColor(Color.RED);
    }

    public void setText(XXTIMGText text) {
        mDefaultText = text;
    }

    public void reset() {
        setText(new XXTIMGText(null, Color.RED));
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        if (vid == R.id.tv_done) {
            onDone();
        } else if (vid == R.id.tv_cancel) {
            dismiss();
        }
    }

    private void onDone() {
        String text = mEditText.getText().toString();
        if (!TextUtils.isEmpty(text) && mCallback != null) {
            mCallback.onText(new XXTIMGText(text, mEditText.getCurrentTextColor()));
        }
        dismiss();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        mEditText.setTextColor(mColorGroup.getCheckColor());
    }

    public interface Callback {

        void onText(XXTIMGText text);
    }
}
