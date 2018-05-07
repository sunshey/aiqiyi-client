package aiqiyi.yc.com.my.contract

import yc.com.base.IDialog
import yc.com.base.IFinish
import yc.com.base.IPresenter
import yc.com.base.IView

/**
 *
 * Created by wanglin  on 2018/5/4 11:12.
 */
interface LoginContract {

    interface View :IView,IDialog,IFinish{
        fun showErrorAccount()
        fun showErrorPassword()
        fun showErrorCode()
        fun showGetCode()
    }
    interface Presenter :IPresenter{}
}