package com.myapp.zin.zinfun.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.myapp.zin.zinfun.R;

public class TempActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.itemview_recommend);
        android.support.v7.widget.CardView cardView1 = (CardView) findViewById(R.id.recommend_cardview1);
        android.support.v7.widget.CardView cardView2 = (CardView) findViewById(R.id.recommend_cardview2);
        SimpleDraweeView image1= (SimpleDraweeView) findViewById(R.id.recomend_img1);
        SimpleDraweeView image2= (SimpleDraweeView) findViewById(R.id.recomend_img2);
        TextView textView1= (TextView) findViewById(R.id.recommend_title1);
        TextView textView2= (TextView) findViewById(R.id.recommend_title2);
        image1.setImageURI(Uri.parse("http://tnfs.tngou.net/image/ext/150714/b32d42d218ebcdb85d39e93ffff390c3.jpg"));
        image2.setImageURI(Uri.parse("http://tnfs.tngou.net/image/ext/150714/3e0d6e232d557afbfa905b987a7a2047.jpg"));
        textView1.setText("mm1");
        textView2.setText("mm2");
    }
}
