package com.myapp.zin.zinfun.entity;

import java.util.List;

/**
 * Created by ZIN on 2016/3/30.
 */
public class DetailListBean {
    private int count;

    private int fcount;

    private int galleryclass;

    private int id;

    private String img;

    private List<Picture> list;

    private int rcount;

    private int size;

    private boolean status;

    private long time;

    private String title;

    private String url;

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
    public void setList(List<Picture> list){
        this.list = list;
    }
    public List<Picture> getList(){
        return this.list;
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
    public void setStatus(boolean status){
        this.status = status;
    }
    public boolean getStatus(){
        return this.status;
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
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public class Picture {
        private int gallery;

        private long id;

        private String src;

        public void setGallery(int gallery){
            this.gallery = gallery;
        }
        public int getGallery(){
            return this.gallery;
        }
        public void setId(long id){
            this.id = id;
        }
        public long getId(){
            return this.id;
        }
        public void setSrc(String src){
            this.src = src;
        }
        public String getSrc(){
            return this.src;
        }

    }
}
