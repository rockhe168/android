package com.android.mygson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.mygson.domain.Person;
import com.android.mygson.gson.FastJsonTools;
import com.android.mygson.http.HttpUtils;

public class Main extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private Button person, persons, liststring, listmap;
	private static final String TAG = "Main";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		person = (Button) this.findViewById(R.id.person);
		persons = (Button) this.findViewById(R.id.persons);
		liststring = (Button) this.findViewById(R.id.liststring);
		listmap = (Button) this.findViewById(R.id.listmap);
		person.setOnClickListener(this);
		persons.setOnClickListener(this);
		liststring.setOnClickListener(this);
		listmap.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.person:
			String path = "http://192.168.0.102:8080/jsonProject/servlet/JsonAction?action_flag=person";
			String jsonString = HttpUtils.getJsonContent(path);
			Person person = FastJsonTools.getPerson(jsonString, Person.class);
			Log.i(TAG, person.toString());
			break;
		case R.id.persons:
			String path2 = "http://192.168.0.102:8080/jsonProject/servlet/JsonAction?action_flag=persons";
			String jsonString2 = HttpUtils.getJsonContent(path2);
			List<Person> list = FastJsonTools.getPersons(jsonString2, Person.class);
			Log.i(TAG, list.toString());
			break;
		case R.id.liststring:
			String path3 = "http://192.168.0.102:8080/jsonProject/servlet/JsonAction?action_flag=liststring";
			String jsonString3 = HttpUtils.getJsonContent(path3);
			List<String> list2 = FastJsonTools.getPersons(jsonString3,String.class);
			Log.i(TAG, list2.toString());
			break;
		case R.id.listmap:
			String path4 = "http://192.168.0.102:8080/jsonProject/servlet/JsonAction?action_flag=listmap";
			String jsonString4 = HttpUtils.getJsonContent(path4);
			List<Map<String,Object>> list3 = FastJsonTools.listKeyMaps(jsonString4);
			Log.i(TAG, list3.toString());
			break;
		}
	}
}