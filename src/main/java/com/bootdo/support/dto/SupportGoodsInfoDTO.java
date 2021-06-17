package com.bootdo.support.dto;

import java.util.Date;

public class SupportGoodsInfoDTO {

    private String id;
    private String goodsname;
    private String unit;
    private String oneleveltype_id;
    private String twoleveltype_id;
    private String specifications;
    private int enabled;
    private int sortno;
    private String remarks;
    private String create_by;
    private Date create_date;
    private String update_by;
    private Date update_date;
    
    private String oneleveltypeName;
    private String twoleveltypeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOneleveltype_id() {
        return oneleveltype_id;
    }

    public void setOneleveltype_id(String oneleveltype_id) {
        this.oneleveltype_id = oneleveltype_id;
    }

    public String getTwoleveltype_id() {
        return twoleveltype_id;
    }

    public void setTwoleveltype_id(String twoleveltype_id) {
        this.twoleveltype_id = twoleveltype_id;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getSortno() {
        return sortno;
    }

    public void setSortno(int sortno) {
        this.sortno = sortno;
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

	public String getOneleveltypeName() {
		return oneleveltypeName;
	}

	public void setOneleveltypeName(String oneleveltypeName) {
		this.oneleveltypeName = oneleveltypeName;
	}

	public String getTwoleveltypeName() {
		return twoleveltypeName;
	}

	public void setTwoleveltypeName(String twoleveltypeName) {
		this.twoleveltypeName = twoleveltypeName;
	}
    
}
