package aiqiyi.yc.com.base

import aiqiyi.yc.com.my.model.bean.UserInfoHelper
import android.os.Build
import android.support.multidex.MultiDexApplication
import com.kk.securityhttp.domain.GoagalInfo
import com.kk.securityhttp.net.contains.HttpConfig
import com.mob.MobSDK
import com.tencent.bugly.Bugly
import com.vondear.rxtools.RxTool
import rx.Observable
import rx.schedulers.Schedulers
import java.util.*

/**
 *
 * Created by wanglin  on 2018/4/27 16:19.
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Observable.just("").subscribeOn(Schedulers.io()).subscribe { init() }
    }

    private fun init() {
        RxTool.init(this)
//        GoagalInfo.get().init(this)
        MobSDK.init(this)
        UserInfoHelper.login(this)
        Bugly.init(this, "a2e3a99246", false);
        //设置http默认参数
        val params = HashMap<String, String>()
        params.put("imeil", GoagalInfo.get().getUid(this))
        val sv = getSV()
        params.put("sv", sv)

        if (GoagalInfo.get().packageInfo != null) {
            params.put("app_version", GoagalInfo.get().packageInfo.versionCode.toString() + "")
        }
        HttpConfig.setDefaultParams(params)
    }

    fun getSV(): String {
        return if (Build.MODEL.contains(Build.BRAND)) Build.MODEL + " " + Build.VERSION.RELEASE else Build.BRAND + " " + Build.MODEL + " " + Build.VERSION.RELEASE
    }
}