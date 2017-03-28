package com.github.qing.animswitchlib.anim;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.view.View;


public class FadeInFadeOutAnimator extends BaseAnimator {
    @Override
    public void setAnimator(View currentView, View nextView, Rect rect) {

        ObjectAnimator currentAlphaAnim = ObjectAnimator.ofFloat(currentView, "alpha", 1f, 0f);
        ObjectAnimator nextAlphaAnim = ObjectAnimator.ofFloat(nextView, "alpha", 0f, 1f);

        ViewCompat.setTranslationY(nextView, 0);
        ViewCompat.setTranslationX(nextView, 0);
        ViewCompat.setAlpha(nextView, 0);
        this.mAnimatorSet.play(nextAlphaAnim).after(100).after(currentAlphaAnim);
    }
}
