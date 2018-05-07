package aiqiyi.yc.com.my.ui.fragment


import aiqiyi.yc.com.R
import aiqiyi.yc.com.my.contract.LoginContract
import aiqiyi.yc.com.my.model.bean.User
import aiqiyi.yc.com.my.presenter.LoginPresenter
import aiqiyi.yc.com.my.ui.activity.LoginGroupActivity
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import butterknife.BindView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_register.*
import yc.com.base.BaseActivity
import yc.com.base.BaseFragment

/**
 * TinyHung@Outlook.com
 * 2017/11/28.
 * 用户注册
 */

class RegisterFragment : BaseFragment<LoginPresenter>(), LoginContract.View {




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
                iv_account_cancel!!.visibility = if (!TextUtils.isEmpty(s) && s.length > 0) View.VISIBLE else View.INVISIBLE
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
                iv_password_cancel!!.visibility = if (!TextUtils.isEmpty(s) && s.length > 0) View.VISIBLE else View.INVISIBLE
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


    protected fun initViews() {


        mPresenter = LoginPresenter(activity, this)

        val onClickListener = View.OnClickListener { view ->
            when (view.id) {
            //注册
                R.id.btn_register -> cureateRegisterUser()
            //获取验证码
                R.id.tv_get_code -> cureateGetNumberCode()
            //清除输入框账号
                R.id.iv_account_cancel -> et_account!!.setText("")
            //清除输入框密码
                R.id.iv_password_cancel -> et_password!!.setText("")
            }
        }
        btn_register!!.setOnClickListener(onClickListener)
        iv_account_cancel!!.setOnClickListener(onClickListener)
        iv_password_cancel!!.setOnClickListener(onClickListener)
        tv_get_code!!.setOnClickListener(onClickListener)
        et_account!!.addTextChangedListener(accountChangeListener)
        et_password!!.addTextChangedListener(passwordChangeListener)
        //监听焦点获悉情况
        et_account!!.onFocusChangeListener = onFocusChangeListener
        et_password!!.onFocusChangeListener = onFocusChangeListener
        mInputAnimation = AnimationUtils.loadAnimation(activity, R.anim.shake)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_register
    }

    override fun init() {
        initViews()
    }


    /**
     * 获取验证码
     *
     * @param
     */
    private fun cureateGetNumberCode() {
        val account = et_account!!.text.toString().trim { it <= ' ' }
        if (null != mLoginGroupActivity && !mLoginGroupActivity!!.isFinishing && null != mPresenter) {
            mPresenter.getCode(account)
        }
    }


    /**
     * 准备注册用户
     */
    private fun cureateRegisterUser() {

        val account = et_account!!.text.toString().trim { it <= ' ' }
        val password = et_password!!.text.toString().trim { it <= ' ' }
        val code = et_code!!.text.toString().trim { it <= ' ' }

        mPresenter.register(account, password, code)
    }


    override fun onDestroyView() {
        super.onDestroyView()

        if (null != mInputAnimation) {
            mInputAnimation!!.cancel()
            mInputAnimation = null
        }

    }


    //==========================================注册结果回调=========================================


    fun showAccountResult(data: User?, tint: String) {
        if (null != data && !TextUtils.isEmpty("${data.id}")) {
            ToastUtil.toast(activity, tint + "成功")
            if (TextUtils.equals(getString(R.string.login), tint) || TextUtils.equals(getString(R.string.register), tint)) {
                //                APP.getInstance().setUserData(data, true);
            }
            if (null != mLoginGroupActivity && !mLoginGroupActivity!!.isFinishing()) {
//                mLoginGroupActivity!!.registerResultFinlish()//登录完成
            }
        } else {
            ToastUtil.toast(activity, tint + "异常，请重试！")
        }
    }


    override fun showLoadingDialog(mess: String) {
        (activity as BaseActivity<*>).showLoadingDialog(mess)
    }


    override fun dismissDialog() {
        (activity as BaseActivity<*>).dismissDialog()
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
        et_code!!.startAnimation(mInputAnimation)
    }

    override fun showGetCode() {
        mLoginGroupActivity!!.showGetCodeDisplay(tv_get_code)
    }
}
