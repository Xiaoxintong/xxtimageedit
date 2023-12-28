package xxt.kareluo.imaging.core.elastic;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by felix on 2017/11/27 下午5:22.
 */

public class XXTIMGElasticAnimator extends ValueAnimator {

    private XXTIMGElastic mElastic;

    public XXTIMGElasticAnimator() {
        setEvaluator(new XXTIMGPointFEvaluator());
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public XXTIMGElasticAnimator(XXTIMGElastic elastic) {
        this();
        setElastic(elastic);
    }

    public void setElastic(XXTIMGElastic elastic) {
        mElastic = elastic;

        if (mElastic == null) {
            throw new IllegalArgumentException("IMGElastic cannot be null.");
        }
    }

    public void start(float x, float y) {
        setObjectValues(new PointF(x, y), mElastic.getPivot());
        start();
    }
}
