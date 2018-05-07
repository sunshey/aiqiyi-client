package aiqiyi.yc.com.index.ui.fragment

import aiqiyi.yc.com.R
import aiqiyi.yc.com.index.contract.IndexContract
import aiqiyi.yc.com.index.model.bean.MovieInfo
import aiqiyi.yc.com.index.presenter.IndexPresenter
import aiqiyi.yc.com.index.ui.activity.MovieDetailActivity
import aiqiyi.yc.com.index.ui.adapter.IndexMovieAdapter
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_index.*
import yc.com.base.BaseFragment

/**
 *
 * Created by wanglin  on 2018/4/27 17:03.
 */
class IndexFragment : BaseFragment<IndexPresenter>(), IndexContract.View {


    var page = 1
    private val LIMIT = 15
    var indexMovieAdapter: IndexMovieAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_index
    }

    override fun init() {
        mPresenter = IndexPresenter(activity, this)
        getData()

    }

    override fun showMovieList(data: List<MovieInfo>) {
        if (indexMovieAdapter == null) {
            index_recyclerView.layoutManager = GridLayoutManager(activity, 2)
            indexMovieAdapter = IndexMovieAdapter(null)
            index_recyclerView.adapter = indexMovieAdapter
        }

        if (page == 1) {
            indexMovieAdapter!!.setNewData(data)
        } else {
            indexMovieAdapter!!.addData(data)
        }

        if (data.size == LIMIT) {
            page++
            indexMovieAdapter!!.loadMoreComplete()
        } else {
            indexMovieAdapter!!.loadMoreEnd()
        }

        initListener()

    }

    fun initListener() {
        indexMovieAdapter!!.setOnLoadMoreListener({
            getData()
        }, index_recyclerView)

        indexMovieAdapter!!.setOnItemClickListener { _, _, position ->
            val moviewInfo = indexMovieAdapter!!.getItem(position)
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra("id", moviewInfo!!.id)
            startActivity(intent)

        }
    }

    fun getData() {
        mPresenter.getMovieList(page, LIMIT)
    }
}