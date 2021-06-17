package com.bootdo.dispatch.center.vo;

import java.util.List;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/9/2
 */
public class GoodLevel2VO {

    private String levelId;
    private String levelName;
    private List<GoodInfoBaseVO> googs;

    public GoodLevel2VO() {
    }

    public GoodLevel2VO(String levelId, String levelName) {
        this.levelId = levelId;
        this.levelName = levelName;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public List<GoodInfoBaseVO> getGoogs() {
        return googs;
    }

    public void setGoogs(List<GoodInfoBaseVO> googs) {
        this.googs = googs;
    }
}
