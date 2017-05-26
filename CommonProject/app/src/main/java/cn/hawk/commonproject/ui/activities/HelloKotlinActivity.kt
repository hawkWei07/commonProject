package cn.hawk.commonproject.ui.activities

import android.content.Intent
import cn.hawk.commonlib.base.KMVPActivity
import cn.hawk.commonproject.R
import cn.hawk.commonproject.contracts.HelloKotlinActivityContract
import cn.hawk.commonproject.presents.HelloKotlinActivityPresenter
import kotlinx.android.synthetic.main.activity_hello_kotlin.*

/**
 * Created by kehaowei on 2017/5/25.
 */
class HelloKotlinActivity : KMVPActivity<HelloKotlinActivityPresenter>(), HelloKotlinActivityContract.View {
    override fun getContentId(): Int {
        return R.layout.activity_hello_kotlin
    }

    override fun createPresenter(): HelloKotlinActivityPresenter {
        return HelloKotlinActivityPresenter()
    }

    override fun bindView() {
        super.bindView()
    }

    override fun initData() {
        super.initData()
    }

    override fun initView() {
        super.initView()
        tv_output.text = "哈哈哈哈哈";
    }

    override fun initEvent() {
        super.initEvent()
        btn_back.setOnClickListener {
            goMain()
        }
    }

    fun goMain() {
        var intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}