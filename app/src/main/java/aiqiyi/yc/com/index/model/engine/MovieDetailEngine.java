package aiqiyi.yc.com.index.model.engine;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.kk.securityhttp.net.contains.HttpConfig;


import java.util.HashMap;
import java.util.Map;

import aiqiyi.yc.com.constant.NetConstant;
import aiqiyi.yc.com.index.model.bean.MovieDetailInfo;
import rx.Observable;
import yc.com.base.BaseEngine;

/**
 * Created by wanglin  on 2018/4/28 16:14.
 */

public class MovieDetailEngine extends BaseEngine {
    public MovieDetailEngine(Context context) {
        super(context);

    }

    public Observable<ResultInfo<MovieDetailInfo>> getDetailInfo(String movie_id) {
        Map<String, String> params = new HashMap<>();
        params.put("movie_id", movie_id);
        return HttpCoreEngin.get(mContext).rxpost(NetConstant.Companion.getDetail_url(), new TypeReference<ResultInfo<MovieDetailInfo>>() {
        }.getType(), params, false, false, false);

    }
}
