package com.myapp.zin.zinfun.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.entity.CardViewContent;
import com.myapp.zin.zinfun.utils.UIUtils;

import java.util.List;

/**
 * Created by ZIN on 2016/4/3.
 */
public class RecommendRecyclerAdapter extends  BaseRecommendRecyclerAdapter<CardViewContent,RecommendRecyclerAdapter.ContentViewHolder> {


    private OnItemLeftClickListener mListenerLeft;
    private OnItemRighttClickListener mListenerRight;
    private List<CardViewContent> mCardItemDatas;
    private  List<String> mTips ;
    private Context context;

    public RecommendRecyclerAdapter(Context context,List<CardViewContent> datas,List<String> tips){
        super(datas,tips);
        this.mCardItemDatas=datas;
        this.mTips=tips;
        this.context=context;

    }
   /* @Override
    protected void setTipViewHolder(RecyclerView.ViewHolder holder, int position) {


    }*/


    @Override
    public ContentViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        Fresco.initialize(UIUtils.getContext());
       // final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_recommend, parent, false);
        final View view = LayoutInflater.from(context).inflate(R.layout.itemview_recommend, parent, false);
        final ContentViewHolder vh = new ContentViewHolder(view);
       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v, (String) view.getTag());//注意这里使用getTag方法获取数据
                }
            }
        });*/
        vh.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListenerLeft!=null){
                    mListenerLeft.OnItemClick(view, (String) vh.cardView1.getTag());
                }
            }
        });
        vh.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListenerRight!=null){
                    mListenerRight.OnItemClick(view,(String) vh.cardView2.getTag());
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindItemViewHolder(ContentViewHolder holder, int position) {
        CardViewContent cardItemData = mCardItemDatas.get(position/2);
        Uri uri1 = Uri.parse(cardItemData.getImgUrl1());
        Uri uri2 = Uri.parse(cardItemData.getImgUrl2());
        String title1 = cardItemData.getTitle1();
        String title2 = cardItemData.getTitle2();
        String content1 = cardItemData.getContent1();
        String content2 = cardItemData.getContent2();
        int classify1 = cardItemData.getClassify1();
        int classify2 = cardItemData.getClassify2();
        holder.simpleDraweeView1.setImageURI(uri1);
        holder.simpleDraweeView2.setImageURI(uri2);
        holder.title1.setText(title1);
        holder.title2.setText(title2);
        holder.content1.setText(content1);
        holder.content2.setText(content2);
        holder.cardView1.setTag(classify1);
        holder.cardView1.setTag(classify2);





    }


    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleDraweeView1;
        private SimpleDraweeView simpleDraweeView2;
        private CardView cardView1;
        private CardView cardView2;
     /*  private LinearLayout cardView1;
        private LinearLayout cardView2;*/
        private TextView title1;
        private TextView title2;
        private TextView content1;
        private TextView content2;

        public ContentViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView1 = (SimpleDraweeView) itemView.findViewById(R.id.recomend_img1);
            simpleDraweeView2 = (SimpleDraweeView) itemView.findViewById(R.id.recomend_img2);
            title1 = (TextView) itemView.findViewById(R.id.recommend_title1);
            title2 = (TextView) itemView.findViewById(R.id.recommend_title2);
            content1 = (TextView) itemView.findViewById(R.id.recommend_content1);
            content2 = (TextView) itemView.findViewById(R.id.recommend_content2);
            cardView1 = (CardView) itemView.findViewById(R.id.recommend_cardview1);
            cardView2 = (CardView) itemView.findViewById(R.id.recommend_cardview2);
            /* cardView1 = (LinearLayout) itemView.findViewById(R.id.recommend_cardview1);
            cardView2 = (LinearLayout) itemView.findViewById(R.id.recommend_cardview2);*/

        }

    }

    /**
     * RecyclerView的Item的监听器接口
     *
     */
    public interface OnItemLeftClickListener
    {
        void OnItemClick(View view, String data);
    }
    public void setOnItemLeftClickListener(OnItemLeftClickListener listener)
    {
        this.mListenerLeft = listener;
    }


    public interface OnItemRighttClickListener
    {
        void OnItemClick(View view, String data);
    }
    public void OnItemRightClickListener(OnItemRighttClickListener listener)
    {
       this.mListenerRight=listener;
    }
}