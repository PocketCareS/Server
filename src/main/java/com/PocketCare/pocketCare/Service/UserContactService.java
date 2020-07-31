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

package com.PocketCare.pocketCare.Service;

import java.util.*;

import com.PocketCare.pocketCare.Entities.*;
import com.PocketCare.pocketCare.Enums.GraphType;
import com.PocketCare.pocketCare.Enums.NumberOfCloseContacts;
import com.PocketCare.pocketCare.Enums.NumberOfClosedContactDuration;
import com.PocketCare.pocketCare.Interface.AnalyticsResponse;
import com.PocketCare.pocketCare.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PocketCare.pocketCare.Constants.AppConstants;
import com.PocketCare.pocketCare.DAO.DailyContactDAO;
import com.PocketCare.pocketCare.DAO.UserContactDao;
import com.PocketCare.pocketCare.DAO.UserDataDAO;
import com.PocketCare.pocketCare.DAO.UserDataRepository;
import com.PocketCare.pocketCare.Exception.AuthorizationException;
import com.PocketCare.pocketCare.Exception.ClientException;
import com.PocketCare.pocketCare.Exception.CustomException;
import com.PocketCare.pocketCare.Utils.AppUtils;

@Service
public class UserContactService {

	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired
	UserService userService;

	@Autowired
	UserContactDao userContactDao;

	@Autowired
	UserDataDAO userDataDAO;

	@Autowired
	DailyContactDAO dailyContactsDao;

	@Autowired
	private UserDataRepository userDataRepository;

	@Deprecated
	public BaseResponse addOrUpdate(String token, UserContactRequest userContactRequest) throws CustomException {
		UserData user = userService.verifyToken(token);
		if (user == null) {
			throw new AuthorizationException();
		}

		for (UserContact userContact : userContactRequest.getUserContacts()) {
			if (userContact.getDate() > 0 && userContact.getContactList() != null) {
				List<String> deviceIds = userDataDAO.convertVBTNameToDeviceId(userContact.getContactList());
				UserContactList userContactList = new UserContactList(
						new EmbeddedContactListId(user.getDeviceId(), userContact.getDate(), -1), deviceIds);
				userContactDao.addOrUpdate(userContactList);
			} else {
				logger.debug("UserContactService: AddOrUpdate: Improper data, raising Client Exception");
				throw new ClientException();
			}
		}
		List<UserContactList> userContactLists = userContactDao.getLastNDaysUserContactData(AppConstants.TRACING_DAYS,
				user.getDeviceId());
		userContactDao.addOrUpdateContactList(userContactLists, user.getDeviceId());
		return new DataUploadResponse(AppConstants.UPLOADSUCCESS, AppConstants.SUCCESS);
	}

	public BaseResponse addOrUpdate(String token, DailyContactRequest dailyContactRequest) throws CustomException {
		UserData user = userService.verifyToken(token);
		if (user == null) {
			throw new AuthorizationException();
		}
		for (Map.Entry<Long, DailyContactInfo> userContactListWrapper : dailyContactRequest.getDateWiseContactInfo()
				.entrySet()) {
			Long date = userContactListWrapper.getKey();
			DailyContactInfo dailyInfo = userContactListWrapper.getValue();
			String dailyKey = dailyInfo.getDailyKey();
			userDataDAO.updateDailyKey(user.getDeviceId(), date, dailyKey);
			for (Map.Entry<Long, Map<String, List<ContactVbtInfo>>> hourlyContactMapping : dailyInfo
					.getHourlyContactInfo().entrySet()) {
				Long hour = hourlyContactMapping.getKey();
				for (Map.Entry<String, List<ContactVbtInfo>> zipWiseContactInfo : hourlyContactMapping.getValue()
						.entrySet()) {
					String zipCode = zipWiseContactInfo.getKey();
					List<ContactVbtInfo> contactVbtInfo = zipWiseContactInfo.getValue();
					EmbeddedContactsId embeddedContactsId = new EmbeddedContactsId(zipCode, date, hour);
					// Save data in database
					dailyContactsDao.addOrUpdate(embeddedContactsId, user.getDeviceId(), contactVbtInfo);
					resolveVbt(embeddedContactsId, user.getDeviceId(), dailyKey, contactVbtInfo);
				}
			}
		}
//		//Get last N days data and put in UserContactList. 
//		//Notification will only be sent to the devices of the respective mentioned in table UserContactList based on the patient devieId 
//		List<UserContactList> userContactLists = userContactDao.getLastNDaysUserContactData(AppConstants.TRACING_DAYS,
//		user.getDeviceId());
//		logger.debug(" Contact List created for device Id " + user.getDeviceId());
//		userContactDao.addOrUpdateContactList(userContactLists, user.getDeviceId());
		return new DataUploadResponse(AppConstants.UPLOADSUCCESS, AppConstants.SUCCESS);
	}

