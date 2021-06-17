package com.bootdo.dispatch.center.ws.msg;

import java.io.Serializable;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/22
 */
public interface WsMsg extends Serializable {

    String getMsgType();

    //private static final long serialVersionUID = -7752260854917225729L;
    //
    //private String msgType;
    //
    //private Object data;
    //
    //
    //public String getMsgType() {
    //    return msgType;
    //}
    //
    //public void setMsgType(String msgType) {
    //    this.msgType = msgType;
    //}
    //
    //public Object getData() {
    //    return data;
    //}
    //
    //public void setData(Object data) {
    //    this.data = data;
    //}
    //
    //public WsMsg(String msgType, Object data) {
    //    this.msgType = msgType;
    //    this.data = data;
    //}


}
