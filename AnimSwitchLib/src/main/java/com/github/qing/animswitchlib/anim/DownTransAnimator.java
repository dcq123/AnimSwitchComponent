package com.github.qing.animswitchlib.anim;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by dcq on 2017/3/28.
 * <p>
 * 向下切换View动画
 */

public class DownTransAnimator extends BaseAnimator {

    @Override
    public void setAnimator(View currentView, View nextView, Rect rect) {

        final int height = rect.height();

        ObjectAnimator currentTransAnim = ObjectAnimator.ofFloat(currentView, "translationY", 0f, height);
        ObjectAnimator currentAlphaAnim = ObjectAnimator.ofFloat(currentView, "alpha", 1f, 0.5f);

        ViewCompat.setTranslationY(nextView, -height);
        ViewCompat.setTranslationX(nextView, 0);
        ObjectAnimator nextTransAnim = ObjectAnimator.ofFloat(nextView, "translationY", -height, 0);
        ObjectAnimator nextAlphaAnim = ObjectAnimator.ofFloat(nextView, "alpha", 0.5f, 1f);

        this.mAnimatorSet.playTogether(currentTransAnim, currentAlphaAnim, nextTransAnim, nextAlphaAnim);
    }


}
