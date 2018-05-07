package aiqiyi.yc.com.index.presenter

import aiqiyi.yc.com.index.contract.MovieDetailContract
import aiqiyi.yc.com.index.model.bean.MovieDetailInfo
import aiqiyi.yc.com.index.model.engine.MovieDetailEngine
import android.content.Context
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import rx.Subscriber
import rx.Subscription
import yc.com.base.BasePresenter

/**
 *
 * Created by wanglin  on 2018/4/28 16:14.
 */
class MovieDetailPresenter(context: Context, view: MovieDetailContract.View) : BasePresenter<MovieDetailEngine, MovieDetailContract.View>(context, view), MovieDetailContract.Presenter {
    init {
        mEngine = MovieDetailEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {
    }

    fun getDetailInfo(movie_id: String) {
        var subscription = mEngine.getDetailInfo(movie_id).subscribe(object : Subscriber<ResultInfo<MovieDetailInfo>>() {


            override fun onError(e: Throwable?) {

            }


            override fun onCompleted() {

            }

            override fun onNext(t: ResultInfo<MovieDetailInfo>?) {
               if (t!!.code== HttpConfig.STATUS_OK&&t.data!=null){
                   mView.showMovieDetail(t.data)
               }
            }

        })
        mSubscriptions.add(subscription)
    }
}