package com.myapp.zin.zinfun.fragment;

import android.view.View;

/**
 * Created by ZIN on 2016/3/30.
 */
public class QingchunmmFragment3 extends BaseFragment3{
    @Override
    public View onSubCreateView() {
        return null;
    }

    @Override
    public void onSubActivityCreate() {

    }




  /*  private void getDataFromNet(){

        LogUtils.i("请求网络获取美女图片列表json");
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        HttpUrl url = HttpUrl.parse("http://www.tngou.net/tnfs/api/list").newBuilder()
                .addQueryParameter("id", "4")
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
                LogUtils.i(resultJson);
            }
        });
    }*/

}
