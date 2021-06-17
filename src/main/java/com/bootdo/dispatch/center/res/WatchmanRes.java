package com.bootdo.dispatch.center.res;

import com.bootdo.common.utils.StringUtils;
import com.bootdo.dispatch.center.base.ContactWay;
import com.bootdo.dispatch.center.base.Contactable;
import com.bootdo.dispatch.center.base.DispatchRes;

import java.util.Date;

/**
 * 值守人员
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/23
 */
public class WatchmanRes  implements DispatchRes,Contactable {

    private String resId;

    private String deptId;

    private String deptName;

    private String name;

    private String mobile;

    private Date dutyDate;

    /**
     * 联系方式
     */
    private ContactWay contactWay;

    @Override
    public ResType getResTypeEnum() {
        return ResType.WATCHMAN;
    }


    @Override
    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ContactWay getContactWay() {
        return contactWay;
    }

    public void setContactWay(ContactWay contactWay) {
        this.contactWay = contactWay;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(Date dutyDate) {
        this.dutyDate = dutyDate;
    }

    @Override
    public void ready() {
        if(StringUtils.isNotEmpty(name)||StringUtils.isNotEmpty(mobile)){
            setContactWay(new ContactWay(name,mobile));
        }
    }
}
