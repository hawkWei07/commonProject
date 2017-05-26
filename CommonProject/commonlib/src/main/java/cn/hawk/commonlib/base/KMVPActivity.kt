package cn.hawk.commonlib.base

import android.os.Bundle

/**
 * Created by kehaowei on 2017/5/25.
 */
abstract class KMVPActivity<T : KCommonPresenter> : BaseActivity() {
    protected var mPresenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        if (null != mPresenter)
            mPresenter!!.attachView()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != mPresenter)
            mPresenter!!.detachView()
    }

    abstract fun createPresenter(): T
}