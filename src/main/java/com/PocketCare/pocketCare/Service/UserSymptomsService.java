package com.PocketCare.pocketCare.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PocketCare.pocketCare.Constants.AppConstants;
import com.PocketCare.pocketCare.DAO.UserSymptomsDao;
import com.PocketCare.pocketCare.Entities.EmbeddedSymptomsId;
import com.PocketCare.pocketCare.Entities.UserData;
import com.PocketCare.pocketCare.Entities.UserSymptons;
import com.PocketCare.pocketCare.Exception.AuthorizationException;
import com.PocketCare.pocketCare.Exception.ClientException;
import com.PocketCare.pocketCare.Exception.CustomException;
import com.PocketCare.pocketCare.model.BaseResponse;
import com.PocketCare.pocketCare.model.DataUploadResponse;
import com.PocketCare.pocketCare.model.UserSymptomsRequest;

@Service
public class UserSymptomsService {

	private static final Logger logger = LogManager.getLogger(UserService.class);

	@Autowired
	UserService userService;

	@Autowired
	UserSymptomsDao userSymptomsDao;

	public BaseResponse addOrUpdate(String token, UserSymptomsRequest userSymptomsRequest) throws CustomException {
		UserData user = userService.verifyToken(token);
		if (user == null) {
			throw new AuthorizationException();
		}
		logger.debug("User Symptons addorupdate: Token Verified successfully. user symptons addorupdate ");

		if (userSymptomsRequest.getDate() > 0 && userSymptomsRequest.getUsersSymptoms() != null) {
			UserSymptons userSymptoms = new UserSymptons(new EmbeddedSymptomsId(user.getDeviceId(), userSymptomsRequest.getDate()), userSymptomsRequest.getUsersSymptoms(), userSymptomsRequest.getRoommatesSymptoms(),userSymptomsRequest.getOthersSymptoms());
			userSymptomsDao.addOrUpdate(userSymptoms);
			logger.debug("User Symptons addorupdate: Successfully saved symptoms.");
			return new DataUploadResponse(AppConstants.UPLOADSUCCESS, AppConstants.SUCCESS);
		} else {
			logger.debug("User Symptons addorupdate: Data is not proper. Raising Client Exception.");
			throw new ClientException();
		}

	}

}
