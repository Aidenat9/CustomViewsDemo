package me.tmgg.viewsdemoapp.widgets.evaluator;

import android.animation.TypeEvaluator;

import me.tmgg.viewsdemoapp.widgets.RadiusColorObj;

/**
 * @author sunwei
 * 邮箱：tianmu19@gmail.com
 * 时间：2019/3/6 16:27
 * 包名：me.tmgg.viewsdemoapp.widgets.evaluator
 * <p>description:            </p>
 */
public class RadiusColorEvalutor implements TypeEvaluator<RadiusColorObj> {
    RadiusColorObj radiusColorObj = new RadiusColorObj();
    @Override
    public RadiusColorObj evaluate(float fraction, RadiusColorObj startValue, RadiusColorObj endValue) {

        radiusColorObj.radius = startValue.radius+fraction*(endValue.radius-startValue.radius);


        return radiusColorObj;
    }
}
