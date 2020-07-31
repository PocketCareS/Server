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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EmbeddedId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.PocketCare.pocketCare.model.ContactVbtInfo;


/*
 * For storing the contacts based on date.
 */
@Document(collection = "UserContactLists")
public class UserContactList {

    @Id
	@EmbeddedId
    private EmbeddedContactListId contactListId;
    private List<String> contactLists;//TODO remove this.
    private Map<String, ContactVbtInfo> contactDeviceInfo;
    private Map<String, ContactVbtInfo> closeContactDeviceInfo;
    private int totalCountTwo;
	private int totalCountTen;
	private int unResolvedCC;
	private int unResolvedCCDuration;
	
	public int getUnResolvedCC() {
		return unResolvedCC;
	}

	public void setUnResolvedCC(int unResolvedCC) {
		this.unResolvedCC = unResolvedCC;
	}

	public int getUnResolvedCCDuration() {
		return unResolvedCCDuration;
	}

	public void setUnResolvedCCDuration(int unResolvedCCDuration) {
		this.unResolvedCCDuration = unResolvedCCDuration;
	}

	public int getTotalCountTwo() {
		return totalCountTwo;
	}

	public void setTotalCountTwo(int totalCountTwo) {
		this.totalCountTwo = totalCountTwo;
	}

	public int getTotalCountTen() {
		return totalCountTen;
	}

	public void setTotalCountTen(int totalCountTen) {
		this.totalCountTen = totalCountTen;
	}

    
    public UserContactList(EmbeddedContactListId contactListId, List<String> contactLists) {
        this.contactListId = contactListId;
        this.contactLists = contactLists;
    }

    public EmbeddedContactListId getContactListId() {
        return contactListId;
    }

    public void setContactListId(EmbeddedContactListId contactListId) {
        this.contactListId = contactListId;
    }

    public List<String> getContactLists() {
        return contactLists;
    }

    public void setContactLists(List<String> contactLists) {
        this.contactLists = contactLists;
    }

	public Map<String, ContactVbtInfo> getContactDeviceInfo() {
		if(Objects.isNull(contactDeviceInfo)){
			contactDeviceInfo = new HashMap<>();
		}
		return contactDeviceInfo;
	}

	public void setContactDeviceInfo(Map<String, ContactVbtInfo> contactDeviceInfo) {
		this.contactDeviceInfo = contactDeviceInfo;
	}

	public Map<String, ContactVbtInfo> getCloseContactDeviceInfo() {
		if(Objects.isNull(closeContactDeviceInfo)){
			closeContactDeviceInfo = new HashMap<>();
		}
		return closeContactDeviceInfo;
	}

	public void setCloseContactDeviceInfo(Map<String, ContactVbtInfo> closeContactDeviceInfo) {
		this.closeContactDeviceInfo = closeContactDeviceInfo;
	}
    
}
