package com.weather.app.tool;

import org.json.JSONException;
import org.json.JSONObject;

import com.weather.app.db.WeatherDao;
import com.weather.app.model.City;
import com.weather.app.model.County;
import com.weather.app.model.Province;

import android.text.TextUtils;

public class ParseJSONWithJSONObject {

	public static boolean parseJSON(WeatherDao dao,String jsonData,String type,String code){
		try {
			if(!TextUtils.isEmpty(jsonData)){
				JSONObject json = new JSONObject(jsonData);
				JSONObject jsonList = json.getJSONObject("list");
				for(int i = 1; i <= jsonList.length(); i++){
					JSONObject wjr = jsonList.getJSONObject("wjr"+i);
					String name = wjr.getString("diming");
					String childNodes = wjr.getString("zidi");
					String cityCode = wjr.getString("daima");
					String level = wjr.getString("dengji");
					
					if("province".equals(type)){
						Province p = new Province(Integer.parseInt(level), name, cityCode, Integer.parseInt(childNodes));
						dao.addProvince(p);
					}else if("city".equals(type)){
						City c = new City(Integer.parseInt(level), name, cityCode, code, Integer.parseInt(childNodes));
						dao.addCity(c);
					}else if("county".equals(type)){
						County c = new County(Integer.parseInt(level), name, cityCode, code, Integer.parseInt(childNodes));
						dao.addCounty(c);
					}
				}
				return true;
			}
			
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}
}
