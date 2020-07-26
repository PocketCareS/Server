package com.PocketCare.pocketCare.model;

import java.util.Map;

public class AdminUserCountResponse {
    Map<Long, Integer> hourlyTotalNumberOfUsers;
    Integer dailyNumberOfUsers;

    public Map<Long, Integer> getHourlyTotalNumberOfUsers() {
        return hourlyTotalNumberOfUsers;
    }

    public void setHourlyTotalNumberOfUsers(Map<Long, Integer> hourlyTotalNumberOfUsers) {
        this.hourlyTotalNumberOfUsers = hourlyTotalNumberOfUsers;
    }

    public Integer getDailyNumberOfUsers() {
        return dailyNumberOfUsers;
    }

    public void setDailyNumberOfUsers(Integer dailyNumberOfUsers) {
        this.dailyNumberOfUsers = dailyNumberOfUsers;
    }
}
