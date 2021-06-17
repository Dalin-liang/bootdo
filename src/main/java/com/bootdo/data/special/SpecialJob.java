package com.bootdo.data.special;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootdo.data.pf.PfCrowdDensityAlarmInfoService;
import com.bootdo.data.pf.PfCrowdDensityStatisticsInfoService;

public class SpecialJob implements Job {
	@Autowired 
	private SpecialService specialService;

	
	
	
	 
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//specialService.getData("api/special/populationBloodPressure/getByBeginTime");
		/*specialService.getData("api/special/populationBloodPressure/getByBeginTime");
		specialService.getData("api/special/populationFall/getByBeginTime");
		specialService.getData("api/special/populationHeartRate/getByBeginTime");
		specialService.getData("api/special/populationSleep/getByBeginTime");
		//specialService.getData("api/special/populationSos/getByBeginTime");
		specialService.getData("api/special/populationStartup/getByBeginTime");
		specialService.getData("api/special/populationWalk/getByBeginTime");*/
		
	}

}
 