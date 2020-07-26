package com.PocketCare.pocketCare.DAO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.PocketCare.pocketCare.Entities.UserSymptons;
import com.mongodb.client.result.UpdateResult;

@Component
public interface UserSymptomsDao {
	
	public UpdateResult addOrUpdate(UserSymptons symptoms);
	
	public long getHealthyCounts(long startDate, long endDate);
	
	public List<UserSymptons> getUnHealthInfo(long startDate, long endDate);

	public long getTotalFilled(long start, long end);

	long getTotalFilled(long start);

	long getHealthyCounts(long startDate);

	List<UserSymptons> getUnHealthInfo(long startDate);

}
