package com.github.qing.animswitchcomponent.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.qing.animswitchlib.BaseAnimSwitchView;
import com.github.qing.animswitchlib.widget.AnimSwitchTextView;

/**
 * Created by dcq on 2017/3/28.
 * 可切换的ImageView
 */

public class SimpleSwitchImageView extends BaseAnimSwitchView<Integer, ImageView, AnimSwitchTextView> {

    public SimpleSwitchImageView(@NonNull Context context) {
        super(context);
    }

    public SimpleSwitchImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleSwitchImageView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected ImageView createNewItemView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        imageView.setLayoutParams(params);
        return imageView;
    }

    @Override
    protected void bindItemView(Integer data, ImageView view) {
        view.setImageResource(data);
    }
}
