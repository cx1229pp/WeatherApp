package com.weather.app.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.weather.app.R;
import com.weather.app.db.WeatherDao;
import com.weather.app.model.City;
import com.weather.app.model.County;
import com.weather.app.model.Province;
import com.weather.app.tool.ParseJSONWithJSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 *ѡ������б�
 */
public class CityListActivity extends Activity {
	private ListView listView;
	private ArrayAdapter<String> adapter;
	//listView���ݼ���
	private List<String> dataList = new ArrayList<String>();
	//���б���List
	private List<String> codeList = new ArrayList<String>();
	private WeatherDao dao;
	//ѡ�еĳ��б���
	private String selectCityCode;
	//��ǰѡ�еĳ�������
	private String selectCityName;
	//ѡ���ʡ�ݱ���
	private String selectProvinceCode;
	//ѡ���ʡ������
	private String selectProvinceName;
	//��ǰѡ�е�item����
	private int currentLevel;

	private Context context = this;
	
	
	//����Ԥ����ز���
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	public TextView tv_location;
	
	//LEVEL_PROVINCE ʡ��,LEVEL_CITY �м�,LEVEL_COUNTY ���м�
	private static final int LEVEL_PROVINCE = 1;
	private static final int LEVEL_CITY = 2;
	private static final int LEVEL_COUNTY = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.city_list_layout);
		
		listView = (ListView) findViewById(R.id.lv_citys);
		dao = WeatherDao.getInstance(this);
		
		adapter = new ArrayAdapter<String>(this,R.layout.item_layout, R.id.tv_item, dataList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//���ʡ�ݲ�ѯ����������Ϣ
				if(currentLevel == LEVEL_PROVINCE){
					selectProvinceCode = codeList.get(position);
					selectProvinceName = dataList.get(position);
					queryCitys();
				}
				//������в�ѯ�����ؼ�����Ϣ
				else if(currentLevel == LEVEL_CITY){
					selectCityCode = codeList.get(position);
					selectCityName = dataList.get(position);
					queryCountys();
				}
				//���ѡ�����
				else{
					Log.d("CityListActivity", dataList.get(position) + "--" + codeList.get(position));
					Intent intent = new Intent(CityListActivity.this,ChoseCityActivity.class);
					//�ж��Ƿ���ѡ����ó���
					if(!dao.isSelectCity(codeList.get(position))){
						dao.addSelectCity(codeList.get(position), dataList.get(position));
						setResult(1,intent);
					}else{
						setResult(-1,intent);
					}

					finish();
				}
			}
			
		});
		//��ѯʡ����Ϣ
		queryProvinces();
		
		//��λ��ǰ������Ϣ
		tv_location = (TextView) findViewById(R.id.tv_location);
		//��λ��ǰ����
		getLocation();
	}

	private void getLocation() {
		//����LocationClient��
		mLocationClient = new LocationClient(this);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ
		option.setCoorType("gcj02");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);//���صĶ�λ���������ַ��Ϣ
		option.setNeedDeviceDirect(true);//���صĶ�λ��������ֻ���ͷ�ķ���
		mLocationClient.setLocOption(option);
		//ע���������
		mLocationClient.registerLocationListener( myListener );
		mLocationClient.start();
		//����λ
		if (mLocationClient != null && mLocationClient.isStarted()){
			int result = mLocationClient.requestLocation();
			Log.d("baidulocation", "requestLocation:"+result);
		}else {
			Log.d("baidulocation", "locClient is null or not started");
		}
	}
	
	/**
	 * ��ѯʡ������
	 */
	private void queryProvinces(){
		if(dao != null){
			List<Province> provinceList = dao.queryProvinces();
			if(provinceList != null && provinceList.size() > 0){
				dataList.clear();
				codeList.clear();
				for(Province p : provinceList){
					dataList.add(p.getName());
					codeList.add(p.getProvinceCode());
				}
				
				adapter.notifyDataSetChanged();
				listView.setSelection(0);
				currentLevel = LEVEL_PROVINCE;
			}else{
				queryFormServer("000000000000","province");
			}
		}
	}
	
	/**
	 * ��ѯʡ���������г�����Ϣ
	 */
	private void queryCitys(){
		if(dao != null){
			List<City> cityList = dao.queryCitys(selectProvinceCode);
			if(cityList != null && cityList.size() > 0){
				dataList.clear();
				codeList.clear();
				for(City c : cityList){
					dataList.add(c.getName());
					codeList.add(c.getCityCode());
				}
				
				adapter.notifyDataSetChanged();
				listView.setSelection(0);
				currentLevel = LEVEL_CITY;
			}else{
				queryFormServer(selectProvinceCode,"city");
			}
		}
	}
	
	/**
	 * ���ݳ��б����ѯ�ؼ�����Ϣ
	 */
	private void queryCountys(){
		if(dao != null){
			List<County> countyList = dao.queryCountys(selectCityCode);
			if(countyList != null && countyList.size() > 0){
				dataList.clear();
				codeList.clear();
				for(County c : countyList){
					dataList.add(c.getName());
					codeList.add(c.getCountyCode());
				}
				dataList.add(selectCityName);
				codeList.add(selectCityCode);
				
				adapter.notifyDataSetChanged();
				listView.setSelection(0);
				currentLevel = LEVEL_COUNTY;
			}else{
				queryFormServer(selectCityCode,"county");
			}
		}
	}
	
	private void queryFormServer(final String code,final String type){
		String url = "http://api.dangqian.com/apidiqu2/api.asp?format=json&id="+code;
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String jsonData = new String(arg2);
				//���ڽӿڷ��ص����ݲ��淶�������һЩ���⴦��
				jsonData = jsonData.substring(jsonData.indexOf("(")+1, jsonData.length()-1);
				boolean result = ParseJSONWithJSONObject.parseJSON(dao,jsonData,type,code);
				if(result){
					if("province".equals(type)){
						queryProvinces();
					}else if("city".equals(type)){
						queryCitys();
					}else if("county".equals(type)){
						queryCountys();
					}
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(CityListActivity.this, "���ݼ���ʧ�ܣ����Ժ����ԣ�", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		if(currentLevel == LEVEL_CITY){
			queryProvinces();
		}else if(currentLevel == LEVEL_COUNTY){
			queryCitys();
		}else{
			finish();
		}
	}
	
	@Override
	protected void onDestroy() {
		mLocationClient.unRegisterLocationListener(myListener);
		mLocationClient.stop();
		super.onDestroy();
	}
	
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
		            return ;
			//int result = location.getLocType();
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			} 
 
			showLocation(location.getLongitude()+","+location.getLatitude());
		}
	}
	
	private void showLocation(String locationInfo){
		tv_location.setText(locationInfo);
	}
}
