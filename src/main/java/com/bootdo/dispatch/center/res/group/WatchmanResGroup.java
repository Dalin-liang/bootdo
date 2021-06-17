package com.bootdo.dispatch.center.res.group;

import com.bootdo.dispatch.center.base.DispatchResGroup;
import com.bootdo.dispatch.center.res.WatchmanRes;
import com.bootdo.support.dao.DailyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/27
 */
@Service
public class WatchmanResGroup implements DispatchResGroup<WatchmanRes> {

    @Autowired
    private DailyDao dailyDao;


    @Override
    public List<WatchmanRes> getAllRes() {
        LocalDate ld = LocalDate.now();
        Date start = Date.from(ld.minusMonths(2).atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        Date end = Date.from(ld.plusDays(1).atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        List<WatchmanRes> data = dailyDao.getWatchman(start,end);
        for (WatchmanRes datum : data) {
            datum.ready();
        }
        return data;
    }

}
