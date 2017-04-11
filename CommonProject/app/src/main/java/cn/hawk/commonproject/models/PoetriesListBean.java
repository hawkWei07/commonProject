package cn.hawk.commonproject.models;

import java.util.ArrayList;

/**
 * Created by kehaowei on 2017/4/11.
 */

public class PoetriesListBean {
    ArrayList<PoetryItemBean> poetries = new ArrayList<>();

    public ArrayList<PoetryItemBean> getPoetries() {
        return poetries;
    }

    public void setPoetries(ArrayList<PoetryItemBean> poetries) {
        this.poetries = poetries;
    }
}
