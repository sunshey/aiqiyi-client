package aiqiyi.yc.com.index.model.engine

import aiqiyi.yc.com.constant.NetConstant
import aiqiyi.yc.com.index.model.bean.MovieInfo
import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import yc.com.base.BaseEngine
import java.util.HashMap

/**
 *
 * Created by wanglin  on 2018/4/27 17:30.
 */
class IndexEngine(context: Context) : BaseEngine(context) {

    fun getMovieInfoList(page: Int, limit: Int): Observable<ResultInfo<List<MovieInfo>>> {
        var params = HashMap<String, String>()
        params.put("page", "$page")
        params.put("limit", "$limit")
//+"$page"+"$limit"
        return HttpCoreEngin.get(mContext).rxpost(NetConstant.index_url, object : TypeReference<ResultInfo<List<MovieInfo>>>() {}.type, mutableMapOf(
                "page" to "$page",
                "limit" to "$limit"
        ), false, false, false) as Observable<ResultInfo<List<MovieInfo>>>
    }
}