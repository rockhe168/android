package com.http.get;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.message.BasicNameValuePair;

public class HttpUtils {

	private static String URL_PATH = "http://192.168.0.102:8080/myhttp/pro1.png";

	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void saveImageToDisk() {
		InputStream inputStream = getInputStream();
		byte[] data = new byte[1024];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("C:\\test.png");
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ��÷������˵�����,��InputStream��ʽ����
	 * @return
	 */
	public static InputStream getInputStream() {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(URL_PATH);
			if (url != null) {
				httpURLConnection = (HttpURLConnection) url.openConnection();
				// ������������ĳ�ʱʱ��
				httpURLConnection.setConnectTimeout(3000);
				httpURLConnection.setDoInput(true);
				// ��ʾ���ñ���http����ʹ��GET��ʽ����
				httpURLConnection.setRequestMethod("GET");
				int responseCode = httpURLConnection.getResponseCode();
				if (responseCode == 200) {
					// �ӷ��������һ��������
					inputStream = httpURLConnection.getInputStream();
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputStream;
	}

	public static void main(String[] args) {
		// �ӷ��������ͼƬ���浽����
		saveImageToDisk();
	}
}
