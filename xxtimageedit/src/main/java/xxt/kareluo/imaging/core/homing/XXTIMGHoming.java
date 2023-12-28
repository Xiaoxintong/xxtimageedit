package xxt.kareluo.imaging.core.homing;

/**
 * Created by felix on 2017/11/28 下午4:14.
 */

public class XXTIMGHoming {

    public float x, y;

    public float scale;

    public float rotate;

    public XXTIMGHoming(float x, float y, float scale, float rotate) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.rotate = rotate;
    }

    public void set(float x, float y, float scale, float rotate) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.rotate = rotate;
    }

    public void concat(XXTIMGHoming homing) {
        this.scale *= homing.scale;
        this.x += homing.x;
        this.y += homing.y;
    }

    public void rConcat(XXTIMGHoming homing) {
        this.scale *= homing.scale;
        this.x -= homing.x;
        this.y -= homing.y;
    }

    public static boolean isRotate(XXTIMGHoming sHoming, XXTIMGHoming eHoming) {
        return Float.compare(sHoming.rotate, eHoming.rotate) != 0;
    }

    @Override
    public String toString() {
        return "IMGHoming{" +
                "x=" + x +
                ", y=" + y +
                ", scale=" + scale +
                ", rotate=" + rotate +
                '}';
    }
}
