package aiqiyi.yc.com.my.contract

import aiqiyi.yc.com.my.model.bean.User
import yc.com.base.IDialog
import yc.com.base.IFinish
import yc.com.base.IPresenter
import yc.com.base.IView

/**
 *
 * Created by wanglin  on 2018/5/7 11:39.
 */
interface SystemSettingContract {
    interface View : IView,IDialog,IFinish {
        fun showUpdateInfo(data: User)
        fun showCacheSize(t: String?)

    }

    interface Presenter : IPresenter {}
}