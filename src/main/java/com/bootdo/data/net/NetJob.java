package com.bootdo.data.net;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class NetJob implements Job{
	@Autowired
	private NetService netService;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		netService.getData("/api/net/eventCrosslineInfo/getByBeginTime");
		netService.getData("/api/net/eventLeftInfo/getByBeginTime");
		netService.getData("/api/net/eventParkingdetectionInfo/getByBeginTime");
		netService.getData("/api/net/eventRioterInfo/getByBeginTime");
		netService.getData("/api/net/eventWanderInfo/getByBeginTime");
		
	}

}
