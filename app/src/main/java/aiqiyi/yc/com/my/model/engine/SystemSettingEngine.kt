package aiqiyi.yc.com.my.model.engine

import aiqiyi.yc.com.constant.NetConstant
import aiqiyi.yc.com.my.model.bean.User
import aiqiyi.yc.com.utils.GlideCacheHelper
import android.content.Context
import android.text.TextUtils
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers
import yc.com.base.BaseEngine

/**
 *
 * Created by wanglin  on 2018/5/7 11:38.
 */
class SystemSettingEngine(context: Context) : BaseEngine(context) {


    fun changePersonInfo(userId: String, ivtor: String, nickname: String, phone: String): Observable<ResultInfo<User>> {
        val params = hashMapOf<String, String>()
        params.put("user_id", userId)
        if (!TextUtils.isEmpty(ivtor)) params.put("ivtor", ivtor)
        if (!TextUtils.isEmpty(nickname)) params.put("nickname", nickname)
        if (!TextUtils.isEmpty(phone)) params.put("phone", phone)
        return HttpCoreEngin.get(mContext).rxpost(NetConstant.update_info_url, object : TypeReference<ResultInfo<User>>() {}.type, params, false, false, false) as Observable<ResultInfo<User>>
    }

    val cacheSize: Observable<String>
        get() = Observable.just("").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map { GlideCacheHelper.getInstance(mContext).cacheSize }


}