	/*
	 * This should be asynchronous This function first checks the presence of self
	 * in existing contacts then resolve the vbtNames present in self contact list
	 */
	private void resolveVbt(EmbeddedContactsId contactsId, String deviceId, String dailyKey,
			List<ContactVbtInfo> vbtInfo) {
		DailyContacts dailyContacts = dailyContactsDao.get(contactsId);
		if (dailyContacts != null) {
			int contactsResolved = 0;
			// Map of deviceId against which the VBT data is stored
			Map<String, List<ContactVbtInfo>> contactMapping = getContactsMappingMap(dailyContacts); //dailyContacts.getContactMapping();
			if (contactMapping != null && !contactMapping.isEmpty()) {
				Map<String, ContactVbtInfo> resolvedDeviceIds = new HashMap<>();
				String selfVbt = AppUtils.getVbtName(dailyKey, contactsId.getHour());
				for (Map.Entry<String, List<ContactVbtInfo>> entry : contactMapping.entrySet()) {
					if (!deviceId.equals(entry.getKey())) {
						String entryDailyKey = userDataDAO.getDailyKeyByDate(entry.getKey(), contactsId.getDate());
						if (entryDailyKey == null) {
							continue;
						}

						/*
						 * Resolving previous unresolved vbt's Checking if VBT of deviceId uploading
						 * data is present in existing contactLists
						 */
						ContactVbtInfo selfVbtInfo = new ContactVbtInfo();
						selfVbtInfo.setVbtName(selfVbt);
						int indexSelf = entry.getValue().indexOf(selfVbtInfo);
						Map<String, ContactVbtInfo> resolvedPrevDeviceIds = new HashMap<>();
						if (indexSelf != -1) {
							resolvedPrevDeviceIds.put(deviceId, entry.getValue().get(indexSelf));
						}

						/*
						 * Resolving the vbt of the current device's contacts checking if the existing
						 * vbt is present in the contact list of the uploading deviceId
						 */
						String entryVbtName = null;
						if (contactsResolved != vbtInfo.size()) {
							entryVbtName = AppUtils.getVbtName(entryDailyKey, contactsId.getHour());
							ContactVbtInfo info = new ContactVbtInfo();
							info.setVbtName(entryVbtName);
							int index = vbtInfo.indexOf(info);
							if (index != -1) {
								contactsResolved += 1;
								ContactVbtInfo entryVbtInfo = vbtInfo.get(index);
								resolvedDeviceIds.put(entry.getKey(), entryVbtInfo);
							}
						}

						/*
						 * D1: <d2:t1> D2: <d1:t2> After server modification: Compare t1 and t2 and
						 * update the duration of contact for each as (t1>t2)?t1:t2
						 */
						ContactVbtInfo processedVbtInfo = AppUtils.compare(resolvedPrevDeviceIds.get(deviceId),
								resolvedDeviceIds.get(entry.getKey()));
						if (processedVbtInfo != null) {
							processedVbtInfo.setVbtName(selfVbt);
							resolvedPrevDeviceIds.put(deviceId, processedVbtInfo);
							ContactVbtInfo resolvedVbtInfo = new ContactVbtInfo(entryVbtName,
									processedVbtInfo.getCountTwo(), processedVbtInfo.getCountTen(),
									processedVbtInfo.getAvgDist(), processedVbtInfo.getZone(), processedVbtInfo.getOnCampus(), processedVbtInfo.getOffCampus());
							resolvedDeviceIds.put(entry.getKey(), resolvedVbtInfo);
							EmbeddedContactListId id = new EmbeddedContactListId(entry.getKey(), contactsId.getDate(),
									contactsId.getHour());
							updateContactInfoToDb(id, resolvedPrevDeviceIds);
						}

					}
				}

				if (resolvedDeviceIds == null || resolvedDeviceIds.isEmpty()) {
					updateUnresolvedInfo(contactsId, deviceId, vbtInfo);
				}
				// update contact list of self uploading deviceId.
				EmbeddedContactListId id = new EmbeddedContactListId(deviceId, contactsId.getDate(),
						contactsId.getHour());
				updateContactInfoToDb(id, resolvedDeviceIds);
			}
		}
	}

