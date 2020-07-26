package com.PocketCare.pocketCare.model;

import java.util.Map;

public class AggregatedCloseContactHourlyData {
    Map<String, Integer> hourlyContactNumber;

    Map<String, Integer> hourlyContactDuration;

    public Map<String, Integer> getHourlyContactNumber() {
        return hourlyContactNumber;
    }

    public void setHourlyContactNumber(Map<String, Integer> hourlyContactNumber) {
        this.hourlyContactNumber = hourlyContactNumber;
    }

    public Map<String, Integer> getHourlyContactDuration() {
        return hourlyContactDuration;
    }

    public void setHourlyContactDuration(Map<String, Integer> hourlyContactDuration) {
        this.hourlyContactDuration = hourlyContactDuration;
    }
}
