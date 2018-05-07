package aiqiyi.yc.com.forum.contract

import yc.com.base.*

/**
 *
 * Created by wanglin  on 2018/5/2 16:24.
 */
interface UploadImageContract {

    interface View : IView,IDialog,IFinish {
        fun showUploadInfo(data: String?)
    }
    interface Presenter : IPresenter {}
}