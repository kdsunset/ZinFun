package com.myapp.zin.zinfun.entity;

import java.util.List;

/**
 * Created by ZIN on 2016/4/2.
 */
public class SosoSearchBean {
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private List<Item> items;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String query;

    public static class Item {
        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        private  String pic_url;

        public String getLastmodified() {
            return lastmodified;
        }

        public void setLastmodified(String lastmodified) {
            this.lastmodified = lastmodified;
        }

        private  String lastmodified;
    }
}