	private void updateUnresolvedInfo(EmbeddedContactsId contactsId, String deviceId, List<ContactVbtInfo> vbtInfo) {
		// TODO Auto-generated method stub
		int totalCloseContactDuration = 0;
		int totalCloseContacts = 0;
		for (ContactVbtInfo entry : vbtInfo) {
			if (isCloseContact(entry)) {
				totalCloseContactDuration += entry.getCountTwo();
				totalCloseContacts += 1;
			}
		}
		userContactDao.updateUnResolvedInfo(
				new EmbeddedContactListId(deviceId, contactsId.getDate(), contactsId.getHour()), totalCloseContacts,
				totalCloseContactDuration);
	}

	private void updateContactInfoToDb(EmbeddedContactListId id, Map<String, ContactVbtInfo> resolvedDeviceIds) {
		UserContactList userContactList = userContactDao.getContactDataById(id);
		Map<String, ContactVbtInfo> map = new HashMap<>();
		if (userContactList != null && userContactList.getContactLists() != null) {
			map = userContactList.getContactDeviceInfo(); // verify this

		} else {
			userContactList = new UserContactList(id, null);
		}
		if (map == null || map.isEmpty()) {
			map = new HashMap<>();
		}
		map.putAll(resolvedDeviceIds);
		if (map == null || map.isEmpty()) {
			return;
		}

		segregateCloseContactsAndSet(userContactList, map);
		userContactDao.addOrUpdate(userContactList);// TODO
	}

	private void segregateCloseContactsAndSet(UserContactList userContactList, Map<String, ContactVbtInfo> map) {
		int totalCountTwo = userContactList.getTotalCountTwo();
		int totalCountTen = userContactList.getTotalCountTen();
		Map<String, ContactVbtInfo> closeMap = userContactList.getCloseContactDeviceInfo();
		Map<String, ContactVbtInfo> aggMap = userContactList.getContactDeviceInfo();

		for (Map.Entry<String, ContactVbtInfo> entry : map.entrySet()) {
			totalCountTwo += entry.getValue().getCountTwo();
			totalCountTen += entry.getValue().getCountTen();
			if (isCloseContact(entry.getValue())) {
				closeMap.put(entry.getKey(), entry.getValue());
			}
			aggMap.put(entry.getKey(), entry.getValue());
		}
		userContactList.setTotalCountTwo(totalCountTwo);
		userContactList.setTotalCountTen(totalCountTen);
	}

