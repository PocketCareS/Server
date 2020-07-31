/*
 * Copyright 2020 University at Buffalo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
