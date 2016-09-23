package com.myapp.zin.zinfun.ui;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.utils.UIUtils;

public abstract class LoadingPage extends FrameLayout {
	//正在加载view
	private View loadingView;
	//加载失败view
	private View errorView;
	//加载为空view
	private View emptyView;
	//加载成功view
	private View successedView;
	
	//初始状态码
	private static final int STATE_UNLOAD = 0;
	//正在加载状态码
	private static final int STATE_LOADING = 1;
	//加载失败状态码
	private static final int STATE_LOAD_ERROR = 2;
	//加载为空状态码
	private static final int STATE_LOAD_EMPTY = 3;
	//加成功状态码
	private static final int STATE_SUCCESSED = 4;
	
	
	//private int state = STATE_UNLOAD;
	private int state = STATE_SUCCESSED;


	public LoadingPage(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		//1,正在加载的view
		loadingView = onCreateLodingView();
		//添加正在加载的view在帧布局上,稍后通过请求网络的状态决定显示隐藏
		addView(loadingView);
		
		//2,加载失败view
		errorView = onCreateErrorView();
		addView(errorView);
		
		//3,加载为空view
		emptyView = onCreateEmptyView();
		addView(emptyView);
		
		//4,加载成功view,在正在加载网络数据成功以后,再将其添加到FrameLayout中,所以此处不做处理
		//5,根据当前的状态码决定上诉那个view显示,那个view隐藏
		showPage();
	}

	/**
	 * 根据不同的请求网络状态,判断那种类型的界面显示,那个种类型的界面隐藏
	 */
	private void showPage() {
		if(loadingView!=null){
			//如果现在处理正在加载或者初始状态的是,都是中心转圈的进度条显示
			if(((state == STATE_LOADING) || (state == STATE_UNLOAD))){
				loadingView.setVisibility(View.VISIBLE);
			}else{
				loadingView.setVisibility(View.GONE);
			}
		}
		
		if(errorView!=null){
			//根据当前状态是否为加载出错,决定加载出错的view的显示隐藏状态
			errorView.setVisibility(
					(state == STATE_LOAD_ERROR)?View.VISIBLE:View.GONE);
		}
		
		if(emptyView!=null){
			//根据当前状态是否为加载为空,决定加载为空的view的显示隐藏状态
			emptyView.setVisibility(
					(state == STATE_LOAD_EMPTY)?View.VISIBLE:View.GONE);
		}
		
		//判断加载成功的view是否为空
		if(successedView == null){
			//调用完了此方法后,返回的即使每一个子模块的对应成功获取数据,展示的view对象
			successedView = onCreateSuccessedView();
			//添加到帧布局中
			if(successedView!=null){
				addView(successedView);
			}
		}
		
		if(successedView!=null){
			successedView.setVisibility((state == STATE_SUCCESSED)?View.VISIBLE:View.GONE); 
		}
	}
	
	/**
	 * 请求网络,返回状态
	 */
	public void show(){
		//1,将上一次的请求结果归位
		if(state == STATE_LOAD_EMPTY || state == STATE_LOAD_ERROR || state == STATE_SUCCESSED){
			state = STATE_UNLOAD;
		}
		//2,再去做下一次请求过程
		if(state == STATE_UNLOAD){
			//3,开启子线程请求网络
			new Thread(){
				public void run() {
					//4,LoadingPage中要去做,每一个子类fragment的请求网络过程(首页,游戏,应用,专题......)
					//因为LoadingPage中不知道每个子类界面的具体请求网络方式,请求网络连接地址,请求网络过程中需要参数
					//5,枚举(限制枚举中已有对象类型,三个请求结果的状态码对应三个对象)
					final ResultState onLoad = onLoad();
					//6,状态码给showPage方法,设计UI使用,将操作UI的过程传递到主线程中去做处理
					UIUtils.runInMainThread(new Runnable() {
						@Override
						public void run() {
							if(onLoad!=null){
								state = onLoad.getState();
								//7,根据请求网络的状态码,调用showPage方法,根据状态码去决定哪个界面显示,哪个界面隐藏
								showPage();
							}
						}
					});
				};
			}.start();
		}
	}

	private View onCreateEmptyView() {
		return UIUtils.inflate(R.layout.layout_empty);
	}

	private View onCreateErrorView() {
		return UIUtils.inflate(R.layout.layout_error);
	}

	private View onCreateLodingView() {
		return UIUtils.inflate(R.layout.layout_loading);
	}
	
	/**
	 * @return	返回加载成功的view的方法,因为首页,应用,游戏.....加载数据成功的界面效果都有差异,并且loadingPage也不知道每一个页面具体的xml
	 * 			所以此方法无法实现,于是定义成抽象方法
	 */
	public abstract View onCreateSuccessedView();
	
	/**
	 * @return	返回一个枚举类型的对象,其对象中维护了响应请求网络的状态码
	 */
	public abstract ResultState onLoad() ;
	
	/**
	 *	通过枚举限定对象类型,通过对象类型的构造方法决定状态码的值
	 */
	public enum ResultState{
		STATE_ERROR(2),
		STATE_EMPTY(3),
		STATE_SUCCESSED(4),
		STATE_LOADING(1);


		private int state;
		private ResultState(int state){
			this.state = state;
		}
		
		public int getState(){
			return state;
		}
	}
	
}
