package aiqiyi.yc.com.forum.model.engine

import aiqiyi.yc.com.constant.NetConstant
import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import com.kk.securityhttp.net.entry.UpFileInfo
import rx.Observable
import yc.com.base.BaseEngine
import java.io.File

/**
 *
 * Created by wanglin  on 2018/5/2 16:15.
 */
class UploadImageEngine(context: Context) : BaseEngine(context) {


    fun uploadInfo(usrId: String, info: String, images: List<String>): Observable<ResultInfo<String>> {
        val image = StringBuilder()

        for (i in images) {
            image.append(i).append(",")
        }
        if (image.length > 1)
            image.deleteCharAt(image.length - 1)

        return HttpCoreEngin.get(mContext).rxpost(NetConstant.upload_forum_url, object : TypeReference<ResultInfo<String>>() {}.type, mutableMapOf(
                "user_id" to usrId,
                "info" to info,
                "images" to image.toString()
        ), null, false, false, false) as Observable<ResultInfo<String>>

    }
}