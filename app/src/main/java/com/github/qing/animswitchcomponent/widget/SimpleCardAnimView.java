package com.github.qing.animswitchcomponent.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.qing.animswitchcomponent.R;
import com.github.qing.animswitchcomponent.model.CardModel;
import com.github.qing.animswitchlib.BaseAnimSwitchView;

/**
 * Created by dcq on 2017/3/28.
 * <p>
 * 自定义的布局进行Anim切换
 */

public class SimpleCardAnimView extends BaseAnimSwitchView<CardModel, View, SimpleCardAnimView> {

    public SimpleCardAnimView(@NonNull Context context) {
        super(context);
    }

    public SimpleCardAnimView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleCardAnimView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createNewItemView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.card_item_layout, this, false);
    }

    @Override
    protected void bindItemView(CardModel data, View view) {
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(data.getTitle());

        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        imageView.setImageResource(data.getIconId());
    }
}
