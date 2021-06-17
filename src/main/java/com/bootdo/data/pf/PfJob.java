package com.bootdo.data.pf;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootdo.data.pf.PfCrowdDensityAlarmInfoService;
import com.bootdo.data.pf.PfCrowdDensityStatisticsInfoService;

public class PfJob implements Job {

	@Autowired
	private PfCrowdDensityAlarmInfoService alarmInfoService;
	@Autowired 
	private PfCrowdDensityStatisticsInfoService crowdDensityStatisticsInfoService;
	@Autowired
	private PfPassengerFlowStatistics passengerFlowStatistics;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		crowdDensityStatisticsInfoService.getData();
		alarmInfoService.getData();
		passengerFlowStatistics.getData();

		
	}

}
 