package com.bootdo.app.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpUtils {

	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";


	//private static ExpiryMap<String, String> token_map = new ExpiryMap<>(1000*60*60*2);//2小时时限

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public static String httpPostWithJson(JSONObject jsonObj, String url) throws ClientProtocolException, IOException {
		HttpPost post = null;
		HttpClient httpClient = new DefaultHttpClient();
		// 设置超时时间
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
		post = new HttpPost(url);
		// 构造消息头
		post.setHeader("Accept", "application/json");
		post.setHeader("Authorization", "Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0");

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<String> keys = jsonObj.keySet();
		for (String key : keys) {
			formparams.add(new BasicNameValuePair(key, jsonObj.get(key) + ""));
		}

		HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");
		post.setEntity(reqEntity);

		String message = "";
		HttpResponse response = httpClient.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity resEntity = response.getEntity();
			message = EntityUtils.toString(resEntity, "utf-8");
		} else {
			System.out.println("请求失败!错误代码：" + response.getStatusLine().getStatusCode() + ",错误原因：" + response.getStatusLine().getReasonPhrase());
		}
		return message;
	}

	public static String httpGetWithJson(JSONObject jsonObj, String url) throws ClientProtocolException, IOException {
		HttpGet get = null;
		HttpClient httpClient = new DefaultHttpClient();
		// 设置超时时间
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
		get = new HttpGet(url);
		// 构造消息头
		get.setHeader("Accept", "application/json");
		get.setHeader("Authorization", "Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0");

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (jsonObj != null) {
			Set<String> keys = jsonObj.keySet();
			for (String key : keys) {
				formparams.add(new BasicNameValuePair(key, jsonObj.get(key) + ""));
			}
		}
		String message = "";
		HttpResponse response = httpClient.execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity resEntity = response.getEntity();
			message = EntityUtils.toString(resEntity, "utf-8");
		} else {
			System.out.println("请求失败!错误代码：" + response.getStatusLine().getStatusCode() + ",错误原因：" + response.getStatusLine().getReasonPhrase());
		}
		return message;
	}
	/**
	 * MD5加密
	 *
	 * @param param 要加密的参数
	 * @return
	 */
	public static String encryptionByMD5(String param) {
		StringBuffer buf = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update((param).getBytes("UTF-8"));
			byte b[] = md5.digest();
			int i;
			buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	public static String timeStamp2Date(String seconds, String format) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		if (format == null || format.isEmpty()) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds + "000")));
	}


	/**
	 *
	 * @param strUrl 请求地址
	 * @param params 请求参数
	 * @param method 请求方法
	 * @return  网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map params,String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if(method==null || method.equals("GET")){
				strUrl = strUrl+"?"+urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if(method==null || method.equals("GET")){
				conn.setRequestMethod("GET");
			}else{
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params!= null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	//将map型转为请求参数型
	public static String urlencode(Map<String,Object>data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * UTC秒数转本地时间
	 *
	 * @param unixTime
	 * @return
	 */
	public static Date FromUtcSecondsToLocal(String unixTime) {
		long nowtime=Long.parseLong(unixTime);
		return new Date(nowtime*1000);
	}


	public static void main(String[] args) {
		String s ="1575129600000";
		System.out.println(new Date(Long.valueOf(s)));
	}

}
