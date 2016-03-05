package com.sigaritus.swu.travel.ui.views.pinnedList.adapter;

/**
 * Created by Administrator on 2016/3/5.
 */
public  class Item {

    public static final int ITEM = 0;
    public static final int SECTION = 1;

    public final int type;
    public final String text;

    public int sectionPosition;
    public int listPosition;

    public Item(int type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override public String toString() {
        return text;
    }

}