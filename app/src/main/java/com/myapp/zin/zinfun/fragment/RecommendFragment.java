package com.myapp.zin.zinfun.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.adapter.RecommendRecyclerAdapter;
import com.myapp.zin.zinfun.entity.ADInfo;
import com.myapp.zin.zinfun.entity.CardViewContent;
import com.myapp.zin.zinfun.entity.SiwammBean;
import com.myapp.zin.zinfun.ui.MyItemDecoration;
import com.myapp.zin.zinfun.ui.widget.banner.BannerView;
import com.myapp.zin.zinfun.utils.UIUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZIN on 2016/4/3.
 */
public class RecommendFragment extends Fragment {

    private View recylerViewLayout;
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecommendRecyclerAdapter mAdapter;
    private Context context;
    BannerView bannerView;
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();


    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  return this.onSubCreateView();

        initView();
        return recylerViewLayout;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSwipeFlashLayout();
        setRecyclerView();
        refreshData();

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


                refreshData();

            }
        });
    }

    private void refreshData() {


        String urlSex = "http://www.tngou.net/tnfs/api/news?&rows=1&classify=1";
        String urlRihan = "http://www.tngou.net/tnfs/api/news?&rows=1&classify=2";
        String urlSiwa = "http://www.tngou.net/tnfs/api/news?&rows=1&classify=3";
        String urlXiezhen = "http://www.tngou.net/tnfs/api/news?&rows=1&classify=4";
       // String urlQingchun = "http://www.tngou.net/tnfs/api/news?&rows=1&classify=5";


        List<String> urls = new ArrayList<>();
        urls.add(urlSex);
        urls.add(urlRihan);
        urls.add(urlSiwa);
        urls.add(urlXiezhen);
       // urls.add(urlQingchun);

        new GetDataTask().execute(urls);


    }

    class GetDataTask extends AsyncTask<List<String>, Void, List<String>> {

        @Override
        protected List<String> doInBackground(List<String>... lists) {
            List<String> imageUrls = new ArrayList<>();
            Gson gson = new Gson();
            for (String urlItem : lists[0]) {
                OkHttpClient mOkHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(urlItem)
                        .build();
                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        SiwammBean siwammBean = gson.fromJson(string, SiwammBean.class);
                        String urlext=siwammBean.getTngou().get(0).getImg();
                        String imgUrl = "http://tnfs.tngou.net/image" +urlext ;

                        imageUrls.add(imgUrl);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            int size=imageUrls.size();
            return imageUrls;

        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);

            List<CardViewContent> datas = new ArrayList<>();
            String titileSex = "性感";
            String titileRihan = "日韩";
            String titileSiwa = "丝袜";
            String titileXie = "写真";
           // String titileQing = "清纯";
            String titileContent = "美女";
            List<String> tips=new ArrayList<>();

            tips.add("美女");
            tips.add("动漫");
           /* tips.add(titileSiwa);
            tips.add(titileXie);*/
            //tips.add(titileQing);
            CardViewContent cardItem1 = new CardViewContent(strings.get(0), strings.get(1), titileSex, titileRihan, titileContent, titileContent, 1, 2);
            CardViewContent cardItem2 = new CardViewContent(strings.get(2), strings.get(3), titileSiwa, titileXie, titileContent, titileContent, 3, 4);
            datas.add(cardItem1);
            datas.add(cardItem2);
            context =getContext();
            mAdapter = new RecommendRecyclerAdapter(context,datas,tips);
            int tipssize=tips.size();
            int datasize=datas.size();
            mRecyclerView.setAdapter(mAdapter);
            mSwipeLayout.setRefreshing(false);

        }
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

    }

    /**
     * init组件的方法
     */
    private void initView() {

       // recylerViewLayout = UIUtils.inflate(R.layout.fragment_recommd);
        recylerViewLayout=LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.fragment_recommd,null );
        mSwipeLayout = (SwipeRefreshLayout) recylerViewLayout.findViewById(R.id.swipe_containerInRecom);

        bannerView = (BannerView) recylerViewLayout.findViewById(R.id.bannerView);
      /*  String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
                "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
                "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
                "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
        // 广告语
        String[] bannerTexts = {"因为专业 所以卓越", "坚持创新 行业领跑", "诚信 专业 双赢", "精细 和谐 大气 开放"};
        List<String> urls=new ArrayList<>();
        List<String> titiles=new ArrayList<>();

        for (int i=0;i<imageUrls.length;i++){
            urls.add(imageUrls[i]);
            titiles.add(bannerTexts[i]);
        }
        bannerView.setBannerImgUrlList(urls);
        bannerView.setBannerTextList(titiles);*/


        mRecyclerView = (RecyclerView) recylerViewLayout.findViewById(R.id.recyclerViewInRecom);
    }

}
