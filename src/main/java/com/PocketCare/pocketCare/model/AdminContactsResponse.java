package com.PocketCare.pocketCare.model;

import java.util.Map;

public class AdminContactsResponse {
    Map<String, Integer> totalContacts;
    Map<String, Integer> totalContactsDuration;
    Map<Long, AggregatedCloseContactHourlyData> aggregatedContactCountHourlyData;
    Long peakHour;

    public Map<String, Integer> getTotalContacts() {
        return totalContacts;
    }

    public void setTotalContacts(Map<String, Integer> totalContacts) {
        this.totalContacts = totalContacts;
    }

    public Map<Long, AggregatedCloseContactHourlyData> getAggregatedContactCountHourlyData() {
        return aggregatedContactCountHourlyData;
    }

    public void setAggregatedContactCountHourlyData(Map<Long, AggregatedCloseContactHourlyData> aggregatedContactCountHourlyData) {
        this.aggregatedContactCountHourlyData = aggregatedContactCountHourlyData;
    }

    public Map<String, Integer> getTotalContactsDuration() {
        return totalContactsDuration;
    }

    public void setTotalContactsDuration(Map<String, Integer> totalContactsDuration) {
        this.totalContactsDuration = totalContactsDuration;
    }

    public Long getPeakHour() {
        return peakHour;
    }

    public void setPeakHour(Long peakHour) {
        this.peakHour = peakHour;
    }
}
