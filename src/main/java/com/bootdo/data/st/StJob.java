package com.bootdo.data.st;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootdo.data.pf.PfCrowdDensityAlarmInfoService;
import com.bootdo.data.pf.PfCrowdDensityStatisticsInfoService;

public class StJob implements Job {
	@Autowired 
	private GctpRService gctpRService;
	@Autowired
	private PptnRService pptnRService;
	@Autowired
	private RiverRService riverRService;
	@Autowired
	private RsvrRService rsvrRService;
	@Autowired
	private SdsjRService sdsjRService;
	@Autowired 
	private SsgkRService ssgkRService;
	@Autowired
	private SsswRService ssswRService;
	@Autowired
	private  SsylRService ssylRService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	//	gctpRService.getData();
		pptnRService.getData();
		riverRService.getData();
		rsvrRService.getData();
		sdsjRService.getData();
		ssgkRService.getData();
		ssswRService.getData();
		ssylRService.getData();
		

		
	}

}
 