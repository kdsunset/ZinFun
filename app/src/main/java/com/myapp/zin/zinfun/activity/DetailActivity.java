package com.myapp.zin.zinfun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.adapter.MasonryAdapter;
import com.myapp.zin.zinfun.entity.DetailListBean;
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

public class DetailActivity extends Activity {

    private RecyclerView mRecyclerView;
    private  String id;
    private MasonryAdapter mAdapter;

    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                DetailListBean s= (DetailListBean) msg.obj;
                List<DetailListBean.Picture> datas=  s.getList();
                LogUtils.i("size"+datas.size());
                if (mAdapter==null){
                    mAdapter = new MasonryAdapter(datas);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }else {
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                mAdapter.setOnItemClickListener(new MasonryAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view,int position, ArrayList<String> data) {
                        Intent intent=new Intent(DetailActivity.this,ShowImageActivity.class);
                        intent.putStringArrayListExtra("URLLIST", data);
                        intent.putExtra("POSITION",position);


                        startActivity(intent);


                    }
                });

            }
        }
    };
    private  StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mRecyclerView = (RecyclerView) findViewById(R.id.detaiRecyclerView);
        Intent intent=getIntent();
         id = intent.getStringExtra("ID");
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



    private void getDataFromNet(){

        LogUtils.i("请求某个主题下的所有图片");
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        HttpUrl url = HttpUrl.parse("http://www.tngou.net/tnfs/api/show").newBuilder()
                .addQueryParameter("id", id)
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
                DetailListBean detailListBean = gson.fromJson(resultJson, DetailListBean.class);
                String title = detailListBean.getList().get(0).getSrc();
                LogUtils.i("src"+title);
                Message message=handler.obtainMessage();
                message.what=1;
                message.obj=detailListBean;
                handler.sendMessage(message);

            }
        });
    }
}
