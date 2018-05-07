package aiqiyi.yc.com.my.ui.fragment

import aiqiyi.yc.com.R
import aiqiyi.yc.com.constant.BusAction
import aiqiyi.yc.com.my.model.bean.User
import aiqiyi.yc.com.my.model.bean.UserInfoHelper
import aiqiyi.yc.com.my.ui.activity.PersonCenterActivity
import aiqiyi.yc.com.my.ui.activity.SystemSettingActivity
import aiqiyi.yc.com.utils.GlideHelper
import android.content.Intent
import android.text.TextUtils
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.LogUtil
import com.vondear.rxtools.RxPhotoTool
import kotlinx.android.synthetic.main.fragment_my.*
import yc.com.base.BaseEngine
import yc.com.base.BaseFragment
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import java.util.concurrent.TimeUnit

/**
 *
 * Created by wanglin  on 2018/4/27 17:16.
 */
class MyFragment : BaseFragment<BasePresenter<BaseEngine, BaseView>>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_my
    }

    override fun init() {
        initView(UserInfoHelper.getUser())
        LogUtil.msg("user :" + UserInfoHelper.getUser())
        initListener()
    }


    private fun initListener() {
        RxView.clicks(iv_avtor).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            if (!UserInfoHelper.isGotoLogin(activity)) {
                //更换图像
                RxPhotoTool.openLocalImage(activity)
            }
        }
        RxView.clicks(tv_nickname).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            if (!UserInfoHelper.isGotoLogin(activity)) {
                //修改昵称
                val intent = Intent(activity, PersonCenterActivity::class.java)
                intent.putExtra("flag", 1)
                startActivity(intent)
            }
        }

        RxView.clicks(tv_phone).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            if (!UserInfoHelper.isGotoLogin(activity)) {
                //修改手机号
                val intent = Intent(activity, PersonCenterActivity::class.java)
                intent.putExtra("flag", 2)
                startActivity(intent)
            }
        }

        RxView.clicks(baseSettingView_share).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            //分享
        }
        RxView.clicks(baseSettingView_accounts).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            //关注账号
        }

        RxView.clicks(baseSettingView_setting).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            //设置
            val intent = Intent(activity, SystemSettingActivity::class.java)
            startActivity(intent)
        }

    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = [(Tag(BusAction.USER))])
    fun initView(user: User?) {
        if (user != null) {
            GlideHelper.loadImage2(activity, iv_avtor, user.avtor, R.mipmap.default_avtor)
            tv_nickname.text = if (!TextUtils.isEmpty(user.nickname)) user.nickname else "还没设置昵称，快来设置吧"
            tv_phone.text = user.phone
        } else {
            iv_avtor.setImageResource(R.mipmap.default_avtor)
            tv_nickname.text = "还没有登录，点击立即登录"
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = [(Tag(BusAction.LOGOUT))])
    fun logout(str: String) {
        GlideHelper.loadImage2(activity, iv_avtor, "", R.mipmap.default_avtor)
        tv_nickname.text = "还没有登录，点击立即登录"
        tv_phone.text = ""
    }


}