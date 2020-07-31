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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PocketCare.pocketCare.DAO.UserDataDAO;
import com.PocketCare.pocketCare.Entities.UserData;
import com.PocketCare.pocketCare.model.UserRegistrationResponse;

@Service
public class AdminService {
    @Autowired
    UserDataDAO userDataDAO;
    @Autowired
    UserService userService;

    public UserRegistrationResponse login(String userName, String password) throws Exception {
        UserData userData = userDataDAO.getUserDataByUserName(userName);
        if(userData == null){
            throw new Exception(" Invalid password or userName");
        }
        password = password.trim();
        if(!userData.getPassword().equals(password)){
            throw new Exception(" Invalid password or userName");
        }
        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
        Date date = new Date();
        if(userData.getExpiryTime()<=date.getTime()){
            userData = userService.generateTokenAndSetExpiryTime(userData);
            userDataDAO.updateUserDataTokenAndExpiry(userData.getDeviceId(), userData.getAuthToken(),
                    userData.getExpiryTime());
        }
        userRegistrationResponse.setToken(userData.getAuthToken());
        return userRegistrationResponse;
    }

    public UserData createAdmin(String userName, String password, String deviceId){
        UserData userData = userDataDAO.getUserDataByDeviceId(deviceId);
        userDataDAO.updateAdminDataUserNameAndPassword(deviceId, userName, password);
        return userData;
    }
}
