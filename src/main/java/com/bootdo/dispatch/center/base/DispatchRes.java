package com.bootdo.dispatch.center.base;

import java.io.Serializable;

/**
 * 可调度资源顶层接口,包括专家,物资,应急队伍等都是可调度资源
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/19
 */
public interface DispatchRes {

    enum ResType{
        //应急队伍,专家,值守人员,应急车辆,应急物资,应急装维,摄像头
        EMERGENCY_TEAM(10,"应急队伍"),EXPERT(20,"专家"),WATCHMAN(30,"值守人员"),
        EMERGENCY_VEHICLES(40,"应急车辆"),EMERGENCY_SUPPLIES(50,"应急物资"),
        EMERGENCY_EQUIPMENT(60,"应急装备"),CAMERA(70,"摄像头");
        //周边信息,学校,特殊人群
        private int value;
        private String cname;
        ResType(int value,String cname) {
            this.value = value;
            this.cname = cname;
        }
        public int getValue() {
            return value;
        }
        public String getCname() {
            return cname;
        }
    }


    default ResType getResTypeEnum(){
        return null;
    }

    /**
     * 资源类型
     * @return
     */
    default int getResType(){
        return this.getResTypeEnum()==null?-1:this.getResTypeEnum().getValue();
    }

    /**
     * 资源类型名称
     * @return
     */
    default String getResTypeName(){
        return this.getResTypeEnum()==null?null:this.getResTypeEnum().getCname();
    }

    /**
     * 资源的Id,提供给前端做唯一标识
     * @return
     */
    Serializable getResId();






    /**
     * 数据从数据库读取完毕后,需要二次包装的动作
     * 处理location,处理联系方式等
     */
    default void ready(){

    }

}
