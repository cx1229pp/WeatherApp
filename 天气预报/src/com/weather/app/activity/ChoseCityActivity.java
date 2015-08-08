package com.weather.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.weather.app.R;
import com.weather.app.customAdapter.WeatherGridAdapter;
import com.weather.app.db.WeatherDao;

import java.util.List;

/**
 * 城市管理页，9宫格展示已选择城市
 */
public class ChoseCityActivity extends Activity implements OnClickListener{
	//private ImageView iv_back;
	private ImageView iv_add;
	private ImageView iv_refresh;
	private ImageView iv_edit;
	private WeatherDao dao;
	private GridView gridView;
	private List<String> selectCitys;
	private WeatherGridAdapter adapter;
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chose_city_layout);
		init();
	}
	
	private void setData(){
		selectCitys = dao.querySelectCitys();
		if(selectCitys != null && selectCitys.size() > 0){
			adapter = new WeatherGridAdapter(selectCitys,this);
			gridView.setAdapter(adapter);
		}
	}
	
	private void setItemClick(){
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position < selectCitys.size()){
					String cityName = selectCitys.get(position);
					/**
					 * 如果显示删除按钮，则点击删除；否则为选中城市查看天气
					 */
					if(adapter.getIsShowDelete()){
						dao.deleteSelectCity(cityName);
						setData();
					}else{
						Intent mainActivityIntent = new Intent(ChoseCityActivity.this,MainActivity.class);
						mainActivityIntent.putExtra("cityName", cityName);

						SharedPreferences sp = getSharedPreferences("weatherSharedPreferences", Context.MODE_PRIVATE);
						Editor editor =  sp.edit();
						editor.putString("cityName",cityName);
						editor.commit();

						setResult(-1, mainActivityIntent);
						finish();
					}
				}else{
					/**
					 * 给添加按钮设置点击事件
					 */
					Intent intent = new Intent(context,CityListActivity.class);
					startActivityForResult(intent,1);
				}
			}
		});

		gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				//长按显示删除按钮
				adapter.setIsShowDelete(adapter.getIsShowDelete() ? false : true);
				return true;
			}
		});
	}
	
	private void init(){
		iv_add = (ImageView) findViewById(R.id.iv_add);
		iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
		iv_edit = (ImageView) findViewById(R.id.iv_edit);
		gridView = (GridView) findViewById(R.id.gridView_choiceCity);
		dao = WeatherDao.getInstance(this);

		iv_refresh.setOnClickListener(this);
		iv_edit.setOnClickListener(this);
		iv_add.setOnClickListener(this);

		setData();
		setItemClick();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_add:
				Intent intent = new Intent(this,CityListActivity.class);
				startActivityForResult(intent,1);
				break;
			case R.id.iv_refresh:

				break;

			case R.id.iv_edit:
				adapter.setIsShowDelete(adapter.getIsShowDelete() ? false : true);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//返回-1表示已选择过，提示重新选择
		if(resultCode == -1){
			Toast.makeText(this, getString(R.string.HintInfoOne), Toast.LENGTH_SHORT).show();
		}else{
			setData();
		}
	}
}
