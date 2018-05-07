package aiqiyi.yc.com.index.contract

import aiqiyi.yc.com.index.model.bean.MovieDetailInfo
import yc.com.base.IPresenter
import yc.com.base.IView

/**
 *
 * Created by wanglin  on 2018/4/28 16:25.
 */
interface MovieDetailContract {

    interface View : IView {
        fun showMovieDetail(data: MovieDetailInfo?)
    }

    interface Presenter : IPresenter {}
}