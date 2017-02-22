package cn.hawk.commonproject.contracts;

import cn.hawk.commonlib.base.BasePresenter;
import cn.hawk.commonlib.base.BaseView;

/**
 * Created by kehaowei on 2017/2/22.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void showOutput(String output);
    }

    interface Presenter extends BasePresenter {

    }
}
