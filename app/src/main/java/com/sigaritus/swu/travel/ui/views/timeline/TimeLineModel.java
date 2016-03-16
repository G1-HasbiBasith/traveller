package com.sigaritus.swu.travel.ui.views.timeline;

import java.io.Serializable;


public class TimeLineModel implements Serializable{
    private String content;
    private String time;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
