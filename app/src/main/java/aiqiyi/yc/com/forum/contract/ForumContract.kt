package aiqiyi.yc.com.forum.contract

import aiqiyi.yc.com.forum.model.bean.ForumInfo
import yc.com.base.IPresenter
import yc.com.base.IView

/**
 *
 * Created by wanglin  on 2018/5/2 14:39.
 */
interface ForumContract {
    interface View : IView {
        fun showForumInfoList(data: List<ForumInfo>)
        fun showEnd()
    }
    interface Presenter : IPresenter {}
}