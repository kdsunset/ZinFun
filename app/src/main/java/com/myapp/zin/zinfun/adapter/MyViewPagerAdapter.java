package com.myapp.zin.zinfun.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyViewPagerAdapter extends PagerAdapter {
	ArrayList<View> view = null;

	public MyViewPagerAdapter() {
		view = new ArrayList<>();
	}

	public void changData(ArrayList<View> view) {
		this.view = view;
		notifyDataSetChanged();
	}

	// ��ȡ��ͼ����
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return view.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(view.get(position));
		return view.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(view.get(position));
	}

}
