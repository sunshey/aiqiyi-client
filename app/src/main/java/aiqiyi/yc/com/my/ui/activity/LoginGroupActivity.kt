package aiqiyi.yc.com.my.ui.activity

import aiqiyi.yc.com.R
import aiqiyi.yc.com.my.ui.fragment.LoginFragment
import aiqiyi.yc.com.my.ui.fragment.RegisterFragment
import android.support.v4.app.Fragment
import cn.smssdk.SMSSDK
import kotlinx.android.synthetic.main.activity_login_group.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView

/**
 *
 * Created by wanglin  on 2018/5/4 09:31.
 */
class LoginGroupActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {

    var fragmentList: ArrayList<Fragment>? = null
    override fun getLayoutId(): Int {

        return R.layout.activity_login_group
    }

    override fun init() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        fragmentList = ArrayList()

        fragmentList!!.add(LoginFragment())
        fragmentList!!.add(RegisterFragment())
        replaceFragment(0)
    }


    fun replaceFragment(position: Int) {
        val bt = supportFragmentManager.beginTransaction()

        bt.replace(R.id.login_container, fragmentList!![position])
        tv_login.text = if (position == 0) getString(R.string.login) else if (position == 1) getString(R.string.register) else ""

        bt.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterAllEventHandler()
    }
}