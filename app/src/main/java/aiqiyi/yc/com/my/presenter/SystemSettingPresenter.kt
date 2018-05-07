package aiqiyi.yc.com.my.presenter

import aiqiyi.yc.com.constant.BusAction
import aiqiyi.yc.com.constant.NetConstant
import aiqiyi.yc.com.my.contract.SystemSettingContract
import aiqiyi.yc.com.my.model.bean.User
import aiqiyi.yc.com.my.model.bean.UserInfoHelper
import aiqiyi.yc.com.my.model.engine.SystemSettingEngine
import aiqiyi.yc.com.utils.EngineUtils
import aiqiyi.yc.com.utils.GlideCacheHelper
import android.content.Context
import android.text.TextUtils
import com.hwangjr.rxbus.RxBus
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import com.kk.utils.ToastUtil
import rx.Subscriber
import yc.com.base.BasePresenter
import java.io.File

/**
 *
 * Created by wanglin  on 2018/5/7 11:37.
 */
class SystemSettingPresenter(context: Context, view: SystemSettingContract.View) : BasePresenter<SystemSettingEngine, SystemSettingContract.View>(context, view), SystemSettingContract.Presenter {
    init {
        mEngine = SystemSettingEngine(mContext)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {
        if (!isForceUI) return
        getCacheSize()
    }

    fun uploadImage(fileName: String, file: File) {
        mView.showLoadingDialog("正在上传中，请稍候...")
        val subscription = EngineUtils.uploadImage(mContext, fileName, file).subscribe(object : Subscriber<ResultInfo<String>>() {
            override fun onError(e: Throwable?) {
                mView.dismissDialog()
            }

            override fun onCompleted() {

            }

            override fun onNext(t: ResultInfo<String>?) {
                mView.dismissDialog()
                if (t != null && t.code == HttpConfig.STATUS_OK && t.data != null) {
                    val url = NetConstant.base_url + t.data
                    updateInfo(UserInfoHelper.getUid()!!, url, "", "")
                } else {
                    ToastUtil.toast(mContext, t!!.message)
                }
            }
        })
        mSubscriptions.add(subscription)
    }


    fun updateInfo(userId: String, ivtor: String, nickname: String, phone: String) {
        if (TextUtils.isEmpty(ivtor) && TextUtils.isEmpty(nickname) && TextUtils.isEmpty(phone)) {
            ToastUtil.toast(mContext, "图像、昵称、手机号必须选择一项修改")
            return
        }

        val subscription = mEngine.changePersonInfo(userId, ivtor, nickname, phone).subscribe(object : Subscriber<ResultInfo<User>>() {
            override fun onCompleted() {
            }

            override fun onNext(t: ResultInfo<User>?) {
                if (t != null && t.code == HttpConfig.STATUS_OK && t.data != null) {
                    mView.showUpdateInfo(t.data)
                    RxBus.get().post(BusAction.USER, t.data)
                    UserInfoHelper.saveUser(t.data)
                    if (!TextUtils.isEmpty(nickname) || !TextUtils.isEmpty(phone)) {
                        mView.finish()
                    }
                }
            }

            override fun onError(e: Throwable?) {
            }
        })
        mSubscriptions.add(subscription)
    }

    fun getCacheSize() {
        val subscription = mEngine.cacheSize.subscribe(object : Subscriber<String>() {
            override fun onNext(t: String?) {
                mView.showCacheSize(t)
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
            }
        })
        mSubscriptions.add(subscription)

    }

    fun clearCache():Boolean{
       return GlideCacheHelper.getInstance(mContext).clearCache()
    }
}


