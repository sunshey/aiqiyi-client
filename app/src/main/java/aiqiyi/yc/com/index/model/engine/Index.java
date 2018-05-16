package aiqiyi.yc.com.index.model.engine;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aiqiyi.yc.com.constant.NetConstant;
import aiqiyi.yc.com.index.contract.IndexContract;
import aiqiyi.yc.com.index.model.bean.MovieInfo;
import rx.Observable;
import rx.Subscriber;
import yc.com.base.BaseEngine;
import yc.com.base.BasePresenter;

/**
 * Created by wanglin  on 2018/4/27 17:54.
 */

public class Index extends BaseEngine {


    public Index(Context context) {
        super(context);
    }

    public Observable<ResultInfo<List<MovieInfo>>> getMovieInfoList(int page, int limit, int type) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("limit", limit + "");
        params.put("type", type + "");
        return HttpCoreEngin.get(mContext).rxpost(NetConstant.Companion.getIndex_url(), new TypeReference<ResultInfo<List<MovieInfo>>>() {
        }.getType(), params, false, false, false);

    }
}
