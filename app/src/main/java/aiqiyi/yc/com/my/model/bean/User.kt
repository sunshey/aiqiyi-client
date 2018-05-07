package aiqiyi.yc.com.my.model.bean

/**
 *
 * Created by wanglin  on 2018/5/4 09:27.
 */
class User {
    var id = 0
    var username = ""
    var phone = ""
    var password = ""
    var nickname = ""
    var avtor = ""

    override fun toString(): String {
        return "[phone-$phone,nickname-$nickname,avtor-$avtor]"
    }
}