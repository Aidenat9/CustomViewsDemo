package me.tmgg.viewsdemoapp.widgets.evaluator;

import android.animation.TypeEvaluator;

import me.tmgg.viewsdemoapp.bean.Point;

/**
 * @author sunwei
 * 邮箱：tianmu19@gmail.com
 * 时间：2019/3/6 13:46
 * 包名：me.tmgg.viewsdemoapp.widgets.evaluator
 * <p>description:            </p>
 */
public class ScaleAlhpaExecutor implements TypeEvaluator<Point> {
    Point point = new Point();
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        point.scale = startValue.scale+fraction*(endValue.scale-startValue.scale);
        point.alpha = ((startValue.alpha)+fraction*(endValue.alpha-startValue.alpha));
        return point;
    }
}
