package com.bootdo.dispatch.center.ws.msg;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/22
 */
public abstract class AbstractWsMsg implements WsMsg {

    private static final long serialVersionUID = -1115118941351365033L;


    private String msgType;


    public AbstractWsMsg(String msgType) {
        this.msgType = msgType;
    }

    public AbstractWsMsg(WsMsgType msgType) {
        this.msgType = msgType.name();
    }

    @Override
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
