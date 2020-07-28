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
