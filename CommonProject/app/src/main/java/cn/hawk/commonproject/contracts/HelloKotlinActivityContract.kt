package cn.hawk.commonproject.contracts

import cn.hawk.commonlib.base.KBasePresenter
import cn.hawk.commonlib.base.KBaseView

/**
 * Created by kehaowei on 2017/5/25.
 */
interface HelloKotlinActivityContract {
    interface Presenter : KBasePresenter {

    }

    interface View : KBaseView<Presenter> {

    }
}