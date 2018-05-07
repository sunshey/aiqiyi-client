package aiqiyi.yc.com.forum.presenter

import aiqiyi.yc.com.forum.contract.ForumContract
import aiqiyi.yc.com.forum.model.bean.ForumInfo
import aiqiyi.yc.com.forum.model.engine.Forum
import aiqiyi.yc.com.forum.model.engine.ForumEngine
import android.content.Context
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import rx.Subscriber
import yc.com.base.BasePresenter

/**
 *
 * Created by wanglin  on 2018/5/2 14:27.
 */
class ForumPresenter(context: Context, view: ForumContract.View) : BasePresenter<Forum, ForumContract.View>(view), ForumContract.Presenter {
    init {
        mEngine = Forum(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {
    }

    fun getForumInfoList(userId: String, page: Int, limit: Int) {
        val subscription = mEngine.getForumInfoList(userId, page, limit).subscribe(object : Subscriber<ResultInfo<List<ForumInfo>>>() {
            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
            }

            override fun onNext(t: ResultInfo<List<ForumInfo>>?) {
                if (t != null && t.code == HttpConfig.STATUS_OK) {
                    if (t.data != null && t.data.isNotEmpty()) {
                        mView.showForumInfoList(t.data)
                    } else {
                        mView.showEnd()
                    }
                }
            }

        })
        mSubscriptions.add(subscription)
    }


}