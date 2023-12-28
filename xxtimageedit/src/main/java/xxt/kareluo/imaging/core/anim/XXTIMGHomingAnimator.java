package xxt.kareluo.imaging.core.anim;

import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

import xxt.kareluo.imaging.core.homing.XXTIMGHoming;
import xxt.kareluo.imaging.core.homing.XXTIMGHomingEvaluator;

/**
 * Created by felix on 2017/11/28 下午12:54.
 */

public class XXTIMGHomingAnimator extends ValueAnimator {

    private boolean isRotate = false;

    private XXTIMGHomingEvaluator mEvaluator;

    public XXTIMGHomingAnimator() {
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override
    public void setObjectValues(Object... values) {
        super.setObjectValues(values);
        if (mEvaluator == null) {
            mEvaluator = new XXTIMGHomingEvaluator();
        }
        setEvaluator(mEvaluator);
    }

    public void setHomingValues(XXTIMGHoming sHoming, XXTIMGHoming eHoming) {
        setObjectValues(sHoming, eHoming);
        isRotate = XXTIMGHoming.isRotate(sHoming, eHoming);
    }

    public boolean isRotate() {
        return isRotate;
    }
}
