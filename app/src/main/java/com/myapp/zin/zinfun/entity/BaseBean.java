package com.myapp.zin.zinfun.entity;

/**
 * Created by ZIN on 2016/3/30.
 */
public class BaseBean {
    private boolean status;

    private int total;


    public void setStatus(boolean status){
        this.status = status;
    }
    public boolean getStatus(){
        return this.status;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }

}
