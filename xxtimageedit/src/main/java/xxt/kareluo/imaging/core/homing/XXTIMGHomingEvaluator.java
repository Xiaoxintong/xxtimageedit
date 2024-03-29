package xxt.kareluo.imaging.core.homing;

import android.animation.TypeEvaluator;

/**
 * Created by felix on 2017/11/28 下午4:13.
 */

public class XXTIMGHomingEvaluator implements TypeEvaluator<XXTIMGHoming> {

    private XXTIMGHoming homing;

    public XXTIMGHomingEvaluator() {

    }

    public XXTIMGHomingEvaluator(XXTIMGHoming homing) {
        this.homing = homing;
    }

    @Override
    public XXTIMGHoming evaluate(float fraction, XXTIMGHoming startValue, XXTIMGHoming endValue) {
        float x = startValue.x + fraction * (endValue.x - startValue.x);
        float y = startValue.y + fraction * (endValue.y - startValue.y);
        float scale = startValue.scale + fraction * (endValue.scale - startValue.scale);
        float rotate = startValue.rotate + fraction * (endValue.rotate - startValue.rotate);

        if (homing == null) {
            homing = new XXTIMGHoming(x, y, scale, rotate);
        } else {
            homing.set(x, y, scale, rotate);
        }

        return homing;
    }
}
