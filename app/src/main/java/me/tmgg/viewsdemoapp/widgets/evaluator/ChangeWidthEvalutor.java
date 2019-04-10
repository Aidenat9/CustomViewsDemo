package me.tmgg.viewsdemoapp.widgets.evaluator;

import android.animation.TypeEvaluator;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/4/10 16:42
 * package：me.tmgg.viewsdemoapp.widgets.evaluator
 * version：1.0
 * <p>description：              </p>
 */
public class ChangeWidthEvalutor implements TypeEvaluator<Float> {
    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {
        float v = (endValue - startValue) * fraction;
        return startValue+v;
    }
}
