package com.myapp.zin.zinfun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.adapter.ResultImgMasonryAdapter;
import com.myapp.zin.zinfun.entity.SosoSearchBean;
import com.myapp.zin.zinfun.ui.SpacesItemDecoration;
import com.myapp.zin.zinfun.utils.LogUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SosoSearchImgActivity extends AppCompatActivity {

    private View recylerViewLayout;
    private SwipeRefreshLayout mSwipeLayout;
    private LinearLayoutManager mLinearLayoutManager;
    public int lastVisibleItem;
    private  int page =1;
    private boolean isPrepare = false;
    private RecyclerView mRecyclerView;
    private  String words;
    private ResultImgMasonryAdapter mAdapter;

    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){

                List<SosoSearchBean.Item> datas = (List<SosoSearchBean.Item>) msg.obj;
                LogUtils.i("size"+datas.size());
                if (mAdapter==null){
                    mAdapter = new ResultImgMasonryAdapter(datas);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }else {
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                mAdapter.setOnItemClickListener(new ResultImgMasonryAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position, ArrayList<String> data) {
                        Intent intent=new Intent(SosoSearchImgActivity.this,ShowImageActivity.class);
                        intent.putStringArrayListExtra("URLLIST", data);
                        intent.putExtra("POSITION",position);


                        startActivity(intent);


                    }
                });

            }
        }
    };
    private  StaggeredGridLayoutManager staggeredGridLayoutManager;
    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult_detail);
        initView();
        mRecyclerView = (RecyclerView) findViewById(R.id.detaiRecyclerView);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        Intent intent=getIntent();
        words = intent.getStringExtra("WORD");
        setToolBar();


        //设置两行的瀑布流布局
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        //设置adapter


        //mAdapter = new MyRecyclerViewAdapter(getTitleData());
       /* mRecyclerView.setAdapter(mAdapter);*/
        //每个item高度一致，可设置为true，提高性能
        mRecyclerView.setHasFixedSize(true);
        //分隔线

        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);

        // mRecyclerView.addItemDecoration(new MyItemDecoration(UIUtils.getContext()));
        ///为每个item增加响应事件
        getDataFromNet();
    }

    private void initView() {
       // recylerViewLayout = UIUtils.inflate(R.layout.fragment_siwamm);
        mSwipeLayout = (SwipeRefreshLayout) recylerViewLayout.findViewById(R.id.swipe_container);
        mRecyclerView = (RecyclerView)recylerViewLayout.findViewById(R.id.recyclerView);
    }

    private void setToolBar() {
        mToolbar.setTitle(words);// 标题的文字需在setSupportActionBar之前，不然会无效

        setSupportActionBar(mToolbar);
       // getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       /* // toolbar.setSubtitle("副标题");
                setSupportActionBar(mToolbar);
        *//* 这些通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbar);之后，不然就报错了 *//*
        // getSupportActionBar().setTitle("标题");
        // getSupportActionBar().setSubtitle("副标题");
        // getSupportActionBar().setLogo(R.drawable.ic_launcher);

        *//* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过Activity的onOptionsItemSelected回调方法来处理 *//*
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this, "action_settings", 0).show();
                        break;
                    case R.id.action_share:
                        Toast.makeText(MainActivity.this, "action_share", 0).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });*/

    }

    private void getDataFromNet(){

        //http://pic.sogou.com/pics?query=%E8%90%9D%E8%8E%89&reqType=ajax&reqFrom=result
        LogUtils.i("搜索图片");
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        HttpUrl url = HttpUrl.parse("http://pic.sogou.com/pics").newBuilder()
                .addQueryParameter("query", words)
                .addQueryParameter("reqType","ajax")
                .addQueryParameter("reqFrom","result")
                .build();
        LogUtils.i(url.toString());

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
                LogUtils.i("onFailure" + e);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                String resultJson = response.body().string();
                LogUtils.i("imglist"+resultJson);
                Gson gson=new Gson();
                SosoSearchBean sosoSearchBean = gson.fromJson(resultJson, SosoSearchBean.class);
                List<SosoSearchBean.Item> items = sosoSearchBean.getItems();

                Message message=handler.obtainMessage();
                message.what=1;
                message.obj=items;
                handler.sendMessage(message);

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
