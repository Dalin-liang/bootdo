package com.bootdo.dispatch.center.vo;

import com.bootdo.dispatch.center.res.GoodDistributionRes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/9/2
 */
public class GoodInfoBaseVO {

    private String goodId;
    private String goodName;
    private String unit;
    private List<GoodDistributionRes> res;

    public GoodInfoBaseVO() {
    }

    public GoodInfoBaseVO(String goodId, String goodName, String unit) {
        this.goodId = goodId;
        this.goodName = goodName;
        this.unit = unit;
        this.res = new ArrayList<>();
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<GoodDistributionRes> getRes() {
        return res;
    }

    public void setRes(List<GoodDistributionRes> res) {
        this.res = res;
    }
}
