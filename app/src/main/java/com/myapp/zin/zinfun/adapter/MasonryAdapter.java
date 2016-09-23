package com.myapp.zin.zinfun.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.entity.DetailListBean;
import com.myapp.zin.zinfun.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZIN on 2016/4/1.
 */
public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView>{
    private List<DetailListBean.Picture> datas;

    int position;
    public MasonryAdapter(List<DetailListBean.Picture> list) {
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
                    mListener.OnItemClick(v,vh.getLayoutPosition(),(ArrayList<String>) view.getTag(R.id.tag_urllist));//注意这里使用getTag方法获取数据
                    LogUtils.i("getAdapterPosition"+vh.getAdapterPosition());
                    LogUtils.i("getLayoutPosition"+vh.getLayoutPosition());
                    LogUtils.i("getPosition"+vh.getPosition());
                    LogUtils.i("getOldPosition"+vh.getOldPosition());


                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(MasonryView masonryView, int position) {
      //  masonryView.imageView.setImageResource(datas.get(position).getImg());


        String imageUrl="http://tnfs.tngou.net/image"+datas.get(position).getSrc();



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

        masonryView.itemView.setTag(R.id.tag_urllist,getImageList());
        masonryView.imageView.setTag(R.id.tag_position,position);


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
        void OnItemClick(View view,int position,ArrayList<String> data);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    public List<String> getImageList() {
        List<String> imagelist=new ArrayList<>();
        for (DetailListBean.Picture s:datas){

            imagelist.add("http://tnfs.tngou.net/image"+s.getSrc());
        }

        return imagelist;
    }
}