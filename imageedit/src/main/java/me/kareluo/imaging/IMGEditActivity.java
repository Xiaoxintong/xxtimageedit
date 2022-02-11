package me.kareluo.imaging;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.kareluo.imaging.core.IMGMode;
import me.kareluo.imaging.core.IMGText;
import me.kareluo.imaging.core.file.IMGAssetFileDecoder;
import me.kareluo.imaging.core.file.IMGDecoder;
import me.kareluo.imaging.core.file.IMGFileDecoder;
import me.kareluo.imaging.core.util.IMGUtils;

/**
 * Created by felix on 2017/11/14 下午2:26.
 */

public class IMGEditActivity extends IMGEditBaseActivity {

    private static final int MAX_WIDTH = 1024;

    private static final int MAX_HEIGHT = 1024;

    public static final String EXTRA_IMAGE_PATH = "IMAGE_PATH";

    public static final String EXTRA_IMAGE_SAVE_PATH = "IMAGE_SAVE_PATH";

    public static final String EXTRA_HOMEWORK_CORRECT_BOOLEAN = "EXTRA_HOMEWORK_CORRECT_BOOLEAN";

    public static IMGEditActivityListener imgEditActivityListener;

    /** 作业批改页面，用来实现：点击学生作业的图片，编辑后直接显示到评价输入框中 */
    private boolean homeworkCorrect;

    @Override
    public void onCreated() {


    }

    @Override
    public Bitmap getBitmap() {
        Intent intent = getIntent();

        if (intent == null) {
            return null;
        }


        IMGDecoder decoder = null;

        String path = intent.getExtras().getString(EXTRA_IMAGE_PATH);

        if (!TextUtils.isEmpty(path)) {

            File imageFile = new File(path);
            Uri imageFileUri;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String authority = getApplicationInfo().packageName + ".provider";
                imageFileUri = FileProvider.getUriForFile(this, authority, imageFile);
            } else {
                imageFileUri = Uri.fromFile(imageFile);
            }

            switch (imageFileUri.getScheme()) {
                case "asset":
                    decoder = new IMGAssetFileDecoder(this, path);
                    break;
                default:
                    decoder = new IMGFileDecoder(path);
                    break;
            }
        }

        if (decoder == null) {
            Toast.makeText(this, "decoder是null", Toast.LENGTH_SHORT).show();
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;

        decoder.decode(options);

        if (options.outWidth > MAX_WIDTH) {
            options.inSampleSize = IMGUtils.inSampleSize(Math.round(1f * options.outWidth / MAX_WIDTH));
        }

        if (options.outHeight > MAX_HEIGHT) {
            options.inSampleSize = Math.max(options.inSampleSize,
                    IMGUtils.inSampleSize(Math.round(1f * options.outHeight / MAX_HEIGHT)));
        }

        options.inJustDecodeBounds = false;

        Bitmap bitmap = decoder.decode(options);
        if (bitmap == null) {
            Toast.makeText(this, "bitmap是null", Toast.LENGTH_SHORT).show();
            return null;
        }

        return bitmap;
    }

    @Override
    public void onText(IMGText text) {
        mImgView.addStickerText(text);
    }

    @Override
    public void onModeClick(IMGMode mode) {
        IMGMode cm = mImgView.getMode();
        if (cm == mode) {
            mode = IMGMode.NONE;
        }
        mImgView.setMode(mode);
        updateModeUI();

        if (mode == IMGMode.CLIP) {
            setOpDisplay(OP_CLIP);
        }
    }

    @Override
    public void onUndoClick() {
        IMGMode mode = mImgView.getMode();
        if (mode == IMGMode.DOODLE) {
            mImgView.undoDoodle();
        } else if (mode == IMGMode.MOSAIC) {
            mImgView.undoMosaic();
        }
    }

    @Override
    public void onCancelClick() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onDoneClick() {
        String path = getIntent().getExtras().getString(EXTRA_IMAGE_SAVE_PATH);
        if (!TextUtils.isEmpty(path)) {
            Bitmap bitmap = mImgView.saveBitmap();
            if (bitmap != null) {
                FileOutputStream fout = null;
                try {
                    fout = new FileOutputStream(path);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fout);
                    this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ path)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (fout != null) {
                        try {
                            fout.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                setResult(RESULT_OK);
                finish();
                //调用接口，用以处理作业批改页面业务
                if (null != imgEditActivityListener) {
                    homeworkCorrect = getIntent().getExtras().getBoolean(EXTRA_HOMEWORK_CORRECT_BOOLEAN);
                    if (homeworkCorrect) {
                        imgEditActivityListener.editImgDone(path);
                    }
                }
                return;
            }
        }
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onCancelClipClick() {
        mImgView.cancelClip();
        setOpDisplay(mImgView.getMode() == IMGMode.CLIP ? OP_CLIP : OP_NORMAL);
    }

    @Override
    public void onDoneClipClick() {
        mImgView.doClip();
        setOpDisplay(mImgView.getMode() == IMGMode.CLIP ? OP_CLIP : OP_NORMAL);
    }

    @Override
    public void onResetClipClick() {
        mImgView.resetClip();
    }

    @Override
    public void onRotateClipClick() {
        mImgView.doRotate();
    }

    @Override
    public void onColorChanged(int checkedColor) {
        mImgView.setPenColor(checkedColor);
    }

    public interface IMGEditActivityListener {
        void editImgDone(String imgPath);
    }
}
