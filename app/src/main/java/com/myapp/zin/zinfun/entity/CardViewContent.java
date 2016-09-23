package com.myapp.zin.zinfun.entity;

/**
 * Created by ZIN on 2016/4/3.
 */
public class CardViewContent {
    private String imgUrl1;
    private String imgUrl2;
    private String title1;
    private String title2;
    private String content1;
    private String content2;

    public int getClassify1() {
        return classify1;
    }

    public void setClassify1(int classify1) {
        this.classify1 = classify1;
    }

    public int getClassify2() {
        return classify2;
    }

    public void setClassify2(int classify2) {
        this.classify2 = classify2;
    }

    private  int classify1;
    private  int classify2;

    public CardViewContent() {
    }

    public CardViewContent(String imgUrl1, String imgUrl2, String title1, String title2, String content1, String content2, int classify1, int classify2) {
        this.imgUrl1 = imgUrl1;
        this.imgUrl2 = imgUrl2;
        this.title1 = title1;
        this.title2 = title2;
        this.content1 = content1;
        this.content2 = content2;
        this.classify1 = classify1;
        this.classify2 = classify2;
    }

    public String getImgUrl1() {
        return imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {
        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }



}
