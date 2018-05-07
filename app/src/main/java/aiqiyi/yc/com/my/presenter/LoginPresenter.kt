package aiqiyi.yc.com.my.presenter

import aiqiyi.yc.com.constant.BusAction
import aiqiyi.yc.com.listener.SmsConfirmListener
import aiqiyi.yc.com.my.contract.LoginContract
import aiqiyi.yc.com.my.model.bean.User
import aiqiyi.yc.com.my.model.bean.UserInfoHelper
import aiqiyi.yc.com.my.model.engine.LoginEngine
import aiqiyi.yc.com.utils.EngineUtils
import aiqiyi.yc.com.utils.SMSHelper
import aiqiyi.yc.com.utils.UiUtils
import android.content.Context
import android.text.TextUtils
import com.hwangjr.rxbus.RxBus
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import com.kk.utils.LogUtil
import com.kk.utils.ToastUtil
import rx.Subscriber
import yc.com.base.BasePresenter

/**
 *
 * Created by wanglin  on 2018/5/4 11:11.
 */
class LoginPresenter(context: Context, view: LoginContract.View) : BasePresenter<LoginEngine, LoginContract.View>(context, view), LoginContract.Presenter {
    init {
        mEngine = LoginEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {
    }

    fun getCode(account: String) {
        mView.showGetCode()
        val subscription = mEngine.getCode(account).subscribe {
            object : Subscriber<Unit>() {
                override fun onNext(t: Unit?) {

                }

                override fun onCompleted() {
                }

                override fun onError(e: Throwable?) {
                }
            }
        }
        mSubscriptions.add(subscription)
    }

    fun register(account: String, password: String, code: String) {
        if (TextUtils.isEmpty(account)) {
            ToastUtil.toast(mContext, "手机号不能为空")
            mView.showErrorAccount()
            return
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast(mContext, "密码不能为空")
            mView.showErrorPassword()
            return
        }
        if (TextUtils.isEmpty(account)) {
            ToastUtil.toast(mContext, "验证码不能为空")
            mView.showErrorCode()
            return
        }

        var isCorrect = false
        SMSHelper.submitCode("86", account, code, object : SmsConfirmListener {
            override fun onSuccess() {
                isCorrect = true
            }

            override fun onFailur() {
                isCorrect = false
            }
        })
        LogUtil.msg("TAG  " + isCorrect)
        if (!isCorrect) {
            ToastUtil.toast(mContext, "验证码不正确，请确认后重新输入！")
            return
        }

        mView.showLoadingDialog("正在注册，请稍候...")
        val subscription = mEngine.register(account, password, code).subscribe(object : Subscriber<ResultInfo<User>>() {
            override fun onError(e: Throwable?) {
                mView.dismissDialog()
            }

            override fun onCompleted() {
            }

            override fun onNext(t: ResultInfo<User>?) {
                mView.dismissDialog()
                if (t != null && t.code == HttpConfig.STATUS_OK && t.data != null) {
                    UserInfoHelper.saveUser(t.data)
                    mView.finish()
                    RxBus.get().post(BusAction.USER, t.data)
                }
            }
        })

        mSubscriptions.add(subscription)

    }

    fun login(account: String, password: String) {
        if (TextUtils.isEmpty(account)) {
            ToastUtil.toast(mContext, "手机号不能为空")
            mView.showErrorAccount()
            return
        }

        if (!UiUtils.isPhone(account)) {
            ToastUtil.toast(mContext, "请输入正确的手机号")
            mView.showErrorAccount()
            return
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast(mContext, "密码不能为空")
            mView.showErrorPassword()
            return
        }
        mView.showLoadingDialog("正在登录，请稍候...")
        val subscription = EngineUtils.login(mContext, account, password).subscribe(object : Subscriber<ResultInfo<User>>() {
            override fun onError(e: Throwable?) {
                mView.dismissDialog()
            }

            override fun onCompleted() {

            }

            override fun onNext(t: ResultInfo<User>?) {
                mView.dismissDialog()
                if (t != null && t.code == HttpConfig.STATUS_OK) {
                    if (t.data != null) {
                        UserInfoHelper.saveUser(t.data)
                        RxBus.get().post(BusAction.USER, t.data)
                        mView.finish()
                    } else {
                        ToastUtil.toast(mContext, t.message)
                    }
                }
            }
        })
        mSubscriptions.add(subscription)
    }
}