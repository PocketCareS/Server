package com.PocketCare.pocketCare.model;

import com.PocketCare.pocketCare.Entities.AnalyticsGeneric;
import com.PocketCare.pocketCare.Interface.AnalyticsResponse;

import java.util.Map;

public class TotalAdminContactResponse implements AnalyticsResponse {
    Map<Long,AdminContactsResponse> aggregatedResponse;


    public Map<Long, AdminContactsResponse> getAggregatedResponse() {
        return aggregatedResponse;
    }

    public void setAggregatedResponse(Map<Long, AdminContactsResponse> aggregatedResponse) {
        this.aggregatedResponse = aggregatedResponse;
    }
}
