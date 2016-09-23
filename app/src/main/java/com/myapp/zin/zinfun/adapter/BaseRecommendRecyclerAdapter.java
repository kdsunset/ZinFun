package com.myapp.zin.zinfun.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.myapp.zin.zinfun.R;

import java.util.List;


/**
 * Created by ZIN on 2016/4/3.
 */
public abstract class BaseRecommendRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter{
    private static final int VIEW_TIP = 0;
    private static final int VIEW_CONTENT = 1;

    private List<T> mDataList;
    private  List<String> mTips;
    public BaseRecommendRecyclerAdapter(List<T> mDataList,List<String> tips) {

        this.mDataList = mDataList;
        this.mTips=tips;


    }
   // int tipsize=tips.size();

    /**
     * 渲染具体的ViewHolder
     * @param parent ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TIP){//Tip 加载view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemview_recommend_tip, parent, false);
            return new TipVewHolder(view);
        } else {
            //数据itemViewHolder
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    /**
     * 绑定ViewHolder的数据。
     * @param holder
     * @param position 数据源list的下标
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TipVewHolder) {
            ((TipVewHolder) holder).getTvTip().setText(mTips.get(position/2));
            ((TipVewHolder) holder).getBtn().setText("更多");


        } else {
            onBindItemViewHolder((VH)holder, position);
        }
    }

  //  protected abstract void setTipViewHolder(RecyclerView.ViewHolder holder, int position);

    private int getContentItemCount() {
        return mDataList==null?0:mDataList.size();
    }
    @Override
    public int getItemCount() {
      return getContentItemCount()+getTipItemCount();

    }



    protected int getTipItemCount() {
        return mTips==null?0:mTips.size();
    }
    /**
     * 决定元素的布局使用哪种类型
     * @param position 数据源List的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ?VIEW_TIP:VIEW_CONTENT;
    }




    /**
     * 新闻标题
     */
    public static class TipVewHolder extends RecyclerView.ViewHolder {
        private TextView tvTip;

        public Button getBtn() {
            return btn;
        }

        public void setBtn(Button btn) {
            this.btn = btn;
        }

        public TextView getTvTip() {
            return tvTip;
        }

        public void setTvTip(TextView tvTip) {
            this.tvTip = tvTip;
        }

        private Button btn;

        public TipVewHolder(View itemView) {
            super(itemView);
            tvTip = (TextView) itemView.findViewById(R.id.recommend_tip);
            btn = (Button) itemView.findViewById(R.id.recommend_btn);
        }


    }
    //数据itemViewHolder 实现
    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    //正常数据itemViewHolder 实现
    public abstract void onBindItemViewHolder(final VH holder, int position);

    public BaseRecommendRecyclerAdapter() {
    }
}
