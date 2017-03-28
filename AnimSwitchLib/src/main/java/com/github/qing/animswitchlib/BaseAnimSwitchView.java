package com.github.qing.animswitchlib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.github.qing.animswitchlib.anim.BaseAnimator;
import com.github.qing.animswitchlib.anim.DownTransAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcq on 2017/3/27.
 * <p>
 * 可切换的view抽象父类
 */

@SuppressWarnings("unchecked")
public abstract class BaseAnimSwitchView<E, T extends View, S extends BaseAnimSwitchView> extends FrameLayout {
    private static final int DEFAULT_SIZE = 2;
    private static final int DEFAULT_DURATION = 500;
    private List<T> container = new ArrayList<>();
    private T currentView, nextView;
    private int duration = DEFAULT_DURATION;
    protected int delayed;
    private boolean isAnimating = false;
    protected BaseAnimator switchAnimator;
    protected E currentData;
    private OnItemClickListener itemClickListener;

    public BaseAnimSwitchView(@NonNull Context context) {
        this(context, null);
    }

    public BaseAnimSwitchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseAnimSwitchView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onObtainAttrs(context, attrs, defStyleAttr);
        init();
    }

    protected void onObtainAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
    }

    private void init() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            T itemView = createNewItemView();
            itemView.setVisibility(INVISIBLE);
            container.add(itemView);
            addView(itemView, itemView.getLayoutParams());
        }
        container.get(0).setVisibility(VISIBLE);

        setSwitchAnimator(new DownTransAnimator());

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(currentData, v);
                }
            }
        });
    }


    protected T findCurrent() {
        for (T tv : container) {
            if (tv.getVisibility() == VISIBLE) {
                return tv;
            }
        }
        return null;
    }

    protected T findNext() {
        for (T tv : container) {
            if (tv.getVisibility() == INVISIBLE) {
                return tv;
            }
        }
        return null;
    }

    public S setDuration(int duration) {
        this.duration = duration;
        return (S) this;
    }

    public S setDelayed(int delayed) {
        this.delayed = delayed;
        return (S) this;
    }

    public S changeData(E data) {
        T next = findNext();
        if (next != null) {
            currentData = data;
            bindItemView(data, next);
            animSwitch();
        }
        return (S) this;
    }

    protected abstract T createNewItemView();

    protected abstract void bindItemView(E data, T view);

    public S setSwitchAnimator(BaseAnimator animator) {
        this.switchAnimator = animator;
        this.switchAnimator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentView.setVisibility(INVISIBLE);
                isAnimating = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                nextView.setVisibility(VISIBLE);
            }
        });
        return (S) this;
    }

    private void animSwitch() {
        if (isAnimating) {
            return;
        }
        currentView = findCurrent();
        nextView = findNext();
        if (currentView == null || nextView == null) {
            return;
        }
        isAnimating = true;

        // start anim
        Rect rect = new Rect(getLeft(), getTop(), getRight(), getBottom());
        System.out.println("rect width:" + rect.width() + "\t height:" + rect.height());
        switchAnimator
                .setDelay(delayed)
                .setDuration(duration)
                .playOn(currentView, nextView, rect);
    }

    public interface OnItemClickListener<E> {
        void onItemClick(E data, View view);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
