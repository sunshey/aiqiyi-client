package aiqiyi.yc.com.my.model.engine

import aiqiyi.yc.com.constant.NetConstant
import aiqiyi.yc.com.my.model.bean.User
import aiqiyi.yc.com.utils.SMSHelper
import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import yc.com.base.BaseEngine

/**
 *
 * Created by wanglin  on 2018/5/4 11:12.
 */
class LoginEngine(context: Context) : BaseEngine(context) {

    fun getCode(phone: String): Observable<Unit> {
        return Observable.just(phone).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map { SMSHelper.sendCode("86", it) }
    }



    fun register(phone: String, password: String, code: String): Observable<ResultInfo<User>> {

        return HttpCoreEngin.get(mContext).rxpost(NetConstant.register_url, object : TypeReference<ResultInfo<User>>() {}.type, mutableMapOf(
                "phone" to phone,
                "pwd" to password,
                "code" to code
        ), null, false, false, false) as Observable<ResultInfo<User>>
    }
}