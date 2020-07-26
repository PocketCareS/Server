package com.PocketCare.pocketCare.model;

import java.util.Map;

public class ContactAnalyticsResponse {
    Map<Long, CloseContactDailyData>contactCount;

    public Map<Long, CloseContactDailyData> getContactCount() {
        return contactCount;
    }

    public void setContactCount(Map<Long, CloseContactDailyData> contactCount) {
        this.contactCount = contactCount;
    }
}
