package me.kareluo.imaging.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by felix on 2017/11/22 下午6:13.
 */

public class XXTIMGPath {

    protected Path path;

    private int color = Color.YELLOW;

    private float width = BASE_MOSAIC_WIDTH;

    private XXTIMGMode mode = XXTIMGMode.DOODLE;

    public static final float BASE_DOODLE_WIDTH = 8f;

    public static final float BASE_MOSAIC_WIDTH = 36f;

    public XXTIMGPath() {
        this(new Path());
    }

    public XXTIMGPath(Path path) {
        this(path, XXTIMGMode.DOODLE);
    }

    public XXTIMGPath(Path path, XXTIMGMode mode) {
        this(path, mode, Color.RED);
    }

    public XXTIMGPath(Path path, XXTIMGMode mode, int color) {
        this(path, mode, color, BASE_MOSAIC_WIDTH);
    }

    public XXTIMGPath(Path path, XXTIMGMode mode, int color, float width) {
        this.path = path;
        this.mode = mode;
        this.color = color;
        this.width = width;
        if (mode == XXTIMGMode.MOSAIC) {
            path.setFillType(Path.FillType.EVEN_ODD);
        }
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public XXTIMGMode getMode() {
        return mode;
    }

    public void setMode(XXTIMGMode mode) {
        this.mode = mode;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return width;
    }

    public void onDrawDoodle(Canvas canvas, Paint paint) {
        if (mode == XXTIMGMode.DOODLE) {
            paint.setColor(color);
            paint.setStrokeWidth(BASE_DOODLE_WIDTH);
            // rewind
            canvas.drawPath(path, paint);
        }
    }

    public void onDrawMosaic(Canvas canvas, Paint paint) {
        if (mode == XXTIMGMode.MOSAIC) {
            paint.setStrokeWidth(width);
            canvas.drawPath(path, paint);
        }
    }

    public void transform(Matrix matrix) {
        path.transform(matrix);
    }
}
