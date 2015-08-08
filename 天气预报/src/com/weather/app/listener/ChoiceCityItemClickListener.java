package com.weather.app.listener;

import java.util.List;

import com.weather.app.activity.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ChoiceCityItemClickListener implements OnItemClickListener {
	private Context context;
	private List<String> selectCitys;

	public ChoiceCityItemClickListener(Context context,List<String> selectCitys) {
		this.context = context;
		this.selectCitys = selectCitys;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		if(selectCitys != null){
			String cityName = selectCitys.get(position);
			Intent mainActivityIntent = new Intent(context,MainActivity.class);
			mainActivityIntent.putExtra("cityName", cityName);
			context.startActivity(mainActivityIntent);
		}

	}

}
