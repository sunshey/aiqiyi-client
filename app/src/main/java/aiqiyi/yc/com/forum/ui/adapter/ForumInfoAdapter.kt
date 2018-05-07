package aiqiyi.yc.com.forum.ui.adapter

import aiqiyi.yc.com.R

import aiqiyi.yc.com.forum.model.bean.ForumInfo
import aiqiyi.yc.com.utils.GlideHelper
import aiqiyi.yc.com.utils.MyItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.util.*

/**
 *
 * Created by wanglin  on 2018/5/3 16:12.
 */
class ForumInfoAdapter(forumInfos: List<ForumInfo>?) : BaseQuickAdapter<ForumInfo, BaseViewHolder>(R.layout.fragment_forum_item, forumInfos) {
    private var decoration: MyItemDecoration? = null

    init {
        decoration = MyItemDecoration(10.0f, 10.0f)
    }

    override fun convert(helper: BaseViewHolder?, item: ForumInfo?) {
        helper!!.setText(R.id.tv_from, if (TextUtils.isEmpty(item!!.nickname)) item.phone else item.nickname)
                .setText(R.id.tv_date, item.pub_date)
                .setText(R.id.tv_device, item.sv)
                .setText(R.id.tv_content, item.info)
        GlideHelper.loadImage2(mContext, helper.getView(R.id.iv_avtor), item.avtor, R.mipmap.default_avtor)

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)


        if (!TextUtils.isEmpty(item.images)) {
            recyclerView.layoutManager = GridLayoutManager(mContext, 5)
            val split = item.images.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val imageList = Arrays.asList(*split)
            recyclerView.adapter = ForumItemAdapter(imageList)
            if (decoration != null) {
                recyclerView.removeItemDecoration(decoration)
            }
            recyclerView.addItemDecoration(decoration)
        }


    }
}