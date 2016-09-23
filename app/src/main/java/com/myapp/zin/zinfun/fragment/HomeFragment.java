package com.myapp.zin.zinfun.fragment;


import android.view.View;
import android.widget.TextView;

import com.myapp.zin.zinfun.ui.LoadingPage;
import com.myapp.zin.zinfun.utils.LogUtils;
import com.myapp.zin.zinfun.utils.UIUtils;


public class HomeFragment extends BaseFragment {

	@Override
	public View onCreateSubSuccessedView() {
		LogUtils.i("homefragment");
		TextView textView = new TextView(UIUtils.getContext());
		textView.setText("首页");
		return textView;
	}

	@Override
	public LoadingPage.ResultState onSubLoad() {
		LogUtils.i("homefragment-onsubload");
		return LoadingPage.ResultState.STATE_SUCCESSED;
	}

}
