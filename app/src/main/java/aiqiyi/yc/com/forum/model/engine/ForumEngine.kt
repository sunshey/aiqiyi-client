package aiqiyi.yc.com.forum.model.engine

import aiqiyi.yc.com.constant.NetConstant
import aiqiyi.yc.com.forum.model.bean.ForumInfo
import android.content.Context
import android.text.TextUtils
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import yc.com.base.BaseEngine

/**
 *
 * Created by wanglin  on 2018/5/2 14:28.
 */
class ForumEngine(context: Context) : BaseEngine(context) {

    fun getForumInfoList(userId: String): Observable<ResultInfo<List<ForumInfo>>> {
        val params = HashMap<String, String>()
        if (!TextUtils.isEmpty(userId)) params.put("user_id",userId)

        return HttpCoreEngin.get(mContext).rxpost(NetConstant.getForumList_url, object : TypeReference<ResultInfo<List<ForumInfo>>>() {}.type, params, false, false, false) as Observable<ResultInfo<List<ForumInfo>>>
    }
}