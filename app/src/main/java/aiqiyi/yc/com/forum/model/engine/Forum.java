package aiqiyi.yc.com.forum.model.engine;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aiqiyi.yc.com.constant.NetConstant;
import aiqiyi.yc.com.forum.model.bean.ForumInfo;
import rx.Observable;
import yc.com.base.BaseEngine;

/**
 * Created by wanglin  on 2018/5/4 16:57.
 */

public class Forum extends BaseEngine {
    public Forum(Context context) {
        super(context);
    }

    public Observable<ResultInfo<List<ForumInfo>>> getForumInfoList(String userId, int page, int limit) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(userId)) params.put("user_id", userId);
        params.put("page", page + "");
        params.put("limit", limit + "");
        return HttpCoreEngin.get(mContext).rxpost(NetConstant.Companion.getGetForumList_url(), new TypeReference<ResultInfo<List<ForumInfo>>>() {
        }.getType(), params, false, false, false);
    }
}
