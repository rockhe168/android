package com.json.tools;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class JsonTools {

	public JsonTools() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param object
	 *            �ǶԽ����ļ��ϵ�����
	 * @return
	 */
	public static String createJsonString(Object value) {
		Gson gson = new Gson();
		String str = gson.toJson(value);
		return str;
	}

}
