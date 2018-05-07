package aiqiyi.yc.com.forum.ui.fragment

import aiqiyi.yc.com.R
import aiqiyi.yc.com.forum.ui.activity.EditForumActivity
import aiqiyi.yc.com.forum.ui.adapter.ForumAdapter
import aiqiyi.yc.com.my.model.bean.UserInfoHelper
import android.content.Intent
import android.support.v4.app.Fragment
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.fragment_forum.*
import yc.com.base.BaseEngine
import yc.com.base.BaseFragment
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2018/4/27 17:14.
 */
class ForumFragment : BaseFragment<BasePresenter<BaseEngine, BaseView>>() {
    var fragmentList: ArrayList<Fragment>? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_forum
    }

    override fun init() {
        fragmentList = ArrayList()

        val forumAllFragment = ForumAllFragment()
        forumAllFragment.mType = 1
        val forumMyFragment = ForumAllFragment()
        forumMyFragment.mType = 2
        fragmentList!!.add(forumAllFragment)
        fragmentList!!.add(forumMyFragment)

        forum_viewpager.adapter = ForumAdapter(activity, childFragmentManager, fragmentList!!)
        tabLayout.setupWithViewPager(forum_viewpager)
        initListener()

    }


    fun initListener() {
        RxView.clicks(floatingActionButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            if (!UserInfoHelper.isGotoLogin(activity)) {
                val intent = Intent(activity, EditForumActivity::class.java)
                startActivity(intent)
            }
        }
    }

}