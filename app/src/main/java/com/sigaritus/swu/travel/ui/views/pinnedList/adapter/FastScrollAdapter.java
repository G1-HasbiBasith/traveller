package com.sigaritus.swu.travel.ui.views.pinnedList.adapter;

import android.content.Context;
import android.widget.SectionIndexer;

/**
 * Created by Administrator on 2016/3/5.
 */
public class FastScrollAdapter extends SimpleAdapter implements SectionIndexer {

    private Item[] sections;

    public FastScrollAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override protected void prepareSections( int sectionsNumber) {
        sections = new Item[sectionsNumber];
    }

    @Override protected void onSectionAdded(Item section, int sectionPosition) {
        sections[sectionPosition] = section;
    }

    @Override public Item[] getSections() {
        return sections;
    }

    @Override public int getPositionForSection(int section) {
        if (section >= sections.length) {
            section = sections.length - 1;
        }
        return sections[section].listPosition;
    }

    @Override public int getSectionForPosition(int position) {
        if (position >= getCount()) {
            position = getCount() - 1;
        }
        return getItem(position).sectionPosition;
    }

}
