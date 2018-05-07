package aiqiyi.yc.com.utils

import aiqiyi.yc.com.R
import aiqiyi.yc.com.listener.SmsConfirmListener
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.kk.utils.LogUtil
import com.kk.utils.ToastUtil
import com.vondear.rxtools.RxTool
import yc.com.base.UIUtils


/**
 *
 * Created by wanglin  on 2018/5/4 13:48.
 */
class SMSHelper {

    companion object {
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        fun sendCode(country: String, phone: String) {
            // 注册一个事件回调，用于处理发送验证码操作的结果
            SMSSDK.registerEventHandler(object : EventHandler() {
                override fun afterEvent(event: Int, result: Int, data: Any?) {
                    LogUtil.msg("TAG: $event--$result--$data")
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        UIUtils.post({
                            ToastUtil.toast(RxTool.getContext(), "验证码已发送，请在手机上查收！")
                        })

//                        // TODO 处理成功得到验证码的结果
                        // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    } else {
                        // TODO 处理错误的结果
                    }
                }
            })

            // 触发操作
            SMSSDK.getVerificationCode(country, phone)
        }

        // 提交验证码，其中的code表示验证码，如“1357”
        fun submitCode(country: String, phone: String, code: String, listener: SmsConfirmListener) {
            // 注册一个事件回调，用于处理提交验证码操作的结果
            SMSSDK.registerEventHandler(object : EventHandler() {
                override fun afterEvent(event: Int, result: Int, data: Any) {
                    LogUtil.msg("TAG: $event--$result--$data")
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // TODO 处理验证成功的结果
                        listener.onSuccess()
                    } else {
                        // TODO 处理错误的结果
                        listener.onFailur()
                    }

                }
            })
            // 触发操作
            SMSSDK.submitVerificationCode(country, phone, code)
        }


    }


}