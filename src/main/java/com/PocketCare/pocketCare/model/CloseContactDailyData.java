package com.PocketCare.pocketCare.model;

import java.util.Map;

public class CloseContactDailyData {
    private Map<Long,CloseContactHourlyData> closeContactCount;
    private Integer duration;
    private Integer totalCount;

    public CloseContactDailyData(Map<Long, CloseContactHourlyData> closeContactCount, Integer duration, Integer totalCount){
        this.closeContactCount = closeContactCount;
        this.duration = duration;
        this.totalCount = totalCount;
    }

    public Map<Long, CloseContactHourlyData> getCloseContactCount() {
        return closeContactCount;
    }

    public void setCloseContactCount(Map<Long, CloseContactHourlyData> closeContactCount) {
        this.closeContactCount = closeContactCount;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
