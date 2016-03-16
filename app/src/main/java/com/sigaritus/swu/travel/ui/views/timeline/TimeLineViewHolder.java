package com.sigaritus.swu.travel.ui.views.timeline;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sigaritus.swu.travel.R;
import com.vipul.hp_hp.timelineview.TimelineView;


public class TimeLineViewHolder extends RecyclerView.ViewHolder {
    public TextView content;
    public TextView time;
    public  TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        content = (TextView) itemView.findViewById(R.id.tx_content);
        time = (TextView) itemView.findViewById(R.id.tx_time);
        mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        mTimelineView.initLine(viewType);
    }

}
