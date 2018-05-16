package aiqiyi.yc.com.index.ui.fragment

import aiqiyi.yc.com.R
import aiqiyi.yc.com.index.contract.IndexContract
import aiqiyi.yc.com.index.model.bean.MovieInfo
import aiqiyi.yc.com.index.presenter.IndexPresenter
import aiqiyi.yc.com.index.ui.activity.MovieDetailActivity
import aiqiyi.yc.com.index.ui.adapter.IndexMainAdapter
import aiqiyi.yc.com.index.ui.adapter.IndexMovieAdapter
import aiqiyi.yc.com.utils.MyItemDecoration
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import com.kk.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_index.*
import yc.com.base.BaseEngine
import yc.com.base.BaseFragment
import yc.com.base.BasePresenter
import yc.com.base.BaseView

/**
 *
 * Created by wanglin  on 2018/4/27 17:03.
 */
class IndexFragment : BaseFragment<BasePresenter<BaseEngine, BaseView>>() {
    private var fragmentList: ArrayList<Fragment>? = null


    override fun getLayoutId(): Int {
        return R.layout.fragment_index
    }

    override fun init() {
        fragmentList = ArrayList()
        val length = resources.getStringArray(R.array.movie_type).size

        (0 until length).forEach { i ->
            val indexItemFragment = IndexItemFragment()
            indexItemFragment.type = i
            fragmentList!!.add(indexItemFragment)
        }

        index_viewpager.adapter = IndexMainAdapter(activity, childFragmentManager, fragmentList!!)
        index_viewpager.offscreenPageLimit = 1
        tabLayout.setupWithViewPager(index_viewpager)
//        tabLayout.setupWithViewPager(index_viewpager)
    }


}