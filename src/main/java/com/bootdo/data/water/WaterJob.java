package com.bootdo.data.water;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class WaterJob implements Job {
	@Autowired  
	private  WaterService waterService;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		waterService.getData("http://113.108.176.67:8090/zghb/a/app/riverInterface/getAlarmInfo");
		
	}


}