	/*
	 * It doesn't make sense if the value is stored hourly. The data should to be
	 * replaced.
	 */
	private ContactVbtInfo appendContactInf(ContactVbtInfo existingContactInfo, ContactVbtInfo contactVbtInfo) {
		if (contactVbtInfo != null) {
			if (existingContactInfo != null) {
				existingContactInfo.setCountTwo(existingContactInfo.getCountTwo() + contactVbtInfo.getCountTwo());
				existingContactInfo.setCountTen(existingContactInfo.getCountTen() + contactVbtInfo.getCountTen());
				existingContactInfo.setAvgDist((existingContactInfo.getAvgDist() + contactVbtInfo.getAvgDist()) / 2);
			} else {
				existingContactInfo = contactVbtInfo;
			}
		}
		return existingContactInfo;
	}

	private boolean isCloseContact(ContactVbtInfo contactVbtInfo) {
		return contactVbtInfo.getCountTwo() >= AppConstants.CLOSECONTACTDURATION;
	}
	
	private Map<String, List<ContactVbtInfo>> getContactsMappingMap(DailyContacts dailyContacts) {
		EmbeddedContactsId id = dailyContacts.getDailyContactsId();
		Map<String, List<ContactVbtInfo>> inValidZipContactMapping = new HashMap<>();
		if (!id.getZipCode().equals("0")) {
			EmbeddedContactsId inValidZipContactId = new EmbeddedContactsId("0", id.getDate(), id.getHour());
			DailyContacts inValidZipContact = dailyContactsDao.get(inValidZipContactId);
			if(inValidZipContact != null) {
				inValidZipContactMapping = inValidZipContact.getContactMapping();
				if (inValidZipContactMapping == null) {
					inValidZipContactMapping = new HashMap<>();
				}
			}
		}
		Map<String, List<ContactVbtInfo>> contactMapping = dailyContacts.getContactMapping();
		if (contactMapping != null) {
			contactMapping.putAll(inValidZipContactMapping);
		} else {
			contactMapping = inValidZipContactMapping;
		}
		return contactMapping;
	}

	public ContactAnalyticsResponse getContactInfo(String token, Long startDate, Long endDate, String contactType)
			throws CustomException {
		UserData user = userService.verifyToken(token);
		if (user == null) {
			throw new AuthorizationException();
		}
		List<Long> dates = AppUtils.getDates(startDate, endDate);
		Map<Long, CloseContactDailyData> contactCountDailyData = new HashMap<Long, CloseContactDailyData>();
		for (Long date : dates) {
			Map<Long, CloseContactHourlyData> contactCountHourlyData = new HashMap<Long, CloseContactHourlyData>();
			List<UserContactList> userContactLists = userContactDao.getContactDataBydeviceIdAndDate(user.getDeviceId(),
					date);
			Integer total_Number_Of_Contacts = 0;
			int totalContactDuration = 0;
			int contactDuration = 0;
			for (UserContactList userContactList : userContactLists) {
				contactDuration = 0;
				if (AppConstants.CONTACTTYPE.equalsIgnoreCase(contactType)) {
					int contacts = userContactList.getCloseContactDeviceInfo().size();
					contactDuration = userContactList.getTotalCountTwo();
					if (contacts == 0) {
						contacts = userContactList.getUnResolvedCC();
						contactDuration = userContactList.getUnResolvedCCDuration();
					}
					contactCountHourlyData.put(userContactList.getContactListId().getHour(),
							new CloseContactHourlyData(contacts, contactDuration));
					total_Number_Of_Contacts += contacts;
				} else {
					contactDuration = userContactList.getTotalCountTen() + userContactList.getTotalCountTwo();
					contactCountHourlyData.put(userContactList.getContactListId().getHour(),
							new CloseContactHourlyData(userContactList.getContactDeviceInfo().size(), contactDuration));
					total_Number_Of_Contacts += userContactList.getContactDeviceInfo().size();
				}
				totalContactDuration += contactDuration;
			}
			contactCountDailyData.put(date,
					new CloseContactDailyData(contactCountHourlyData, totalContactDuration, total_Number_Of_Contacts));
		}
		ContactAnalyticsResponse contactAnalyticsResponse = new ContactAnalyticsResponse();
		contactAnalyticsResponse.setContactCount(contactCountDailyData);
		return contactAnalyticsResponse;
	}


