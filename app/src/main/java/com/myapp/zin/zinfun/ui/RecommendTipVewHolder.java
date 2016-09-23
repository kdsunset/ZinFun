package com.myapp.zin.zinfun.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapp.zin.zinfun.R;


/**
 * Created by wenhuaijun on 2016/2/6 0006.
 */
/**
* 该类的描述
*
*
*/
public class RecommendTipVewHolder extends RecyclerView.ViewHolder  {
    private TextView tip;
    private Button btn;
    public RecommendTipVewHolder(View itemView) {
        super(itemView);
        tip =(TextView)itemView.findViewById(R.id.recommend_tip);
        btn = (Button) itemView.findViewById(R.id.recommend_btn);
    }




}
