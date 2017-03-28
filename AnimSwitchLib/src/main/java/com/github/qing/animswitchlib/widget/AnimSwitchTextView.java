package com.github.qing.animswitchlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.qing.animswitchlib.BaseAnimSwitchView;
import com.github.qing.animswitchlib.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcq on 2017/3/27.
 * <p>
 * 进行文本切换的TextView容器
 */

public class AnimSwitchTextView extends BaseAnimSwitchView<String, TextView, AnimSwitchTextView> {

    private static final int WHAT = 0x001;
    private int textColor;
    private int textSize;
    private int textPadding;
    private static SwitchHandler handler;
    private List<String> data = new ArrayList<>();
    private int position = 0;
    private boolean isAutoChange = false;
    private int autoChangeDelay = 1000;


    public AnimSwitchTextView(@NonNull Context context) {
        this(context, null);
    }

    public AnimSwitchTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimSwitchTextView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onObtainAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        textSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AnimSwitchTextView, defStyleAttr, 0);

        textColor = ta.getColor(R.styleable.AnimSwitchTextView_textColor, Color.parseColor("#333333"));
        textSize = (int) ta.getDimension(R.styleable.AnimSwitchTextView_textSize, textSize);
        textPadding = ta.getDimensionPixelSize(R.styleable.AnimSwitchTextView_textSize, 20);

        ta.recycle();
    }

    @Override
    protected TextView createNewItemView() {
        TextView textView = new TextView(getContext());
        textView.setTextColor(textColor);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setPadding(textPadding, textPadding, textPadding, textPadding);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        return textView;
    }

    public AnimSwitchTextView setData(List<String> data) {
        this.data.clear();
        this.data.addAll(data);
        return this;
    }

    public AnimSwitchTextView isAutoChange(boolean isAutoChange) {
        this.isAutoChange = isAutoChange;
        handler = new SwitchHandler(this);
        return this;
    }

    public AnimSwitchTextView setAutoChangeDelay(int autoChangeDelay) {
        this.autoChangeDelay = autoChangeDelay;
        return this;
    }

    public void showNext() {
        if (data != null && data.size() > 0) {
            int index = position % data.size();
            String text = data.get(index);
            currentData = text;
            changeData(text);

            position++;
        }
    }

    private void resume() {
        if (isAutoChange && handler != null) {
            handler.sendEmptyMessageDelayed(WHAT, autoChangeDelay);
        }
    }

    private void pause() {
        if (isAutoChange && handler != null) {
            handler.removeCallbacksAndMessages(null);
            switchAnimator.getmAnimatorSet().cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pause();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        resume();
    }

    @Override
    protected void bindItemView(String data, TextView view) {
        view.setText(data);
    }

    private static class SwitchHandler extends Handler {
        WeakReference<AnimSwitchTextView> wr;

        SwitchHandler(AnimSwitchTextView ast) {
            wr = new WeakReference<>(ast);
        }

        @Override
        public void handleMessage(Message msg) {
            AnimSwitchTextView view = wr.get();
            if (msg.what == WHAT && view != null) {
                view.showNext();
                sendEmptyMessageDelayed(WHAT, view.autoChangeDelay);
            }
        }
    }
}
