package com.myapp.zin.zinfun.fragment;


import android.view.View;
import android.widget.TextView;

import com.myapp.zin.zinfun.utils.LogUtils;
import com.myapp.zin.zinfun.utils.UIUtils;


public class HomeFragment3 extends BaseFragment3 {




	@Override
	public View onSubCreateView() {
		LogUtils.i("homefragment");
		TextView textView = new TextView(UIUtils.getContext());
		textView.setText("首页");
		return textView;
	}

	@Override
	public void onSubActivityCreate() {

	}
}
