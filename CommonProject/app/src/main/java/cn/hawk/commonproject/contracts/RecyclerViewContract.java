package cn.hawk.commonproject.contracts;

import java.util.ArrayList;

import cn.hawk.commonlib.base.BasePresenter;
import cn.hawk.commonlib.base.BaseView;

/**
 * Created by kehaowei on 2017/3/24.
 */

public interface RecyclerViewContract {

    interface View extends BaseView<Presenter> {
        void refreshList(ArrayList<String> infos);
    }

    interface Presenter extends BasePresenter {

    }
}
