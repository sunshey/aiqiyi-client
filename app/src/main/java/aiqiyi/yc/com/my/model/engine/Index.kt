package aiqiyi.yc.com.my.model.engine

import android.content.Context

import aiqiyi.yc.com.utils.GlideCacheHelper
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers
import yc.com.base.BaseEngine

/**
 * Created by wanglin  on 2018/5/7 16:50.
 */

class Index(context: Context) : BaseEngine(context) {

    fun clearCache(): Observable<Boolean> {
        return Observable.just("").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map { GlideCacheHelper.getInstance(mContext).clearCache() }
    }
}
