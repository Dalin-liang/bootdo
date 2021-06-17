package com.bootdo.support.entity;

import java.util.List;

/**
 * 地理信息类别周边信息
 */
public class SupportArroundInfo {

    //地理信息类别id
    private String geotype_id;

    //地理信息建筑的间数（栋数）
    private Integer typenumber;

    //该地理信息的总人数
    private Integer number;

    //地理信息的分类(crowd表示人群，building表示建筑)
    private String attention_type;

    //地理信息类别名称
    private String name;

    private List<SupportGeoInfo> geoInfos;


    public String getGeotype_id() {
        return geotype_id;
    }

    public void setGeotype_id(String geotype_id) {
        this.geotype_id = geotype_id;
    }

    public Integer getTypenumber() {
        return typenumber;
    }

    public void setTypenumber(Integer typenumber) {
        this.typenumber = typenumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAttention_type() {
        return attention_type;
    }

    public void setAttention_type(String attention_type) {
        this.attention_type = attention_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SupportGeoInfo> getGeoInfos() {
        return geoInfos;
    }

    public void setGeoInfos(List<SupportGeoInfo> geoInfos) {
        this.geoInfos = geoInfos;
    }
}
