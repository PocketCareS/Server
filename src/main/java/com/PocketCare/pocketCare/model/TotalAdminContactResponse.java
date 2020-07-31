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

import com.PocketCare.pocketCare.Interface.AnalyticsResponse;

public class TotalAdminContactResponse implements AnalyticsResponse {
    Map<Long,AdminContactsResponse> aggregatedResponse;


    public Map<Long, AdminContactsResponse> getAggregatedResponse() {
        return aggregatedResponse;
    }

    public void setAggregatedResponse(Map<Long, AdminContactsResponse> aggregatedResponse) {
        this.aggregatedResponse = aggregatedResponse;
    }
}
