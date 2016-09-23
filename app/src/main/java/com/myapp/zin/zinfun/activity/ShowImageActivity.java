package com.myapp.zin.zinfun.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.zin.zinfun.R;
import com.myapp.zin.zinfun.adapter.Mypager;
import com.myapp.zin.zinfun.utils.ImageUtils;
import com.myapp.zin.zinfun.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import cn.qqtheme.framework.picker.FilePicker;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowImageActivity extends Activity {

    ArrayList<String> photoUri = null;
    TextView title;
    ProgressBar progressBar1;
    ViewPager viewpager;
    Mypager adapter;
    ImageLoader loader;
    DisplayImageOptions options;
    ArrayList<View> view = new ArrayList<View>();
    Context context = null;

    int prePosition;
    int curPosition;
    PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_item);
       /* ImageView image = (ImageView) findViewById(R.id.imageView);
        Intent intent=getIntent();
        String src = intent.getStringExtra("SRC");

        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_anim)
                .showImageOnFail(R.drawable.shibai)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


        ImageLoader.getInstance().displayImage("http://tnfs.tngou.net/image"+src, image, options);*/
        Intent intent=getIntent();
        photoUri = intent.getStringArrayListExtra("URLLIST");

        prePosition = intent.getExtras().getInt("POSITION");
        curPosition=prePosition;

        LogUtils.i("positon");
        LogUtils.i("shou listsize="+photoUri.size()+"positon"+prePosition);
        LogUtils.i(photoUri.get(0));

        loader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.loading_anim)
                .showImageOnFail(R.drawable.shibai).cacheInMemory(true)
                .cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(0))
                .build();
        init();
        viewpagerListener();
      //  loadImageByPosition(position);


    }
    private void viewpagerListener() {
        // TODO Auto-generated method stub
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                title.setText((arg0 + 1) + "/" + view.size());
              //  viewpager.setCurrentItem(arg0);
                curPosition=arg0;

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void init() {
        // TODO Auto-generated method stub
        context = this;

        title = (TextView) findViewById(R.id.textView1);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        viewpager = (ViewPager) findViewById(R.id.viewpager);


        for (int i = 0; i < photoUri.size(); i++) {
            ImageView im = new ImageView(context);

           // ZoomImageView im = new ZoomImageView(context,);
            mAttacher = new PhotoViewAttacher(im);

            im.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            view.add(im);
            loader.displayImage(photoUri.get(i), im,
                    new ImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String arg0, View arg1) {
                            // TODO Auto-generated method stub
                            progressBar1.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String arg0, View arg1,
                                                    FailReason arg2) {
                            // TODO Auto-generated method stub
                            progressBar1.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String arg0, View arg1,
                                                      Bitmap arg2) {
                            // TODO Auto-generated method stub
                            progressBar1.setVisibility(View.GONE);
                            LogUtils.i("加载成功");
                        }

                        @Override
                        public void onLoadingCancelled(String arg0, View arg1) {
                            // TODO Auto-generated method stub
                            progressBar1.setVisibility(View.GONE);
                        }
                    });


        }

        mAttacher.setOnLongClickListener(null);
        view.get(prePosition).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LogUtils.i(",长按了");
                Toast.makeText(context,"chananle ",Toast.LENGTH_SHORT);
                return false;
            }
        });
        mAttacher.update();
        //更新adapter中的数据
        LogUtils.i("更新");
        //adapter.changData(view);
        title.setText(prePosition+1 + "/" + view.size());
       // viewpager.setOffscreenPageLimit(view.size());

        adapter = new Mypager(view);
        viewpager.setAdapter(adapter);
        LogUtils.i("setadapter");
        viewpager.setCurrentItem(prePosition);


    }

    public  void selectPathAndSave(){
        //noinspection MissingPermission

        FilePicker picker = new FilePicker(this, FilePicker.DIRECTORY);
        picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
            @Override
            public void onFilePicked(String currentPath) {
              //  showToast(currentPath);
               // Toast.makeText(context,currentPath,Toast.LENGTH_LONG).show();
                String curImageURL=photoUri.get(curPosition);
                final String fileName="mm"+curImageURL.substring(curImageURL.length()-12,curImageURL.length());
                //final String fileName="mm"+a.substring(0,8);
                final String strPath =currentPath;

                ImageLoader.getInstance().loadImage(curImageURL, new SimpleImageLoadingListener(){

                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        ImageUtils.saveImage(fileName,loadedImage,strPath);
                Toast.makeText(context,"图片已保存在"+strPath+"目录下",Toast.LENGTH_LONG).show();
                    }

                });
            }
        });
        picker.show();

    }
  /*  public  void showDialog(){


        MaterialDialog mMaterialDialog = new MaterialDialog(this)
                .setTitle("MaterialDialog")
                .setMessage("Hello world!")
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();

                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();

                    }
                });

        mMaterialDialog.show();

    }*/
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.showimage_menu, menu);
    return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()){

            case R.id.save_image: {
                selectPathAndSave();



                break;
            }
            default:
                break;
        }
//         Toast.makeText(MainActivity.this, ""+item.getItemId(), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    private void saveIt() {


    }

}
