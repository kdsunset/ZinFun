package com.myapp.zin.zinfun.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.entity.SiwammBean;
import com.myapp.zin.zinfun.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ZIN on 2016/3/31.
 */
public class SiwammRecyclerAdapter  extends BaseLoadMoreRecyclerAdapter<SiwammBean.Tngou,SiwammRecyclerAdapter.ItemViewHolder>{

    /*
     *1.继承RecyclerViewAdapter类，需重写 onCreateViewHolder，onBindViewHolder，getItemCount
     * 三个方法
     */
    /**
     * 为RecyclerView添加item的点击事件
     * 1.在MyAdapter中定义接口,模拟ListView的OnItemClickListener：
     * 2. 声明一个这个接口的变量( OnItemClickListener mListener;)，并注册监听（ itemView.setOnClickListener）
     * 3.接口的onItemClick()中的v.getTag()方法，这需要在onBindViewHolder()方法中设置和item相关的数据
     * 4.最后暴露给外面的调用者，定义一个设置Listener的方法（setOnItemClickListener）：
     */
    private OnItemClickListener mListener;
    @Override
    public SiwammRecyclerAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mmlist_item, parent, false);
        ItemViewHolder vh = new ItemViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v, (String) view.getTag());//注意这里使用getTag方法获取数据
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindItemViewHolder(SiwammRecyclerAdapter.ItemViewHolder holder, int position) {
        /*  String string=datas.get(position);
        holder.setTvTitle(string) ;*/
        LogUtils.i("adapter");
        LogUtils.i("adapter"+getItem(position).getTitle());
        LogUtils.i("adapter"+getItem(position).getTime());
        LogUtils.i("adapter"+getItem(position).getImg());
        String title=getItem(position).getTitle();

        long longTime=getItem(position).getTime();
        String imageUrl="http://tnfs.tngou.net/image"+getItem(position).getImg();


        holder.tvTitle.setText(title);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        holder.tvDate.setText(sdf.format(longTime));
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_anim)
                .showImageOnFail(R.drawable.shibai)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageView mImageView=holder.imageView;

        ImageLoader.getInstance().displayImage(imageUrl, mImageView, options);

        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(getItem(position).getId()+"");


    }

    /**
     * @param datas 展示的数据
     */
    public SiwammRecyclerAdapter(List<SiwammBean.Tngou> datas){

        appendToList(datas);
    }
    /**
     *自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    static  class ItemViewHolder extends  RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvDate;
        ImageView imageView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle= (TextView) itemView.findViewById(R.id.tvTitle);
            tvDate= (TextView) itemView.findViewById(R.id.tvDate);
            imageView= (ImageView) itemView.findViewById(R.id.imageView);

        }
       /* public  void  setTvTitle(String t){
            tvTitle.setText(t);
        }*/

    }
    /**
     * RecyclerView的Item的监听器接口
     *
     */
    public interface OnItemClickListener
    {
        void OnItemClick(View view, String data);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }


}
