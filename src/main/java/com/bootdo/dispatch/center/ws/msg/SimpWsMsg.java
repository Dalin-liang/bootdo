package com.bootdo.dispatch.center.ws.msg;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/22
 */
public class SimpWsMsg extends AbstractWsMsg {

    private static final long serialVersionUID = 431200920857705183L;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public SimpWsMsg(String msgType, Object data) {
        super(msgType);
        this.data = data;
    }

    public SimpWsMsg(WsMsgType msgType, Object data) {
        super(msgType);
        this.data = data;
    }
}
