package aiqiyi.yc.com.my.ui.fragment

import aiqiyi.yc.com.R

import aiqiyi.yc.com.my.contract.LoginContract
import aiqiyi.yc.com.my.model.bean.User
import aiqiyi.yc.com.my.model.bean.UserInfoHelper
import aiqiyi.yc.com.my.presenter.LoginPresenter
import aiqiyi.yc.com.my.ui.activity.LoginGroupActivity
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.kk.utils.LogUtil
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_login.*

import yc.com.base.BaseFragment

/**
 * TinyHung@Outlook.com
 * 2017/11/28.
 * 账号密码登录
 */

class LoginFragment : BaseFragment<LoginPresenter>(), LoginContract.View {


    private var mInputAnimation: Animation? = null
    private var mLoginGroupActivity: LoginGroupActivity? = null

    /**
     * 账号输入框监听
     */
    private val accountChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            if (null != iv_account_cancel)
                iv_account_cancel!!.visibility = if (!TextUtils.isEmpty(s) && s.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    /**
     * 密码输入框监听
     */
    private val passwordChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (null != iv_password_cancel)
                iv_password_cancel!!.visibility = if (!TextUtils.isEmpty(s) && s.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        }

        override fun afterTextChanged(s: Editable) {

        }
    }


    /**
     * 对个输入框焦点进行监听
     */
    private val onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        when (v.id) {
            R.id.et_account -> if (hasFocus) {
                if (et_account!!.text.toString().isNotEmpty()) {
                    iv_account_cancel!!.visibility = View.VISIBLE
                }
            } else {
                if (null != iv_account_cancel) iv_account_cancel!!.visibility = View.INVISIBLE
            }
            R.id.et_password -> if (hasFocus) {
                if (et_password!!.text.toString().isNotEmpty()) {
                    iv_password_cancel!!.visibility = View.VISIBLE
                }
            } else {
                if (null != iv_password_cancel)
                    iv_password_cancel!!.visibility = View.INVISIBLE
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mLoginGroupActivity = context as LoginGroupActivity
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun init() {
        initViews()
    }


    private fun initViews() {

        mPresenter = LoginPresenter(activity, this)
        val onClickListener = View.OnClickListener { view ->
            when (view.id) {
            //登录
                R.id.btn_login -> createAccountLogin()
            //忘记密码
                R.id.tv_retrieve_password -> if (null != mLoginGroupActivity && !mLoginGroupActivity!!.isFinishing) {
//                    mLoginGroupActivity!!.addReplaceFragment(LoginGroupActivity.FIND_PWD, "修改密码", "登录")//打开修改密码界面
//                    mLoginGroupActivity!!.showOthreLoginView(false)
                }
            //清除输入框账号
                R.id.iv_account_cancel -> et_account!!.setText("")
            //清除输入框密码
                R.id.iv_password_cancel -> et_password!!.setText("")
                R.id.tv_register -> mLoginGroupActivity!!.replaceFragment(1)
            }
        }
        et_account!!.setText(if (!TextUtils.isEmpty(UserInfoHelper.getUid())) UserInfoHelper.getUser()!!.phone else "")
        et_account!!.setSelection(et_account!!.text.toString().length)
        tv_retrieve_password!!.setOnClickListener(onClickListener)
        iv_account_cancel!!.setOnClickListener(onClickListener)
        iv_password_cancel!!.setOnClickListener(onClickListener)
        btn_login!!.setOnClickListener(onClickListener)
        tv_register!!.setOnClickListener(onClickListener)
        et_account!!.addTextChangedListener(accountChangeListener)
        et_password!!.addTextChangedListener(passwordChangeListener)
        //监听焦点获悉情况
        et_account!!.onFocusChangeListener = onFocusChangeListener
        et_password!!.onFocusChangeListener = onFocusChangeListener
        mInputAnimation = AnimationUtils.loadAnimation(activity, R.anim.shake)
        //设置密码属性
        et_password!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }


    /**
     * 用户使用账号登录
     */
    private fun createAccountLogin() {
        if (null != et_account && null != et_password) {
            val account = et_account!!.text.toString().trim { it <= ' ' }
            val password = et_password!!.text.toString().trim { it <= ' ' }
            mPresenter.login(account, password)

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        et_account!!.setText("")
        et_password!!.setText("")
        if (null != mInputAnimation) {
            mInputAnimation!!.cancel()
            mInputAnimation = null
        }
    }


    fun showAccountResult(data: User?, tint: String) {
        if (null != data && !TextUtils.isEmpty("${data.id}")) {
            if (TextUtils.equals(getString(R.string.login), tint)) {
                //                APP.getInstance().setUserData(data, true);
            }
            if (null != mLoginGroupActivity && !mLoginGroupActivity!!.isFinishing()) {
//                mLoginGroupActivity!!.loginResultFinlish()
            }
        } else {
            ToastUtil.toast(activity, tint + "异常，请重试！")
        }
    }


    override fun showLoadingDialog(mess: String) {
        if (null != mLoginGroupActivity)
            mLoginGroupActivity!!.showLoadingDialog(mess)
    }


    override fun dismissDialog() {

        if (null != mLoginGroupActivity)

            mLoginGroupActivity!!.dismissDialog()
    }

    override fun finish() {
        activity.finish()
    }

    override fun showErrorAccount() {

        et_account!!.startAnimation(mInputAnimation)
    }

    override fun showErrorPassword() {
        et_password!!.startAnimation(mInputAnimation)
    }

    override fun showErrorCode() {

    }

    override fun showGetCode() {

    }


}
