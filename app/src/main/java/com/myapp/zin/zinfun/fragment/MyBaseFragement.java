package com.myapp.zin.zinfun.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.activity.DetailActivity;
import com.myapp.zin.zinfun.adapter.SiwammRecyclerAdapter;
import com.myapp.zin.zinfun.entity.SiwammBean;
import com.myapp.zin.zinfun.ui.MyItemDecoration;
import com.myapp.zin.zinfun.utils.LogUtils;
import com.myapp.zin.zinfun.utils.UIUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZIN on 2016/4/1.
 */
public abstract class MyBaseFragement  extends Fragment {
    private View recylerViewLayout;
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private SiwammRecyclerAdapter mAdapter;
    public int lastVisibleItem;
    private  int page =1;
    private boolean isPrepare = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  return this.onSubCreateView();
        initView();
        return recylerViewLayout;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // this.onSubActivityCreated();
        setSwipeFlashLayout();
        setRecyclerView();

        getDataFromNet(1);
    }
/*
    public  abstract View  onSubCreateView();
    public  abstract void  onSubActivityCreated();*/

    /*@Override
    public View onSubCreateView() {
        initView();
        return recylerViewLayout;
    }

    @Override
    public void onSubActivityCreate() {
        setSwipeFlashLayout();
        setRecyclerView();

        getDataFromNet(1);

    }*/
    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            mSwipeLayout.setRefreshing(false);

            switch (msg.what){
                case 1:{
                    SiwammBean s= (SiwammBean) msg.obj;

                    List<SiwammBean.Tngou> datas =s.getTngou();
                    LogUtils.i("5执行handler");
                    if (mAdapter==null){
                        mAdapter=new SiwammRecyclerAdapter(datas);
                        mAdapter.setHasFooter(true);

                    }
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                    LogUtils.i("datas大小B="+datas.size());

                    if (datas!=null&&datas.size()!=0){
                        mAdapter.setHasMoreData(true);
                    }else {
                        mAdapter.setHasMoreDataAndFooter(false,true);
                        LogUtils.i("datas为0执行了");
                    }

                    if (page==1){
                        mAdapter.clear();
                        mAdapter.appendToList(datas);

                        LogUtils.i("svroll0");
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(0);

                    }else {
                        // mAdapter.setHasMoreData(true);
                        int position = mAdapter.getItemCount();
                        mAdapter.appendToList(datas);

                        LogUtils.i("scrollposition"+position);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(position);
                    }
                    mAdapter.setOnItemClickListener(new SiwammRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, String data) {
                            //Toast.makeText(UIUtils.getContext(), "data:" + data, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
                            intent.putExtra("ID", data);
                            startActivity(intent);
                        }
                    });
                    break;
                }
                case 0:{

                    break;
                }


            }


/*
                if (mAdapter == null) {
                    mAdapter = new MyRecyclerViewAdapter(datas);

                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                }else {

                     mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                }
             *//*   if (mAdapter==null){
                    mAdapter=new MyRecyclerViewAdapter(datas);
                    mRecyclerView.setAdapter(mAdapter);
                    LogUtils.i("6new adapter");
                }*//*
                //滚动到列首部--->这是一个很方便的api，可以滑动到指定位置
                if (page == 1) {
                    mAdapter.resetData(datas);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.scrollToPosition(0);
                    LogUtils.i("8 page=1");

                } else {
                   // mAdapter.setHasMoreData(true);
                    mAdapter.addDataAtBottom(datas);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount());

                }*/





        }
    };



  /*  @Override
    public LoadingPage.ResultState onSubLoad() {

        return LoadingPage.ResultState.STATE_SUCCESSED;
    }

    @Override
    public View onCreateSubSuccessedView() {
        LogUtils.i("siwai-onCreateSubSuccessedView");
        initView();
        setSwipeFlashLayout();
        setRecyclerView();

        getDataFromNet(1);
        return recylerViewLayout;
    }
*/
    /**
     * 设置刷新模块的方法
     */
    private void setSwipeFlashLayout() {
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                page=1;

                getDataFromNet(page);

            }
        });
    }

    /**
     * 设置RecyclerView的方法
     */
    private void setRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        //mAdapter = new MyRecyclerViewAdapter(getTitleData());
       /* mRecyclerView.setAdapter(mAdapter);*/
        //每个item高度一致，可设置为true，提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        //分隔线
        mRecyclerView.addItemDecoration(new MyItemDecoration(UIUtils.getContext()));
        ///为每个item增加响应事件
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int currentScrollState = newState;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                        (lastVisibleItem) >= totalItemCount - 1))  {


                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    page=page+1;
                    getDataFromNet(page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });

      /*  mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                page=page+1;
                mAdapter.setHasMoreData(true);
                mAdapter.setHasFooter(true);
                LogUtils.i("执行加载更多方法"+page);
                getDataFromNet(page);



            }
        });*/


    }

    /**
     * init组件的方法
     */
    private void initView() {

        recylerViewLayout = UIUtils.inflate(R.layout.fragment_siwamm);
        mSwipeLayout = (SwipeRefreshLayout) recylerViewLayout.findViewById(R.id.swipe_container);
        mRecyclerView = (RecyclerView)recylerViewLayout.findViewById(R.id.recyclerView);
    }


    private void getDataFromNet(int page){

        LogUtils.i("0请求网络获取美女图片列表json");
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        HttpUrl url = HttpUrl.parse(setURL()).newBuilder()
                .addQueryParameter("id", setId())
                .addQueryParameter("page", page+"")
                .addQueryParameter("rows","10")
                .build();
        LogUtils.i("1.网络地址"+url.toString());

        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtils.i("3.onFailure" + e);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                String resultJson = response.body().string();
                LogUtils.i("4.请求结果"+resultJson);
                Gson gson=new Gson();
                SiwammBean siwammBean = gson.fromJson(resultJson, SiwammBean.class);
                List<SiwammBean.Tngou> resultdatas =new ArrayList<SiwammBean.Tngou>();


                Message message=handler.obtainMessage();
                message.what=1;
               /* if (siwammBean.getTngou()!=null&&siwammBean.getTngou().size()!=0){

                    resultdatas=siwammBean.getTngou();
                    String title = resultdatas.get(0).getTitle();
                    LogUtils.i("title"+title);
                  //  message.obj=resultdatas;
                    message.what=1;
                }else{
                    message.what=0;

                }*/
                message.obj=siwammBean;
                handler.sendMessage(message);



            }
        });
    }

    protected abstract String setURL();

    protected abstract String setId();


}
