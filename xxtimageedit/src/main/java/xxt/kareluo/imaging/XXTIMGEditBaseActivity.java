package xxt.kareluo.imaging;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.ViewSwitcher;

import xxt.kareluo.imaging.core.XXTIMGMode;
import xxt.kareluo.imaging.core.XXTIMGText;
import xxt.kareluo.imaging.view.XXTIMGColorGroup;
import xxt.kareluo.imaging.view.XXTIMGView;

/**
 * Created by felix on 2017/12/5 下午3:08.
 */

abstract class XXTIMGEditBaseActivity extends Activity implements View.OnClickListener,
        XXTIMGTextEditDialog.Callback, RadioGroup.OnCheckedChangeListener,
        DialogInterface.OnShowListener, DialogInterface.OnDismissListener {

    protected XXTIMGView mXXTIMGView;

    private RadioGroup mModeGroup;

    private XXTIMGColorGroup mColorGroup;

    private XXTIMGTextEditDialog mTextDialog;

    private View mLayoutOpSub;

    private ViewSwitcher mOpSwitcher, mOpSubSwitcher;

    public static final int OP_HIDE = -1;

    public static final int OP_NORMAL = 0;

    public static final int OP_CLIP = 1;

    public static final int OP_SUB_DOODLE = 0;

    public static final int OP_SUB_MOSAIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bitmap bitmap = getBitmap();
        if (bitmap != null) {
            setContentView(R.layout.xxt_image_edit_activity);
            initViews();
            mXXTIMGView.setImageBitmap(bitmap);
            onCreated();
//            //  默认涂鸦
//            onModeClick(IMGMode.DOODLE);
        } else finish();
    }

    public void onCreated() {

    }

    private void initViews() {
        mXXTIMGView = findViewById(R.id.xxt_image_canvas);
        mModeGroup = findViewById(R.id.xxt_rg_modes);

        mOpSwitcher = findViewById(R.id.xxt_vs_op);
        mOpSubSwitcher = findViewById(R.id.xxt_vs_op_sub);

        mColorGroup = findViewById(R.id.xxt_cg_colors);
        mColorGroup.setOnCheckedChangeListener(this);

        mLayoutOpSub = findViewById(R.id.xxt_layout_op_sub);

        updateModeUI();
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        if (vid == R.id.rb_doodle) {
            onModeClick(XXTIMGMode.DOODLE);
        } else if (vid == R.id.btn_text) {
            onTextModeClick();
        } else if (vid == R.id.rb_mosaic) {
            onModeClick(XXTIMGMode.MOSAIC);
        } else if (vid == R.id.btn_clip) {
            onModeClick(XXTIMGMode.CLIP);
        } else if (vid == R.id.btn_undo) {
            onUndoClick();
        } else if (vid == R.id.tv_done) {
            onDoneClick();
        } else if (vid == R.id.tv_cancel) {
            onCancelClick();
        } else if (vid == R.id.ib_clip_cancel) {
            onCancelClipClick();
        } else if (vid == R.id.ib_clip_done) {
            onDoneClipClick();
        } else if (vid == R.id.tv_clip_reset) {
            onResetClipClick();
        } else if (vid == R.id.ib_clip_rotate) {
            onRotateClipClick();
        }
    }

    public void updateModeUI() {
        XXTIMGMode mode = mXXTIMGView.getMode();
        switch (mode) {
            case DOODLE:
                mModeGroup.check(R.id.rb_doodle);
                setOpSubDisplay(OP_SUB_DOODLE);
                break;
            case MOSAIC:
                mModeGroup.check(R.id.rb_mosaic);
                setOpSubDisplay(OP_SUB_MOSAIC);
                break;
            case NONE:
                mModeGroup.clearCheck();
                setOpSubDisplay(OP_HIDE);
                break;
        }
    }

    public void onTextModeClick() {
        if (mTextDialog == null) {
            mTextDialog = new XXTIMGTextEditDialog(this, this);
            mTextDialog.setOnShowListener(this);
            mTextDialog.setOnDismissListener(this);
        }
        mTextDialog.show();
    }

    @Override
    public final void onCheckedChanged(RadioGroup group, int checkedId) {
        onColorChanged(mColorGroup.getCheckColor());
    }

    public void setOpDisplay(int op) {
        if (op >= 0) {
            mOpSwitcher.setDisplayedChild(op);
        }
    }

    public void setOpSubDisplay(int opSub) {
        if (opSub < 0) {
            mLayoutOpSub.setVisibility(View.GONE);
        } else {
            mOpSubSwitcher.setDisplayedChild(opSub);
            mLayoutOpSub.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        mOpSwitcher.setVisibility(View.GONE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mOpSwitcher.setVisibility(View.VISIBLE);
    }

    public abstract Bitmap getBitmap();

    public abstract void onModeClick(XXTIMGMode mode);

    public abstract void onUndoClick();

    public abstract void onCancelClick();

    public abstract void onDoneClick();

    public abstract void onCancelClipClick();

    public abstract void onDoneClipClick();

    public abstract void onResetClipClick();

    public abstract void onRotateClipClick();

    public abstract void onColorChanged(int checkedColor);

    @Override
    public abstract void onText(XXTIMGText text);
}
