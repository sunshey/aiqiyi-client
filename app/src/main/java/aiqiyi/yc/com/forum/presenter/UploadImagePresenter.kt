package aiqiyi.yc.com.forum.presenter

import aiqiyi.yc.com.constant.BusAction
import android.content.Context

import aiqiyi.yc.com.forum.contract.UploadImageContract
import aiqiyi.yc.com.forum.model.engine.UploadImageEngine
import aiqiyi.yc.com.utils.EngineUtils
import android.text.TextUtils
import com.hwangjr.rxbus.RxBus
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import com.kk.utils.ToastUtil
import rx.Subscriber
import yc.com.base.BasePresenter
import java.io.File

/**
 * Created by wanglin  on 2018/5/2 16:14.
 */

class UploadImagePresenter(context: Context, view: UploadImageContract.View) : BasePresenter<UploadImageEngine, UploadImageContract.View>(context, view), UploadImageContract.Presenter {
    init {
        mEngine = UploadImageEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {

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
                    mView.showUploadInfo(t.data)
                } else {
                    ToastUtil.toast(mContext, t!!.message)
                }
            }


        })
        mSubscriptions.add(subscription)
    }

    fun uploadInfo(userId: String, info: String, images: List<String>) {
        if (TextUtils.isEmpty(info) && images.isEmpty()) {
            ToastUtil.toast(mContext, "请输入要发表的文字或图片")
            return
        }
        mView.showLoadingDialog("正在上传论坛信息，请稍候...")
        val subscription = mEngine.uploadInfo(userId, info, images).subscribe(object : Subscriber<ResultInfo<String>>() {
            override fun onNext(t: ResultInfo<String>?) {
                mView.dismissDialog()
                if (t != null && t.code == HttpConfig.STATUS_OK && t.data != null) {
                    ToastUtil.toast(mContext, t.data)
                    RxBus.get().post(BusAction.PUBLISH_FORUM, "success")
                    mView.finish()
                }
            }

            override fun onError(e: Throwable?) {
                mView.dismissDialog()
            }

            override fun onCompleted() {
            }
        })
        mSubscriptions.add(subscription)
    }
}
