package aiqiyi.yc.com.my.ui.activity

import aiqiyi.yc.com.R
import aiqiyi.yc.com.my.contract.SystemSettingContract
import aiqiyi.yc.com.my.model.bean.User
import aiqiyi.yc.com.my.model.bean.UserInfoHelper
import aiqiyi.yc.com.my.presenter.SystemSettingPresenter
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.activity_person_center.*
import rx.android.schedulers.AndroidSchedulers
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import java.util.concurrent.TimeUnit

/**
 *
 * Created by wanglin  on 2018/5/7 15:21.
 */
class PersonCenterActivity : BaseActivity<SystemSettingPresenter>(), SystemSettingContract.View {



    var flag = 0 // 1.修改昵称 2修改手机号
    var et_content = ""
    override fun getLayoutId(): Int {
        return R.layout.activity_person_center
    }

    override fun init() {
        mPresenter = SystemSettingPresenter(this, this)
        flag = intent.getIntExtra("flag", 0)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener { finish() }

        setState()
        initListener()

    }

    private fun initListener() {

        RxTextView.textChanges(et_name).debounce(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (it.toString().trim().isNotEmpty()) {
                iv_cancel.visibility = View.VISIBLE
            } else {
                iv_cancel.visibility = View.GONE
            }
            et_content = it.toString().trim()
        }

        RxView.clicks(iv_cancel).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            et_name.setText("")
        }
        RxView.clicks(btn_commit).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            if (flag == 1) {
                mPresenter.updateInfo(UserInfoHelper.getUid()!!, "", et_content, "")
            }
        }

    }

    fun setState() {
        when (flag) {
            1 -> {
                tv_top_title.text = "修改昵称"
                rl_phone.visibility = View.GONE
            }
            2 -> {
                tv_top_title.text = "修改手机号"
                rl_phone.visibility = View.VISIBLE
                et_name.hint = "请输入手机号"
                et_code.hint = "请输入验证码"
                et_code.inputType = EditorInfo.TYPE_CLASS_NUMBER
                et_name.inputType = EditorInfo.TYPE_CLASS_PHONE
            }


        }
    }

    override fun showUpdateInfo(data: User) {

    }
    override fun showCacheSize(t: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}