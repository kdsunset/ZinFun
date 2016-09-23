package com.myapp.zin.zinfun.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.zin.zinfun.ui.LoadingPage;
import com.myapp.zin.zinfun.utils.LogUtils;
import com.myapp.zin.zinfun.utils.UIUtils;

public abstract class BaseFragment extends Fragment {
	private LoadingPage mLoadingPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mLoadingPage = new LoadingPage(UIUtils.getContext()){
			@Override
			public View onCreateSuccessedView() {
				//BaseFragment依然是不知道每一个子模块(首页,游戏,应用....),子模块布局效果,所以要再进行一次抽象
				return BaseFragment.this.onCreateSubSuccessedView();
			}

			@Override
			public LoadingPage.ResultState onLoad() {
				//BaseFragment依然是不知道子类fragment具体请求网络操作,所以继续抽象
				LogUtils.i("basefragment-onload");
				return BaseFragment.this.onSubLoad();
			}
		};
		//返回挂在了四种界面展示情况某一种的帧布局对象
		return mLoadingPage;
	}



	/**
	 * @return	子类请求网络的抽象方法
	 */
	public abstract LoadingPage.ResultState onSubLoad() ;
	/**
	 * @return	在子类fragment中返回每一个子类fragment的界面效果方法
	 */
	public abstract View onCreateSubSuccessedView() ;
	
	//添加一个请求网络的触发方法
	public void subShow(){
		if(mLoadingPage!=null){
			mLoadingPage.show();
		}
	}
}
