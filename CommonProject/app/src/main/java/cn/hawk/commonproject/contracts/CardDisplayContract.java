package cn.hawk.commonproject.contracts;

import java.util.ArrayList;

import cn.hawk.commonlib.base.BasePresenter;
import cn.hawk.commonlib.base.BaseView;
import cn.hawk.commonproject.models.PoetryItemBean;

/**
 * Created by kehaowei on 2017/4/10.
 */

public interface CardDisplayContract {
    interface View extends BaseView<Presenter> {
        void showPoetries(ArrayList<PoetryItemBean> infos);
    }

    interface Presenter extends BasePresenter {

    }
}
