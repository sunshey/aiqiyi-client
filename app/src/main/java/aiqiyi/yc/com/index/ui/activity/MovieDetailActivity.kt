package aiqiyi.yc.com.index.ui.activity

import aiqiyi.yc.com.R
import aiqiyi.yc.com.index.contract.MovieDetailContract
import aiqiyi.yc.com.index.model.bean.MovieDetailInfo
import aiqiyi.yc.com.index.presenter.MovieDetailPresenter
import aiqiyi.yc.com.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_movie_detail.*
import yc.com.base.BaseActivity

/**
 *
 * Created by wanglin  on 2018/4/28 11:19.
 */
class MovieDetailActivity : BaseActivity<MovieDetailPresenter>(), MovieDetailContract.View {


    var movieId = 0
    override fun getLayoutId(): Int {
        return R.layout.activity_movie_detail
    }

    override fun init() {
        mPresenter = MovieDetailPresenter(this, this)
        movieId = intent.getIntExtra("id", 0)
        getData()
    }

    override fun showMovieDetail(data: MovieDetailInfo?) {
        tv_name.text = String.format(getString(R.string.movie_name), data!!.movieName)
        tv_actor.text = data.actor
        tv_place.text = String.format(getString(R.string.movie_place), data.place)
        tv_type.text = String.format(getString(R.string.movie_type), data.type)
        tv_director.text = String.format(getString(R.string.movie_director), data.director)
        tv_introduce.text = String.format(getString(R.string.movie_introduce), data.desc)
        GlideHelper.loadImage(this, iv_cover, data.moviePic, R.color.gray)
    }

    fun getData() {
        mPresenter.getDetailInfo("$movieId")
    }
}