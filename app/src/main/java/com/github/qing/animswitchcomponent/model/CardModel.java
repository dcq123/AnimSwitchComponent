package com.github.qing.animswitchcomponent.model;

/**
 * Created by dcq on 2017/3/28.
 */

public class CardModel {

    String title;
    int iconId;

    public CardModel(String title,int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
