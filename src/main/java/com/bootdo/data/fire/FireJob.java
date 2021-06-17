package com.bootdo.data.fire;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class FireJob implements Job {
	@Autowired
	private FireService fireService;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		fireService.getData("/api/fire/burning/getByBeginTime");
		fireService.getData("/api/fire/electricDistinguish/getByBeginTime");
		fireService.getData("/api/fire/electricEarlyWarning/getByBeginTime");
		fireService.getData("/api/fire/electricError/getByBeginTime");
		fireService.getData("/api/fire/electricNow/getByBeginTime");
		fireService.getData("/api/fire/electricWarning/getByBeginTime");
		fireService.getData("/api/fire/smoke/getByBeginTime");
		fireService.getData("/api/fire/smokeRelay/getByBeginTime");
		fireService.getData("/api/fire/smokeRelayWarm/getByBeginTime");
		fireService.getData("/api/fire/userMessage/getByBeginTime");
		fireService.getData("/api/fire/userMessageFc/getByBeginTime");
		fireService.getData("/api/fire/userMessageFcSensor/getByBeginTime");
		fireService.getData("/api/fire/waterCycle/getByBeginTime");
		fireService.getData("/api/fire/waterCycleV2/getByBeginTime");
		fireService.getData("/api/fire/waterNowSmr1000/getByBeginTime");
		fireService.getData("/api/fire/waterWarm/getByBeginTime");
		fireService.getData("/api/fire/waterWarmSmr1000/getByBeginTime");
		fireService.getData("/api/fire/waterWarmV2/getByBeginTime");
	}

}
