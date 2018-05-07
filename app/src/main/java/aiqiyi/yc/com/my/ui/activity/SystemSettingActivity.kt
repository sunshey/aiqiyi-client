package aiqiyi.yc.com.my.ui.activity

import aiqiyi.yc.com.R
import aiqiyi.yc.com.constant.BusAction
import aiqiyi.yc.com.constant.SpConstant
import aiqiyi.yc.com.listener.PhotoHandlerListener
import aiqiyi.yc.com.my.contract.SystemSettingContract
import aiqiyi.yc.com.my.model.bean.User
import aiqiyi.yc.com.my.model.bean.UserInfoHelper
import aiqiyi.yc.com.my.presenter.SystemSettingPresenter
import aiqiyi.yc.com.utils.IvAvatarHelper
import android.content.Intent
import android.net.Uri
import android.view.View
import com.hwangjr.rxbus.RxBus
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import com.tencent.bugly.beta.Beta
import com.vondear.rxtools.RxPhotoTool
import com.vondear.rxtools.RxSPTool
import com.vondear.rxtools.RxTool
import kotlinx.android.synthetic.main.activity_system_setting.*
import kotlinx.android.synthetic.main.base_setting_view.view.*
import yc.com.base.BaseActivity
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * Created by wanglin  on 2018/5/7 09:56.
 */
class SystemSettingActivity : BaseActivity<SystemSettingPresenter>(), SystemSettingContract.View {


    override fun getLayoutId(): Int {
        return R.layout.activity_system_setting
    }

    override fun init() {
        mPresenter = SystemSettingPresenter(this, this)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        initView(UserInfoHelper.getUser())
        initListener()

    }

    private fun initListener() {
        RxView.clicks(baseSettingView_avtor).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            if (!UserInfoHelper.isGotoLogin(this)) {
                RxPhotoTool.openLocalImage(this@SystemSettingActivity)
//
            }
        }
        RxView.clicks(baseSettingView_phone).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            if (!UserInfoHelper.isGotoLogin(this@SystemSettingActivity)) {
                //修改昵称
                val intent = Intent(this@SystemSettingActivity, PersonCenterActivity::class.java)
                intent.putExtra("flag", 2)
                startActivity(intent)
            }
        }
        RxView.clicks(baseSettingView_cache).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            if (mPresenter.clearCache()) {
                baseSettingView_cache.extraText = "0.00KB"
                ToastUtil.toast(this@SystemSettingActivity, "清除缓存成功")
            }
        }
        RxView.clicks(baseSettingView_version).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            Beta.checkUpgrade(true, false)
        }

        RxView.clicks(tv_logout).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            UserInfoHelper.saveUser(null)
            RxSPTool.putString(RxTool.getContext(), SpConstant.USER, "")
            tv_logout.visibility = View.GONE
            RxBus.get().post(BusAction.LOGOUT, "logout")
            baseSettingView_avtor.clearIcon()
            baseSettingView_phone.extraText = ""
        }

    }

    private fun initView(user: User?) {

        if (user != null) {
            baseSettingView_avtor.setIvIcon(user.avtor)
            baseSettingView_phone.extraText = "去修改"
            tv_logout.visibility = View.VISIBLE
        } else {
            tv_logout.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (null != data) {
            IvAvatarHelper.onActivityResult(this, requestCode, resultCode, data, object : PhotoHandlerListener {
                override fun onPhotoHandler(uri: Uri?) {
                    val path = RxPhotoTool.getImageAbsolutePath(this@SystemSettingActivity, uri)
                    val file = File(path)
                    mPresenter.uploadImage(path.substring(path.lastIndexOf("/") + 1), file)
                }
            })
        }
    }

    override fun showUpdateInfo(data: User) {
        initView(data)
    }

    override fun showCacheSize(t: String?) {
        baseSettingView_cache.extraText = t
    }

}