package com.myapp.zin.zinfun.entity;

import java.util.List;

/**
 * Created by ZIN on 2016/3/30.
 */
public class SiwammBean extends  BaseBean {
    public List<Tngou> getTngou() {
        return tngou;
    }

    public void setTngou(List<Tngou> tngou) {
        this.tngou = tngou;
    }

    public List<Tngou> tngou ;

    public class Tngou {
        private int count;

        private int fcount;

        private int galleryclass;

        private int id;

        private String img;

        private int rcount;

        private int size;

        private long time;

        private String title;

        public void setCount(int count){
            this.count = count;
        }
        public int getCount(){
            return this.count;
        }
        public void setFcount(int fcount){
            this.fcount = fcount;
        }
        public int getFcount(){
            return this.fcount;
        }
        public void setGalleryclass(int galleryclass){
            this.galleryclass = galleryclass;
        }
        public int getGalleryclass(){
            return this.galleryclass;
        }
        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setImg(String img){
            this.img = img;
        }
        public String getImg(){
            return this.img;
        }
        public void setRcount(int rcount){
            this.rcount = rcount;
        }
        public int getRcount(){
            return this.rcount;
        }
        public void setSize(int size){
            this.size = size;
        }
        public int getSize(){
            return this.size;
        }
        public void setTime(long time){
            this.time = time;
        }
        public long getTime(){
            return this.time;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }

    }

}