	public AnalyticsResponse getContactInfoAggregated( Long startDate, Long endDate, String contactType, String graphType)
			throws CustomException {
		GraphType graphTypeEnum = GraphType.from(graphType);
		switch (graphTypeEnum){
			case CLOSE_CONTACTS:
				return this.getNoOfContactAndDurationInfo(startDate,endDate,contactType,null);
			case NUMBER_OF_USERS:
				return this.getUserCountInfo(startDate,endDate);
			case CLOSE_CONTACTS_PERECENTAGE:
				return this.getNoOfContactAndDurationInfo(startDate,endDate,contactType,"percentage");
			default:
				return this.getNoOfContactAndDurationInfo(startDate,endDate,contactType,null);
		}
	}

	public TotalAdminUserCountResponse getUserCountInfo(Long startDate, Long endDate){
		List<Long> dates = AppUtils.getDates(startDate, endDate);
		Map<Long, AdminUserCountResponse> aggregatedResponse = new TreeMap<Long, AdminUserCountResponse>();
		for(Long date: dates){
			Map<Long, Integer> hourlyNoOfUserData = new TreeMap<>();
			List<DailyContacts> dailyContacts = dailyContactsDao.getDailyContactsByDate(date);
			Set<String> deviceIDs = new HashSet<>();
			for(DailyContacts dailyContact:dailyContacts){
				deviceIDs.addAll(dailyContact.getContactMapping().keySet());
				if(hourlyNoOfUserData.get(dailyContact.getDailyContactsId().getHour())!=null){
					Integer count = hourlyNoOfUserData.get(dailyContact.getDailyContactsId().getHour());
					count = count+dailyContact.getContactMapping().keySet().size();
					hourlyNoOfUserData.put(dailyContact.getDailyContactsId().getHour(),count);
				}
				else{
					hourlyNoOfUserData.put(dailyContact.getDailyContactsId().getHour(),
							dailyContact.getContactMapping().keySet().size());
				}
			}
			AdminUserCountResponse adminUserCountResponse = new AdminUserCountResponse();
			adminUserCountResponse.setDailyNumberOfUsers(deviceIDs.size());
			adminUserCountResponse.setHourlyTotalNumberOfUsers(hourlyNoOfUserData);
			aggregatedResponse.put(date,adminUserCountResponse);
		}
		TotalAdminUserCountResponse totalAdminUserCountResponse = new TotalAdminUserCountResponse();
		totalAdminUserCountResponse.setAggregatedResponse(aggregatedResponse);
		return totalAdminUserCountResponse;
	}

