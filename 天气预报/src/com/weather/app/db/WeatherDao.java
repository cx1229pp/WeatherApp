package com.weather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.weather.app.model.City;
import com.weather.app.model.County;
import com.weather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WeatherDao {
	private static final String dbName = "weather";
	private static final int version = 1;
	private static WeatherDao dao;
	private SQLiteDatabase db;
	private static final String TABLE_PROVINCE = "province";
	private static final String TABLE_CITY = "city";
	private static final String TABLE_COUNTY = "county";
	private static final String TABLE_SELECTCITY = "selectCity";

	/**
	 * ˽�й��췽�������ڳ�ʼ�����ݿ�
	 * @param context
	 */
	private WeatherDao(Context context){
		WeatherOpenHelper openHelper = new WeatherOpenHelper(context, dbName, null, version);
		db = openHelper.getWritableDatabase();
	}
	
	/**
	 * ����ģʽ��ȡWeatherDaoʵ��
	 * @param context
	 * @return
	 */
	public synchronized static  WeatherDao getInstance(Context context){
		if(dao == null){
			dao = new WeatherDao(context);
		}
		
		return dao;
	}
	
	/**
	 * ����ʡ����Ϣ
	 * @param province
	 */
	public void addProvince(Province province){
		ContentValues values = new ContentValues();
		values.put("level", province.getLevel());
		values.put("name", province.getName());
		values.put("province_code", province.getProvinceCode());
		values.put("child_nodes", province.getChildNodes());
		db.insert(TABLE_PROVINCE, null, values);
	}
	
	/**
	 * ��ѯʡ����Ϣ
	 * @return
	 */
	public List<Province> queryProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query(TABLE_PROVINCE, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			int level = cursor.getInt(cursor.getColumnIndex("level"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String provinceCode = cursor.getString(cursor.getColumnIndex("province_code"));
			int childNodes = cursor.getInt(cursor.getColumnIndex("child_nodes"));
			
			Province province = new Province(level, name, provinceCode, childNodes);
			list.add(province);
		}
		
		return list;
	}
	
	/**
	 * ����������Ϣ
	 * @param city
	 */
	public void addCity(City city){
		ContentValues values = new ContentValues();
		values.put("level", city.getLevel());
		values.put("name", city.getName());
		values.put("city_code", city.getCityCode());
		values.put("province_code", city.getProvinceCode());
		values.put("child_nodes", city.getChildNodes());
		
		db.insert(TABLE_CITY, null, values);
	}
	
	/**
	 * ��ѯʡ������������Ϣ
	 * @param provinceCode ʡ�ݱ���
	 * @return ������Ϣ����
	 */
	public List<City> queryCitys(String provinceCode){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query(TABLE_CITY, null, " province_code = ?", new String[]{provinceCode}, null, null, null);
		while(cursor.moveToNext()){
			int level = cursor.getInt(cursor.getColumnIndex("level"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String cityCode = cursor.getString(cursor.getColumnIndex("city_code"));
			int childNodes = cursor.getInt(cursor.getColumnIndex("child_nodes"));
			
			City city = new City(level, name, cityCode, provinceCode, childNodes);
			list.add(city);
		}
		
		return list;
	}
	
	/**
	 * �����ؼ�����Ϣ
	 * @param county
	 */
	public void addCounty(County county){
		ContentValues values = new ContentValues();
		values.put("level", county.getLevel());
		values.put("name", county.getName());
		values.put("city_code", county.getCityCode());
		values.put("county_code", county.getCountyCode());
		values.put("child_nodes", county.getChildNodes());
		
		db.insert(TABLE_COUNTY, null, values);
	}
	
	/**
	 * ��ѯ���������ؼ�����Ϣ
	 * @param cityCode ���б���
	 * @return �ؼ�����Ϣ����
	 */
	public List<County> queryCountys(String cityCode){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query(TABLE_COUNTY, null, " city_code = ?", new String[]{cityCode}, null, null, null);
		while(cursor.moveToNext()){
			int level = cursor.getInt(cursor.getColumnIndex("level"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String countyCode = cursor.getString(cursor.getColumnIndex("county_code"));
			int childNodes = cursor.getInt(cursor.getColumnIndex("child_nodes"));
			
			County county = new County(level, name, countyCode, cityCode, childNodes);
			list.add(county);
		}
		
		return list;
	}
	
	public void addSelectCity(String selectCityCode,String selectCityName){
		ContentValues values = new ContentValues();
		values.put("city_code", selectCityCode);
		values.put("city_name", selectCityName);
		
		db.insert(TABLE_SELECTCITY, null, values);
	}
	
	public List<String> querySelectCitys(){
		List<String> list = new ArrayList<String>();
		Cursor cursor = db.query(TABLE_SELECTCITY, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			String cityName = cursor.getString(cursor.getColumnIndex("city_name"));
			list.add(cityName);
		}
		
		return list;
	}

	/**
	 * �ж��Ƿ���ѡ��ó���
	 * @param cityCode ���б���
	 * @return
	 */
	public boolean isSelectCity(String cityCode){
		Cursor cursor = db.query(TABLE_SELECTCITY, null, "city_code = ?", new String[]{cityCode}, null, null, null);
		Log.d("isSelectCity",cursor.getCount()+"");
		if(cursor.getCount() > 0){
			return true;
		}else{
			return false;
		}
	}

	public void deleteSelectCity(String cityName){
		db.delete(TABLE_SELECTCITY,"city_name = ?",new String[]{cityName});
	}
	
	public void closeDB(){
		if(db != null){
			try{
				db.close();
			}catch(Exception e){
			}
		}
	}
}
