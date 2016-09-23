package com.myapp.zin.zinfun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.activity.DetailActivity;
import com.myapp.zin.zinfun.adapter.MyRecyclerViewAdapter;
import com.myapp.zin.zinfun.entity.SiwammBean;
import com.myapp.zin.zinfun.ui.LoadingPage;
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
 * Created by ZIN on 2016/3/30.
 */
public class SiwaimmFragment extends BaseFragment{

    private View recylerViewLayout;
    private  SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MyRecyclerViewAdapter mAdapter;
    public int lastVisibleItem;
    private  int page =1;
    private boolean isPrepare = false;

    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            mSwipeLayout.setRefreshing(false);

            switch (msg.what){
                case 1:{

                    List<SiwammBean.Tngou> datas = (ArrayList<SiwammBean.Tngou>) msg.obj;
                    LogUtils.i("5执行handler");
                    if (mAdapter==null){
                        mAdapter=new MyRecyclerViewAdapter(datas);
                    }
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                    if (page==1){
                        mAdapter.resetData(datas);

                        LogUtils.i("svroll0");
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(0);

                    }else {
                        mAdapter.addDataAtBottom(datas);

                        LogUtils.i("scrollposition"+mAdapter.getItemCount());
                        mAdapter.notifyDataSetChanged();
                       // mRecyclerView.scrollToPosition(mAdapter.getItemCount());
                    }
                    mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getDataFromNet(1);
    }

    @Override
    public LoadingPage.ResultState onSubLoad() {

        return LoadingPage.ResultState.STATE_SUCCESSED;
    }

    @Override
    public View onCreateSubSuccessedView() {
        LogUtils.i("siwai-onCreateSubSuccessedView");
        initView();
        setSwipeFlashLayout();
        setRecyclerView();
        isPrepare = true;
       // getDataFromNet(1);
        return recylerViewLayout;
    }

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
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {




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
                    mSwipeLayout.setRefreshing(true);

                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    Toast.makeText(UIUtils.getContext(),"加载更多...",Toast.LENGTH_SHORT);
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
        /*mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
               // mAdapter.setHasFooter(true);
                page=page+1;

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
        HttpUrl url = HttpUrl.parse("http://www.tngou.net/tnfs/api/list").newBuilder()
                .addQueryParameter("id", "3")
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
                List<SiwammBean.Tngou> resultdatas =new ArrayList<>();


                Message message=handler.obtainMessage();
                if (siwammBean.getTngou()!=null&&siwammBean.getTngou().size()!=0){

                    resultdatas=siwammBean.getTngou();
                    String title = resultdatas.get(0).getTitle();
                    LogUtils.i("title"+title);
                    message.obj=resultdatas;
                    message.what=1;
                }else{
                    message.what=0;

                }


                handler.sendMessage(message);



            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint() && isPrepare){
            getDataFromNet(1);
            mSwipeLayout.setRefreshing(true);
        }


    }
}
