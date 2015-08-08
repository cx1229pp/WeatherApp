package com.weather.app.customAdapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.weather.app.R;

import java.util.List;

/**
 * Created by chenxue on 2015/7/12.
 * 自定义选择城市gridview Adapter，最后加入添加按钮
 */
public class WeatherGridAdapter extends BaseAdapter {
    private List<String> list;
    /*
    是否显示删除按钮
     */
    private boolean isShowDelete = false;
    private Context contenxt;

    public WeatherGridAdapter(List<String> list, Context contenxt) {
        this.list = list;
        this.contenxt = contenxt;
    }

    public boolean getIsShowDelete(){
        return this.isShowDelete;
    }

    /**
     * 设置是否显示删除按钮
     * @param isShowDelete
     */
    public void setIsShowDelete(boolean isShowDelete){
        this.isShowDelete = isShowDelete;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = 0;
        if(list != null && list.size() > 0){
            //在gridview最后加入添加按钮，所以长度加1
            count = list.size()+1;
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String cityName = "";
        if(position < list.size()){
            cityName = list.get(position);
        }
        View view;
        ViewHandler viewHandler;

        if(convertView == null){
            viewHandler = new ViewHandler();
            if(position < list.size()){
                view = View.inflate(contenxt, R.layout.chose_city_item,null);

                viewHandler.tv_choseCity = (TextView)view.findViewById(R.id.tv_choseCity);
                viewHandler.tv_weather = (TextView)view.findViewById(R.id.tv_weather);
                viewHandler.setText(cityName,"");
                viewHandler.iv_delete = (ImageView)view.findViewById(R.id.iv_delete);
            }else{
                /**
                 * 加入添加按钮
                 */
                ImageView iv_add = new ImageView(contenxt);
                iv_add.setImageDrawable(contenxt.getResources().getDrawable(R.drawable.add));
                //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100,100);
                //lp.gravity = Gravity.CENTER;
                iv_add.setLayoutParams(new GridView.LayoutParams(150, 150));

                view = iv_add;
                viewHandler.iv_add = iv_add;
            }

            view.setTag(viewHandler);
        }else{
            view = convertView;
            viewHandler = (ViewHandler)view.getTag();
            if(position < list.size()){
                viewHandler.setText(cityName,"");
                viewHandler.iv_delete.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);
            }
        }

        return view;
    }

    private class ViewHandler{
        TextView tv_choseCity;
        TextView tv_weather;
        ImageView iv_delete;
        ImageView iv_add;

        private void setText(String cityName,String weather){
            tv_choseCity.setText(cityName);
            tv_weather.setText(weather);
        }
    }
}
