package me.tmgg.viewsdemoapp.widgets.evaluator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/4/10 21:51
 * version：1.0
 * <p>description：
 *  * 贝塞尔曲线（二阶抛物线）
 *  * controllPoint 是中间的转折点
 *  * startValue 是起始的位置
 *  * endValue 是结束的位置
 *  *   </p>
 */

public class ParabolaEvaluator implements TypeEvaluator<PointF> {
    private PointF controllPoint;

    public ParabolaEvaluator(PointF controllPoint) {
        this.controllPoint = controllPoint;
    }
    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        //只要能保证：当fraction=0时返回值为startValue，并且当fraction=1时返回值为endValue，就是一个比较合理的函数
        PointF pointF = new PointF();
//        pointF.x = startValue.x + fraction * (endValue.x - startValue.x);// x方向匀速移动
//        pointF.y = startValue.y + fraction * fraction * (endValue.y - startValue.y);// y方向抛物线加速移动
        int x = (int) ((1 - fraction) * (1 - fraction) * startValue.x + 2 * fraction * (1 - fraction) * controllPoint.x + fraction * fraction * endValue.x);
        int y = (int) ((1 - fraction) * (1 - fraction) * startValue.y + 2 * fraction * (1 - fraction) * controllPoint.y + fraction * fraction * endValue.y);
        pointF.x = x;
        pointF.y = y;
        return pointF;
    }
}