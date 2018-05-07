package aiqiyi.yc.com.base

import aiqiyi.yc.com.R
import aiqiyi.yc.com.forum.ui.fragment.ForumFragment
import aiqiyi.yc.com.index.ui.fragment.IndexFragment
import aiqiyi.yc.com.my.ui.fragment.MyFragment
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView

class MainActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    var fragmentList: ArrayList<Fragment>? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        fragmentList = ArrayList()
        fragmentList!!.add(IndexFragment())
        fragmentList!!.add(ForumFragment())
        fragmentList!!.add(MyFragment())

        mViewPager.adapter = MainViewpagerAdapter(supportFragmentManager, fragmentList!!)
        mViewPager.currentItem = 0
        mViewPager.offscreenPageLimit = 2
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                baseBottomView.selectTab(position)
            }

        })
        baseBottomView.setTabSelectedListener { mViewPager.currentItem = it }

    }


}


