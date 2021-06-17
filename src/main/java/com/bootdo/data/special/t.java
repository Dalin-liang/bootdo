package com.bootdo.data.special;

import com.alibaba.fastjson.JSONObject;

public class t {
public static void main(String[] args) {
	
	String str="{\\\"tablename\\\": \\\"RM_EVER_M\\\",			 	\\\"operation\\\": \\\"INSERT\\\",				 	\\\"data\\\": [{							 		\\\"PRO_CD\\\": \\\"5027bd9c361e4453874bbd1b320b6b\\\",	 		\\\"PC_CD\\\": \\\"55TDMqlm\\\",			 		\\\"MN_TYCD\\\": \\\"40000000\\\",			 		\\\"SUB_TYCD\\\": \\\"40000001\\\",		 		\\\"LON\\\": \\\"116.29201000\\\",				 		\\\"LOT\\\": \\\"40.05028000\\\",				 		\\\"DIR\\\": \\\"**********\\\",				 		\\\"HDL_ST\\\": \\\"1\\\",			 		\\\"EVE_TM\\\": \\\"2019-10-23 11:40:12\\\",			 		\\\"TS\\\":\\\"2019-10-23 11:40:12\\\", 	 		\\\"EVE_CH\\\": \\\"3\\\",			 		\\\"IF_ISSUE\\\": \\\"0\\\",		 		\\\"IF_PUB\\\": \\\"0\\\",			 		\\\"IF_UP\\\": \\\"0\\\",			 		\\\"ADDR\\\": \\\"******\\\",					 		\\\"PHONE\\\": \\\"1234567890\\\"			 	}] }";
	JSONObject json=JSONObject.parseObject(str);
	System.out.println(json.get("data"));
}
}
