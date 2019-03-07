package me.tmgg.viewsdemoapp.transition;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.ViewGroup;

/**
 * @author sunwei
 * 邮箱：tianmu19@gmail.com
 * 时间：2019/3/6 15:11
 * 包名：me.tmgg.viewsdemoapp.transition
 * <p>description:  边角、颜色变化位移          </p>
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ChangeRectRadiusAndColorTransition extends Transition {
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues values) {



    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {



        return null;
    }
}
