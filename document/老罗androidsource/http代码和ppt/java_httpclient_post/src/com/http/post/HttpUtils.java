package com.http.post;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtils {

	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String sendHttpClientPost(String path,
			Map<String, String> map, String encode) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		try {
			// 实现将请求的参数封装到表单中,请求体重
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
			// 使用Post方式提交数据
			HttpPost httpPost = new HttpPost(path);
			httpPost.setEntity(entity);
			// 指定post请求
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return changeInputStream(httpResponse.getEntity().getContent(),
						encode);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 将一个输入流转换成指定编码的字符串
	 * 
	 * @param inputStream
	 * @param encode
	 * @return
	 */
	public static String changeInputStream(InputStream inputStream,
			String encode) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "http://192.168.0.102:8080/myhttp/servlet/LoginAction";
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "admin");
		params.put("password", "123");
		String result = HttpUtils.sendHttpClientPost(path, params, "utf-8");
		System.out.println("-->>"+result);
	}

}
