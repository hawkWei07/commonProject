package cn.hawk.commonlib.base

import android.os.Bundle
import android.view.View

/**
 * Created by kehaowei on 2017/5/25.
 */
abstract class KMVPFragment<T : CommonPresenter> : BaseFragment() {
    protected var mPresenter: T? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        if (null != mPresenter) {
            mPresenter!!.attachView()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (null != mPresenter)
            mPresenter!!.detachView()
    }

    abstract fun createPresenter(): T
}