package com.bootdo.dispatch.center.vo;

import java.util.List;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/9/2
 */
public class GoodLevel1VO {

    private String levelId;
    private String levelName;
    private List<GoodLevel2VO> level2;

    public GoodLevel1VO() {
    }

    public GoodLevel1VO(String levelId, String levelName) {
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

    public List<GoodLevel2VO> getLevel2() {
        return level2;
    }

    public void setLevel2(List<GoodLevel2VO> level2) {
        this.level2 = level2;
    }
}
