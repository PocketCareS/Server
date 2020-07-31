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

package com.PocketCare.pocketCare.Entities;

import java.util.List;
import java.util.Map;

import javax.persistence.EmbeddedId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.PocketCare.pocketCare.model.ContactVbtInfo;

@Document(collection = "DailyContacts")
public class DailyContacts {

	@Id
	@EmbeddedId
	private EmbeddedContactsId dailyContactsId;

	Map<String, List<ContactVbtInfo>> contactMapping;
	
	public EmbeddedContactsId getDailyContactsId() {
		return dailyContactsId;
	}

	public void setDailyContactsId(EmbeddedContactsId dailyContactsId) {
		this.dailyContactsId = dailyContactsId;
	}

	public Map<String, List<ContactVbtInfo>> getContactMapping() {
		return contactMapping;
	}

	public void setContactMapping(Map<String, List<ContactVbtInfo>> contactMapping) {
		this.contactMapping = contactMapping;
	}
	
	

}
