package com.github.qing.animswitchlib.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.Interpolator;

public abstract class BaseAnimator {

    private long duration = 500;
    AnimatorSet mAnimatorSet;
    private Interpolator interpolator;
    private long delay;
    private Animator.AnimatorListener listener;

    public abstract void setAnimator(View currentView, View nextView, Rect rect);

    public void playOn(View currentView, View nextView, Rect rect) {
        mAnimatorSet = new AnimatorSet();
        setAnimator(currentView, nextView, rect);
        mAnimatorSet.setDuration(duration);
        if (interpolator != null) {
            mAnimatorSet.setInterpolator(interpolator);
        }
        if (delay > 0) {
            mAnimatorSet.setStartDelay(delay);
        }
        if (listener != null) {
            mAnimatorSet.addListener(listener);
        }
        mAnimatorSet.start();
    }

    public BaseAnimator setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public AnimatorSet getmAnimatorSet() {
        return mAnimatorSet;
    }

    public BaseAnimator setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public BaseAnimator setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public BaseAnimator setListener(Animator.AnimatorListener listener) {
        this.listener = listener;
        return this;
    }

    public void reset(View view) {
        ViewCompat.setAlpha(view, 1);
        ViewCompat.setTranslationX(view, 0);
        ViewCompat.setTranslationY(view, 0);
        ViewCompat.setRotation(view, 0);
        ViewCompat.setRotationX(view, 0);
        ViewCompat.setRotationY(view, 0);
    }
}
