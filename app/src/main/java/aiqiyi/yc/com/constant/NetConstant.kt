package aiqiyi.yc.com.constant

/**
 *
 * Created by wanglin  on 2018/4/27 17:39.
 */
class NetConstant {


    companion object {
        //http://192.168.80.92:8080/movielist/1/10
        val base_url = "http://172.24.27.1:8080/"

        //https接口地址，目前证书验证失败，不能访问
//        val base_url = "https://172.24.27.1:8081/"
        /**
         * 首页
         */
        val index_url = base_url + "movielist"

        val detail_url = base_url + "movie_detail"

        val upload_forum_url = base_url + "upload_forum"

        /**
         * 上传图片
         */
        val upload_image_url = base_url + "upload_image"
        /**
         * 获取论坛信息
         */
        val getForumList_url = base_url + "getForumList"

        val login_url = base_url + "login"

        val register_url = base_url + "register"
        /**
         * 更新个人资料
         */
        val update_info_url = base_url + "update_info"
    }
}