	public TotalAdminContactResponse getNoOfContactAndDurationInfo(Long startDate, Long endDate, String contactType,String perecentage){
		List<Long> dates = AppUtils.getDates(startDate, endDate);
		Map<Long, AdminContactsResponse> contactCountDailyData = new TreeMap<Long, AdminContactsResponse>();
		for (Long date : dates) {
			Map<Long, AggregatedCloseContactHourlyData> aggregatedContactCountHourlyData = new TreeMap<Long,
					AggregatedCloseContactHourlyData>();
			List<UserContactList> userContactLists = userContactDao.getContactDataByDate(date);
//			Integer total_Number_Of_Contacts = 0;
			Map<String, Integer> total_Number_Of_Contacts = new TreeMap<>();
			Map<String, Integer> totalContactDuration = new TreeMap<>();
//			int totalContactDuration = 0;
			int contactDuration = 0;
			Map<Long, Integer> peakHourMap = new TreeMap<>();
			for (UserContactList userContactList : userContactLists) {
				contactDuration = 0;
				if (AppConstants.CONTACTTYPE.equalsIgnoreCase(contactType)) {
					if (peakHourMap.get(userContactList.getContactListId().getHour()) != null) {
						Integer hour = userContactList.getCloseContactDeviceInfo().size() +
								peakHourMap.get(userContactList.getContactListId().getHour());
						peakHourMap.put(userContactList.getContactListId().getHour(), hour);
					} else {
						peakHourMap.put(userContactList.getContactListId().getHour(), userContactList.getCloseContactDeviceInfo().size());
					}
					NumberOfCloseContacts numberOfCloseContacts = NumberOfCloseContacts.
							from(userContactList.getCloseContactDeviceInfo().size());
					NumberOfClosedContactDuration numberOfClosedContactDuration = NumberOfClosedContactDuration.
							from(userContactList.getTotalCountTwo());
					if (aggregatedContactCountHourlyData.get(userContactList.getContactListId().getHour()) != null) {
						AggregatedCloseContactHourlyData aggregatedCloseContactHourlyData = aggregatedContactCountHourlyData.
								get(userContactList.getContactListId().getHour());
						if (aggregatedCloseContactHourlyData.getHourlyContactNumber().
								get(numberOfCloseContacts.name()) != null) {
							Integer count = aggregatedCloseContactHourlyData.getHourlyContactNumber().
									get(numberOfCloseContacts.name());
							aggregatedCloseContactHourlyData.getHourlyContactNumber().
									put(numberOfCloseContacts.name(), count + 1);
						} else {
							aggregatedCloseContactHourlyData.getHourlyContactNumber().put(numberOfCloseContacts.name(), 1);
						}
						if (aggregatedCloseContactHourlyData.
								getHourlyContactDuration().get(numberOfClosedContactDuration.name()) != null) {
							Integer count = aggregatedCloseContactHourlyData.getHourlyContactDuration().
									get(numberOfClosedContactDuration.name());
							aggregatedCloseContactHourlyData.getHourlyContactDuration().
									put(numberOfClosedContactDuration.name(), count + 1);
						} else {
							aggregatedCloseContactHourlyData.getHourlyContactDuration().
									put(numberOfClosedContactDuration.name(), 1);
						}
						aggregatedContactCountHourlyData.put(userContactList.getContactListId().getHour(), aggregatedCloseContactHourlyData);
						// TO DO DURATION
					} else {
						AggregatedCloseContactHourlyData aggregatedCloseContactHourlyData = new AggregatedCloseContactHourlyData();
						Map<String, Integer> hourlyData = new TreeMap<>();
						hourlyData.put(numberOfCloseContacts.name(), 1);
						aggregatedCloseContactHourlyData.setHourlyContactNumber(hourlyData);
						Map<String, Integer> hourlyContactDuration = new TreeMap<>();
						hourlyContactDuration.put(numberOfClosedContactDuration.name(), 1);
						aggregatedCloseContactHourlyData.setHourlyContactDuration(hourlyContactDuration);
						aggregatedContactCountHourlyData.put(userContactList.getContactListId().getHour(), aggregatedCloseContactHourlyData);
						//TO DO DURATION
					}
					if (total_Number_Of_Contacts.get(numberOfCloseContacts.name()) != null) {
						Integer count = total_Number_Of_Contacts.get(numberOfCloseContacts.name());
						total_Number_Of_Contacts.put(numberOfCloseContacts.name(), count + 1);
					} else {
						total_Number_Of_Contacts.put(numberOfCloseContacts.name(), 1);
					}
					if (totalContactDuration.get(numberOfClosedContactDuration.name()) != null) {
						Integer count = totalContactDuration.get(numberOfClosedContactDuration.name());
						totalContactDuration.put(numberOfClosedContactDuration.name(), count + 1);
					} else {
						totalContactDuration.put(numberOfClosedContactDuration.name(), 1);
					}
					int contacts = userContactList.getCloseContactDeviceInfo().size();
					contactDuration = userContactList.getTotalCountTwo();
//					if (contacts == 0) {
//						contacts = userContactList.getUnResolvedCC();
//						contactDuration = userContactList.getUnResolvedCCDuration();
//					}
//					contactCountHourlyData.put(userContactList.getContactListId().getHour(),
//							new CloseContactHourlyData(contacts, contactDuration));
//					total_Number_Of_Contacts += contacts;
				} else {
					contactDuration = userContactList.getTotalCountTen() + userContactList.getTotalCountTwo();
//					contactCountHourlyData.put(userContactList.getContactListId().getHour(),
//							new CloseContactHourlyData(userContactList.getContactDeviceInfo().size(), contactDuration));
//					total_Number_Of_Contacts += userContactList.getContactDeviceInfo().size();
				}
//				totalContactDuration += contactDuration;
			}
			Long peakHour = null;
			Integer peakHourValue = 0;
			for (Map.Entry<Long, Integer> entry : peakHourMap.entrySet()) {
				if (peakHourValue < entry.getValue()) {
					peakHour = entry.getKey();
					peakHourValue = entry.getValue();
				}
			}
			if (perecentage != null) {
				for (Map.Entry<Long, AggregatedCloseContactHourlyData> entry : aggregatedContactCountHourlyData.entrySet()) {
					AggregatedCloseContactHourlyData aggregatedCloseContactHourlyData = entry.getValue();
					Map<String, Integer> hourlyContactNumber = aggregatedCloseContactHourlyData.getHourlyContactNumber();
					Map<String, Integer> hourlyContactDuration = aggregatedCloseContactHourlyData.getHourlyContactDuration();
					Integer totalVal = 0;
					Integer totalValDuration = 0;
					for (Map.Entry<String, Integer> entryContact : hourlyContactNumber.entrySet()) {
						totalVal = totalVal + entryContact.getValue();
					}
					for (Map.Entry<String, Integer> entryDuration : hourlyContactDuration.entrySet()) {
						totalValDuration = totalValDuration + entryDuration.getValue();
					}
					for (Map.Entry<String, Integer> entryContact : hourlyContactNumber.entrySet()) {
						Integer percent = (entryContact.getValue()*100) / totalVal;
						hourlyContactNumber.put(entryContact.getKey(), percent);
					}
					for (Map.Entry<String, Integer> entryDuration : hourlyContactDuration.entrySet()) {
						Integer percent = (entryDuration.getValue()*100) / totalValDuration;
						hourlyContactDuration.put(entryDuration.getKey(), percent);
					}
					aggregatedCloseContactHourlyData.setHourlyContactDuration(hourlyContactDuration);
					aggregatedCloseContactHourlyData.setHourlyContactNumber(hourlyContactNumber);
					aggregatedContactCountHourlyData.put(entry.getKey(), aggregatedCloseContactHourlyData);
				}
				Integer totalContactDay = 0;
				for (Map.Entry<String, Integer> entry : total_Number_Of_Contacts.entrySet()) {
					totalContactDay = totalContactDay + entry.getValue();
				}
				for (Map.Entry<String, Integer> entry : total_Number_Of_Contacts.entrySet()) {
					Integer val = entry.getValue();
					total_Number_Of_Contacts.put(entry.getKey(), (val*100) / totalContactDay);
				}
				totalContactDay = 0;
				for (Map.Entry<String, Integer> entry : totalContactDuration.entrySet()) {
					totalContactDay = totalContactDay + entry.getValue();
				}
				for (Map.Entry<String, Integer> entry : totalContactDuration.entrySet()) {
					Integer val = entry.getValue();
					totalContactDuration.put(entry.getKey(), (val*100) / totalContactDay);
				}
			}
			AdminContactsResponse adminContactsResponse = new AdminContactsResponse();
			adminContactsResponse.setAggregatedContactCountHourlyData(aggregatedContactCountHourlyData);
			adminContactsResponse.setTotalContacts(total_Number_Of_Contacts);
			adminContactsResponse.setTotalContactsDuration(totalContactDuration);
			adminContactsResponse.setPeakHour(peakHour);
			contactCountDailyData.put(date, adminContactsResponse);

		}
		TotalAdminContactResponse totalAdminContactResponse = new TotalAdminContactResponse();
		totalAdminContactResponse.setAggregatedResponse(contactCountDailyData);
		return totalAdminContactResponse;
	}

