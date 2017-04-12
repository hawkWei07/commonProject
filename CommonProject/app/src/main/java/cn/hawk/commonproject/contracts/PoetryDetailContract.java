package cn.hawk.commonproject.contracts;

import cn.hawk.commonlib.base.BasePresenter;
import cn.hawk.commonlib.base.BaseView;

/**
 * Created by kehaowei on 2017/4/12.
 */

public interface PoetryDetailContract {
    interface View extends BaseView<Presenter> {
        void showPoetryContent(String content);
    }

    interface Presenter extends BasePresenter {

    }
}
