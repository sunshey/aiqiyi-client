package aiqiyi.yc.com.utils

import aiqiyi.yc.com.constant.NetConstant
import aiqiyi.yc.com.my.model.bean.User
import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import com.kk.securityhttp.net.entry.UpFileInfo
import rx.Observable
import java.io.File

/**
 *
 * Created by wanglin  on 2018/5/4 15:59.
 */
class EngineUtils {
    companion object {
        fun login(context: Context, phone: String, password: String): Observable<ResultInfo<User>> {
            return HttpCoreEngin.get(context).rxpost(NetConstant.login_url, object : TypeReference<ResultInfo<User>>() {}.type, mutableMapOf(
                    "phone" to phone,
                    "pwd" to password
            ), null, false, false, false) as Observable<ResultInfo<User>>
        }

        fun uploadImage(context: Context,fileName: String, file: File): Observable<ResultInfo<String>> {
            val uploadInfo = UpFileInfo()
            uploadInfo.name = "image"
            uploadInfo.filename = fileName
            uploadInfo.file = file
            return HttpCoreEngin.get(context).rxuploadFile(NetConstant.upload_image_url, object : TypeReference<ResultInfo<String>>() {}.type, uploadInfo, null, false) as Observable<ResultInfo<String>>
        }
    }
}