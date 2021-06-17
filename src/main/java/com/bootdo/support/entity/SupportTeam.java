package com.bootdo.support.entity;

import java.util.Date;
import java.util.List;

public class SupportTeam {
    private String id;
    private String name;
    private String teamtype_id;
    private String dept_id;
    private Integer numofteam;
    private String remarks;
    private String create_by;
    private Date create_date;
    private String update_by;
    private Date update_date;
    private String address;
    private String lon;
    private String lat;

    //关联应急人员
    private List<SupportDeptPerson> deptperson;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamtype_id() {
        return teamtype_id;
    }

    public void setTeamtype_id(String teamtype_id) {
        this.teamtype_id = teamtype_id;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public Integer getNumofteam() {
        return numofteam;
    }

    public void setNumofteam(Integer numofteam) {
        this.numofteam = numofteam;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

    public List<SupportDeptPerson> getDeptperson() {
        return deptperson;
    }

    public void setDeptperson(List<SupportDeptPerson> deptperson) {
        this.deptperson = deptperson;
    }
}
