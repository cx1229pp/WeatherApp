package com.weather.app.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class ViewChange extends FrameLayout {
	public static final int MainMenu=0, ChooseMap=1, MapOptions=2, 
	        SetLocation=3, view=4, EditMap=5, CreateMap=6;
	private int current; //the current view
	private View[] views= new View[7];
	
	public ViewChange(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ViewChange(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ViewChange(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	    for(int i = 0; i < getChildCount(); i++){
	                getChildAt(i).layout(0, 0, getWidth(), getHeight());
	            }
	}
	    /**Sets the view of the view group, only one view can be viewed at a time*/
	public void setView(int v){
	            current=v;
	    for (int i=0; i<views.length; i++){
	        if (v==i){
	            views[i].setVisibility(View.VISIBLE);
	        } else{
	            views[i].setVisibility(View.GONE);
	        }
	    }
	}
}
