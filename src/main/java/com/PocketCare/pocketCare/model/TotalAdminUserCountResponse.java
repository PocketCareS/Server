package com.PocketCare.pocketCare.model;

import com.PocketCare.pocketCare.Interface.AnalyticsResponse;

import java.util.Map;

public class TotalAdminUserCountResponse implements AnalyticsResponse {
    Map<Long, AdminUserCountResponse> aggregatedResponse;

    public Map<Long, AdminUserCountResponse> getAggregatedResponse() {
        return aggregatedResponse;
    }

    public void setAggregatedResponse(Map<Long, AdminUserCountResponse> aggregatedResponse) {
        this.aggregatedResponse = aggregatedResponse;
    }
}
