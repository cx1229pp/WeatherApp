package com.weather.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.weather.app.R;
import com.weather.app.customLayout.IndexLayout;
import com.weather.app.customLayout.WeatherLaytout;
import com.weather.app.model.Weather;
import com.weather.app.tool.ParseWeatherJSON;
import com.weather.app.util.NetworkUtil;
import com.weather.app.util.WeatherConstant;

import org.apache.http.Header;

public class MainActivity<SharePreferences> extends Activity implements OnClickListener{
	private TextView tv_city;
	private TextView tv_updateTime;
	private String cityName;
	private Weather weather;
	private WeatherLaytout weatherLaytout;
	private IndexLayout indexLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_layout);
		init();
	}

	private void init(){
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_updateTime = (TextView) findViewById(R.id.tv_updateTime);
		weatherLaytout = (WeatherLaytout)findViewById(R.id.weather_layout);
		indexLayout = (IndexLayout)findViewById(R.id.index_layout);

		//判断网络是否可用
		if(NetworkUtil.networkIsConnect(this)){
			//读取默认选中的城市
			SharedPreferences sp = getSharedPreferences("weatherSharedPreferences", Context.MODE_PRIVATE);
			cityName = sp.getString("cityName", "");
			if(!TextUtils.isEmpty(cityName)){
				tv_city.setText(cityName);
				getWeather();
			}else{
				disableView();
			}

			tv_city.setOnClickListener(this);
		}else{
			tv_city.setText(getString(R.string.NetworkErrorInfo));
			disableView();
		}
	}

	private void disableView(){
		tv_updateTime.setVisibility(View.GONE);
		weatherLaytout.setVisibility(View.GONE);
		indexLayout.setVisibility(View.GONE);
	}

	private void enableView(){
		tv_updateTime.setVisibility(View.VISIBLE);
		weatherLaytout.setVisibility(View.VISIBLE);
		indexLayout.setVisibility(View.VISIBLE);
	}

	/**
	 * 查询天气
	 */
	private void getWeather(){
		String url = WeatherConstant.GET_WEATHER_URL;
		url = url.replace("LOCATION",cityName);
		url = url.replace("AK",getString(R.string.baiduKey));
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {
				String jsonString = new String(bytes);
				weather = ParseWeatherJSON.praseJson(jsonString);
				if (weather != null) {
					setWeather();
				}
			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
				Toast.makeText(MainActivity.this,getString(R.string.ErrorInfo),Toast.LENGTH_SHORT);
			}
		});
	}

	/**
	 * 显示天气信息
	 */
	private void setWeather(){
		tv_updateTime.setText(weather.getDate());

		weatherLaytout.setData(weather);
		indexLayout.setData(weather);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_city:
			Intent intent = new Intent(this,ChoseCityActivity.class);
			startActivityForResult(intent,1);

			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == -1){
			cityName = data.getStringExtra("cityName");
			tv_city.setText(cityName);
			enableView();
			getWeather();
		}
	}
}
