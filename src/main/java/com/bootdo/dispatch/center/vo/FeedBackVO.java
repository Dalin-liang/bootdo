package com.bootdo.dispatch.center.vo;

import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDetailDO;

import java.util.List;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/9/5
 */
public class FeedBackVO {

    //任务Id
    private String taskId;
    //地址
    private String address;
    //反馈人类别 0 用户id 1 姓名
    private Integer personType;
    //应急人员表Id
    private String deptpersonId;
    //方案Id
    private String actionProgId;
    //实际反馈人
    private String deptpesonName;
    //反馈明细
    private List<DispatchTaskFeedbackDetailDO> details;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public String getDeptpersonId() {
        return deptpersonId;
    }

    public void setDeptpersonId(String deptpersonId) {
        this.deptpersonId = deptpersonId;
    }

    public String getActionProgId() {
        return actionProgId;
    }

    public void setActionProgId(String actionProgId) {
        this.actionProgId = actionProgId;
    }

    public List<DispatchTaskFeedbackDetailDO> getDetails() {
        return details;
    }

    public void setDetails(List<DispatchTaskFeedbackDetailDO> details) {
        this.details = details;
    }

    public String getDeptpesonName() {
        return deptpesonName;
    }

    public void setDeptpesonName(String deptpesonName) {
        this.deptpesonName = deptpesonName;
    }
}
