package aiqiyi.yc.com.index.presenter

import aiqiyi.yc.com.index.contract.IndexContract
import aiqiyi.yc.com.index.model.bean.MovieInfo
import aiqiyi.yc.com.index.model.engine.Index
import aiqiyi.yc.com.index.model.engine.IndexEngine
import android.content.Context
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import com.kk.utils.LogUtil
import rx.Subscriber
import yc.com.base.BasePresenter

/**
 *
 * Created by wanglin  on 2018/4/27 17:52.
 */
class IndexPresenter(context: Context?, view: IndexContract.View?) : BasePresenter<Index, IndexContract.View>(context, view), IndexContract.Presenter {

    init {
        mEngine = Index(mContext)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {
//        if (!isForceUI) return
//        getMovieList(1, 15)
    }

    fun getMovieList(page: Int, limit: Int, type: Int) {
        val subscription = mEngine.getMovieInfoList(page, limit, type).subscribe(object : Subscriber<ResultInfo<List<MovieInfo>>>() {
            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
            }

            override fun onNext(t: ResultInfo<List<MovieInfo>>?) {
                if (t!!.code == HttpConfig.STATUS_OK && t.data != null) {
//                    LogUtil.msg("数据：——>"+t.data)
                    mView.showMovieList(t.data)
                }
            }

        })
        mSubscriptions.add(subscription)
    }
}