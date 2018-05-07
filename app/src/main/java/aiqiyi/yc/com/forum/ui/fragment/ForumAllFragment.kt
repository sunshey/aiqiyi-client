package aiqiyi.yc.com.forum.ui.fragment

import aiqiyi.yc.com.R
import aiqiyi.yc.com.constant.BusAction
import aiqiyi.yc.com.forum.contract.ForumContract
import aiqiyi.yc.com.forum.model.bean.ForumInfo
import aiqiyi.yc.com.forum.presenter.ForumPresenter
import aiqiyi.yc.com.forum.ui.adapter.ForumInfoAdapter
import aiqiyi.yc.com.my.model.bean.User
import aiqiyi.yc.com.my.model.bean.UserInfoHelper
import aiqiyi.yc.com.utils.MyItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import com.kk.utils.LogUtil
import kotlinx.android.synthetic.main.activity_forum_upload.*
import yc.com.base.BaseFragment

/**
 *
 * Created by wanglin  on 2018/5/2 10:07.
 */
class ForumAllFragment : BaseFragment<ForumPresenter>(), ForumContract.View {


    var mType = 1
    var page = 1
    val LIMIT = 10
    var forumAdapter: ForumInfoAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_forum_detail
    }

    override fun init() {
        mPresenter = ForumPresenter(activity, this)
        getData("")


    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = [(Tag(BusAction.PUBLISH_FORUM))])
    fun getData(string: String) {
        if (mType == 2) {
            if (!TextUtils.isEmpty(UserInfoHelper.getUid())) {
                mPresenter.getForumInfoList(UserInfoHelper.getUid()!!, page, LIMIT)
            }
        } else {
            mPresenter.getForumInfoList("", page, LIMIT)
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = [(Tag(BusAction.USER))])
    fun reloadData(user: User) {
        if (mType == 2) {
            mPresenter.getForumInfoList("${user.id}", page, LIMIT)
        }
    }

    override fun showForumInfoList(data: List<ForumInfo>) {
        LogUtil.msg("data list :" + data.toString())
        if (forumAdapter == null) {
            recyclerView.layoutManager = LinearLayoutManager(activity)
            forumAdapter = ForumInfoAdapter(null)
            recyclerView.adapter = forumAdapter
            recyclerView.addItemDecoration(MyItemDecoration(10.0f))
        }
        if (page == 1) {
            forumAdapter!!.setNewData(data)
        } else {
            forumAdapter!!.addData(data)
        }
        if (data.size == LIMIT) {
            page++
            forumAdapter!!.loadMoreComplete()
        } else {
            forumAdapter!!.loadMoreEnd()
        }


        initListener()

    }

    override fun showEnd() {
        forumAdapter!!.loadMoreEnd()
    }

    private fun initListener() {
        forumAdapter!!.setOnLoadMoreListener({
            getData("")
        }, recyclerView)

    }


}