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

package com.PocketCare.pocketCare.DAO;

import com.PocketCare.pocketCare.Entities.EmbeddedContactListId;
import com.PocketCare.pocketCare.Entities.UserContactList;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserContactDao {
    public UserContactList addOrUpdate(UserContactList userContactList);
    public UpdateResult addOrUpdateContactList(List<UserContactList> userContactList, String deviceId);
    public List<UserContactList> getLastNDaysUserContactData(Integer days,String deviceId);
	UserContactList getContactDataById(EmbeddedContactListId id);
	List<UserContactList> getContactDataBydeviceIdAndDate(String deviceId,Long date);
	List<UserContactList> getContactDataByDate(Long date);
	List<UserContactList> getContactDataBydeviceIdAndDateAndHour(String deviceId,Long date,Long hour);
	UpdateResult updateUnResolvedInfo(EmbeddedContactListId id, int count, int duration);

    }
