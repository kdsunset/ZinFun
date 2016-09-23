package com.myapp.zin.zinfun.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.entity.SosoSearchBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZIN on 2016/4/1.
 */
public class ResultImgMasonryAdapter extends RecyclerView.Adapter<ResultImgMasonryAdapter.MasonryView>{
    private List<SosoSearchBean.Item> datas;

    int position;
    public ResultImgMasonryAdapter(List<SosoSearchBean.Item> list) {
        datas =list;
    }

    private OnItemClickListener mListener;
    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.masonry_item, viewGroup, false);
        final MasonryView vh = new MasonryView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v,vh.getLayoutPosition(),(ArrayList<String>) view.getTag());//注意这里使用getTag方法获取数据



                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(MasonryView masonryView, int position) {
      //  masonryView.imageView.setImageResource(datas.get(position).getImg());


        String imageUrl=datas.get(position).getPic_url();



        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_anim)
                .showImageOnFail(R.drawable.shibai)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageView mImageView=masonryView.imageView;

        ImageLoader.getInstance().displayImage(imageUrl, mImageView, options);

        //将数据保存在itemView的Tag中，以便点击时进行获取

        masonryView.itemView.setTag(getImageList());


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class MasonryView extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MasonryView(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.masonry_item_img );



        }


    }

    /**
     * RecyclerView的Item的监听器接口
     *
     */
    public interface OnItemClickListener
    {
        void OnItemClick(View view, int position, ArrayList<String> data);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    public List<String> getImageList() {
        List<String> imagelist=new ArrayList<>();
        for (SosoSearchBean.Item s:datas){

            imagelist.add(s.getPic_url());
        }

        return imagelist;
    }
}