	public Map<String,ContactTracingResponse> getContactTracingList( String deviceId){
		List<UserContactList> userContactLists = userContactDao.getLastNDaysUserContactData(AppConstants.TRACING_DAYS,
				deviceId);
		if(userContactLists == null){
			logger.debug("empty list");
		}
//		Set<String> contactTracingData = new HashSet<>();
		Map<String, ContactTracingResponse> contactTracingData = new HashMap<>();
		for(UserContactList userContactList:userContactLists){
			logger.debug("not empty");
//				Set<String> contactData = new HashSet<>();
			Map<String, ContactVbtInfo> data = userContactList.getContactDeviceInfo();
			for(Map.Entry entry: data.entrySet()){
				ContactVbtInfo contactVbtInfo = (ContactVbtInfo) entry.getValue();
				if(contactVbtInfo.getCountTwo()>=AppConstants.CONTACTTRACEDURATION){
                    ContactTracingResponse contactTracingResponse = null;
					if(contactTracingData.get(entry.getKey())==null){
					    contactTracingResponse = new ContactTracingResponse();
					    contactTracingResponse.setDate(userContactList.getContactListId().getDate());
					    contactTracingResponse.setTotalContactDuration(contactVbtInfo.getCountTwo());
					    contactTracingResponse.setMaxContactDuration(contactVbtInfo.getCountTwo());
					    contactTracingResponse.setEncounterCount(1);
					    List<ContactTracingIndividualData> contactTracingIndividualDataList = new ArrayList<>();
					    ContactTracingIndividualData contactTracingIndividualData = new ContactTracingIndividualData();
					    contactTracingIndividualData.setDuration(contactVbtInfo.getCountTwo());
					    contactTracingIndividualData.setDate(userContactList.getContactListId().getDate());
					    contactTracingIndividualDataList.add(contactTracingIndividualData);
					    contactTracingResponse.setContactInformation(contactTracingIndividualDataList);
                    }
					else{
						List<ContactTracingIndividualData> contactTracingIndividualDataList = contactTracingData.
								get(entry.getKey()).getContactInformation();
					    contactTracingResponse = contactTracingData.get(entry.getKey());
					    ContactTracingIndividualData contactTracingIndividualData = new ContactTracingIndividualData();
					    contactTracingIndividualData.setDate(userContactList.getContactListId().getDate());
					    contactTracingIndividualData.setDuration(contactVbtInfo.getCountTwo());
					    if(contactTracingResponse.getMaxContactDuration()<contactVbtInfo.getCountTwo()){
					        contactTracingResponse.setMaxContactDuration(contactVbtInfo.getCountTwo());
					        contactTracingResponse.setDate(userContactList.getContactListId().getDate());
                        }
					    Integer duration = contactTracingResponse.getTotalContactDuration()+contactVbtInfo.getCountTwo();
					    contactTracingResponse.setTotalContactDuration(duration);
					    Integer encounter = contactTracingResponse.getEncounterCount();
					    contactTracingResponse.setEncounterCount(encounter+1);
					    contactTracingIndividualDataList.add(contactTracingIndividualData);
					    contactTracingResponse.setContactInformation(contactTracingIndividualDataList);
                    }
					contactTracingData.put((String)entry.getKey(),contactTracingResponse);
				}
			}

		}
		return contactTracingData;
	}
}
