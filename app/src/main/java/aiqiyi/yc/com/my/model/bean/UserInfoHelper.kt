package aiqiyi.yc.com.my.model.bean

import aiqiyi.yc.com.constant.SpConstant
import aiqiyi.yc.com.my.ui.activity.LoginGroupActivity
import aiqiyi.yc.com.utils.EngineUtils
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.alibaba.fastjson.JSON
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import com.kk.utils.LogUtil
import com.vondear.rxtools.RxSPTool
import com.vondear.rxtools.RxTool
import rx.Subscriber

/**
 *
 * Created by wanglin  on 2018/5/4 10:37.
 */
class UserInfoHelper {

    companion object {
        private var mUser: User? = null
        fun saveUser(user: User?) {
            this.mUser = user
            try {
                RxSPTool.putString(RxTool.getContext(), SpConstant.USER, JSON.toJSONString(user))
            } catch (e: Exception) {
                LogUtil.msg("error message-->" + e.message)
            }
        }

        fun getUser(): User? {
            if (mUser != null) {
                return mUser
            }
            try {
                val str = RxSPTool.getString(RxTool.getContext(), SpConstant.USER)

                mUser = JSON.parseObject(str, User::class.java)

            } catch (e: Exception) {
                LogUtil.msg("error message-->" + e.message)
            }
            return mUser
        }


        fun getUid(): String? {
            if (mUser != null) {
                return "${mUser!!.id}"
            }
            return null
        }

        fun isGotoLogin(activity: Activity): Boolean {

            if (TextUtils.isEmpty(getUid())) {
                activity.startActivity(Intent(activity, LoginGroupActivity::class.java))
                return true
            }
            return false

        }

        fun login(context: Context) {
            if (getUser() != null)
                EngineUtils.login(context, mUser!!.phone, mUser!!.password).subscribe {
                    object : Subscriber<ResultInfo<User>>() {
                        override fun onNext(t: ResultInfo<User>?) {
                            if (t != null && t.code == HttpConfig.STATUS_OK && t.data != null) {
                                saveUser(t.data)
                            }
                        }

                        override fun onCompleted() {

                        }

                        override fun onError(e: Throwable?) {
                        }
                    }
                }
        }
    }



}