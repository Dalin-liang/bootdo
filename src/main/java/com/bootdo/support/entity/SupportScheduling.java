package com.bootdo.support.entity;

import java.util.Date;

public class SupportScheduling {
    private String id;
    private String deptperson_id;
    private Date scheduling_date;
    private String work;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptperson_id() {
        return deptperson_id;
    }

    public void setDeptperson_id(String deptperson_id) {
        this.deptperson_id = deptperson_id;
    }

    public Date getScheduling_date() {
        return scheduling_date;
    }

    public void setScheduling_date(Date scheduling_date) {
        this.scheduling_date = scheduling_date;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
