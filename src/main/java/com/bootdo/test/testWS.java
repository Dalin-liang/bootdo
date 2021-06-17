package com.bootdo.test;

import java.util.Date;
import java.text.DateFormat;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.xml.namespace.QName;
import java.lang.Integer;
import javax.xml.rpc.ParameterMode;

public class testWS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       sentMsg();
        // stateMsg();
	}

	public static void sentMsg() {
        try {
            String endpoint = "http://172.27.11.105/dx/xfire/MsmService";
            //直接引用远程的wsdl文件
            //以下都是套路
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName("sentMsg");//wsdl里面描述的接口名称
            call.addParameter("account", org.apache.axis.encoding.XMLType.XSD_STRING,
                    ParameterMode.IN);//接口的参数
            call.addParameter("password", org.apache.axis.encoding.XMLType.XSD_STRING,
                    ParameterMode.IN);
            call.addParameter("msg", org.apache.axis.encoding.XMLType.XSD_STRING,
                    ParameterMode.IN);
            call.addParameter("mobiles", org.apache.axis.encoding.XMLType.XSD_STRING,
                    ParameterMode.IN);
            call.addParameter("sendtime", org.apache.axis.encoding.XMLType.XSD_STRING,
                    ParameterMode.IN);
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型

            // String temp = "测试人员";
            String result = (String)call.invoke(new Object[]{"zhzg","zc511300","发送开会短信","13534438911","2019-11-26 19:40:00"});
            //给方法传递参数，并且调用方法
            System.out.println("result is "+result);
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public static void stateMsg() {
	    /*（3）短信状态查询（stateMsg）
接口描述：
根据传入参数，先验证身份是否通过，身份验证成功的，根据输入的短信ID，查询短信发送状态，返回手机号、发送状态。
传入参数：用户帐号、密码、短信ID
返回值：多条记录，每条记录包括手机号、状态
*/
        try {
            String endpoint = "http://172.27.11.105/dx/xfire/MsmService";
            //直接引用远程的wsdl文件
            //以下都是套路
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName("stateMsg");//wsdl里面描述的接口名称
            call.addParameter("account", org.apache.axis.encoding.XMLType.XSD_STRING,
                    ParameterMode.IN);//接口的参数
            call.addParameter("password", org.apache.axis.encoding.XMLType.XSD_STRING,
                    ParameterMode.IN);
            call.addParameter("id", XMLType.XSD_LONG,
                    ParameterMode.IN);
//            call.addParameter("mobiles", org.apache.axis.encoding.XMLType.XSD_STRING,
//                    ParameterMode.IN);
//            call.addParameter("sendtime", org.apache.axis.encoding.XMLType.XSD_STRING,
//                    ParameterMode.IN);
            call.setReturnType(XMLType.XSD_ANYTYPE);//设置返回类型
            System.out.println(call.invoke(new Object[]{"zhzg","zc511300",-4}));
            // String temp = "测试人员";
//            String result = (String)call.invoke(new Object[]{"zhzg","zc511300",-4});
            //给方法传递参数，并且调用方法
//            System.out.println("result is "+result);
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
