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
