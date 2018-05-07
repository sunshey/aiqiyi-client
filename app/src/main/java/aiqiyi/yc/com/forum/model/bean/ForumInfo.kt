package aiqiyi.yc.com.forum.model.bean

/**
 *
 * Created by wanglin  on 2018/5/3 14:41.
 */
class ForumInfo {
    var id = 0
    var user_id = 0
    var info = ""
    var images = ""
    var pub_date = ""
    var nickname = ""
    var phone = ""
    var sv = ""
    var avtor = ""


    override fun toString(): String {
        return "[info:$info][images:$images][pub_date:$pub_date][nickname:$nickname][phone:$phone][sv:$sv][avtor:$avtor]"
    }

}