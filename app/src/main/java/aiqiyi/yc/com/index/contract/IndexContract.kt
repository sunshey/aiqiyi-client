package aiqiyi.yc.com.index.contract

import aiqiyi.yc.com.index.model.bean.MovieInfo
import yc.com.base.IPresenter
import yc.com.base.IView

/**
 *
 * Created by wanglin  on 2018/4/27 17:52.
 */
interface IndexContract {

    interface View : IView {
        fun showMovieList(data: List<MovieInfo>)
    }
    interface Presenter : IPresenter {}
}