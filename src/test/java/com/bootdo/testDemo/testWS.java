package com.bootdo.testDemo;

import java.util.Date;
import java.text.DateFormat;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import javax.xml.namespace.QName;
import java.lang.Integer;
import javax.xml.rpc.ParameterMode;

public class testWS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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

            String temp = "测试人员";
            String result = (String)call.invoke(new Object[]{"帐号","密码","发送开会短信","13925533173","2015-03-09 19:40:00"});
            //给方法传递参数，并且调用方法
            System.out.println("result is "+result);
     }
     catch (Exception e) {
            System.err.println(e.toString());
     }
	}

}
