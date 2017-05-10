package cn.hawk.commonproject.models;

import java.util.ArrayList;
import java.util.List;

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

    public void setPoetries(List<PoetryItemBean> poetries) {
        this.poetries.clear();
        this.poetries.addAll(poetries);
    }

    public PoetryItemBean getPoetryById(int id) {
        for (PoetryItemBean item : poetries) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }
}
