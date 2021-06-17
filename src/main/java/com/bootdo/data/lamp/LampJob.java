package com.bootdo.data.lamp;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class LampJob  implements Job{
	@Autowired
	private LapmService lapmService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		lapmService.getData("/api/lamp/alarmDoor/getByBeginTime");
		lapmService.getData("/api/lamp/alarmPhaseLoss/getByBeginTime");
		lapmService.getData("/api/lamp/alarmPower/getByBeginTime");
		lapmService.getData("/api/lamp/alarmTransformer/getByBeginTime");
		lapmService.getData("/api/lamp/alarmWaterenter/getByBeginTime");
		lapmService.getData("/api/lamp/alarmZoneInfrared/getByBeginTime");
		lapmService.getData("/api/lamp/listPathLine/getByBeginTime");

	}
	
	

	
}
