package aiqiyi.yc.com.index.model.bean

import com.alibaba.fastjson.annotation.JSONField

/**
 *
 * Created by wanglin  on 2018/4/28 16:15.
 */
object MovieDetailInfo {
    @JSONField(name = "movie_id")
    var movieId = ""
    @JSONField(name = "movie_pic")
    var moviePic = ""
    @JSONField(name = "movie_name")
    var movieName = ""
    @JSONField(name = "movie_director")
    var director = ""
    @JSONField(name = "movie_place")
    var place = ""
    @JSONField(name = "movie_type")
    var type = ""
    @JSONField(name = "movie_actor")
    var actor = ""
    @JSONField(name = "movie_introduce")
    var desc = ""
}