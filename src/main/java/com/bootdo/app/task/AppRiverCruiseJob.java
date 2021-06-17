package com.bootdo.app.task;

import com.bootdo.app.service.RiverCruiseService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class AppRiverCruiseJob implements Job {

    @Autowired
    private RiverCruiseService riverCruiseService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        riverCruiseService.getRiverCruiseEvent();
    }
}
