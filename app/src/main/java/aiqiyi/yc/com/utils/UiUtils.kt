package aiqiyi.yc.com.utils

import java.util.regex.Pattern

/**
 *
 * Created by wanglin  on 2018/5/4 16:30.
 */
class UiUtils {
    companion object {
        /**
         * 手机号匹配
         */
        fun isPhone(phone: String): Boolean {

            var pattern = "^1[3|4|5|6|7|8|9]\\d{9}$"
            return Pattern.matches(pattern, phone)
        }
    }
}