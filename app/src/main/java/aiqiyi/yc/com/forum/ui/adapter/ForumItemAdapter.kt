package aiqiyi.yc.com.forum.ui.adapter

import aiqiyi.yc.com.R
import aiqiyi.yc.com.utils.GlideHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * Created by wanglin  on 2018/5/4 17:38.
 */
class ForumItemAdapter(images: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.forum_item, images) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        GlideHelper.loadImage(mContext, helper!!.getView(R.id.iv_pic), item!!, R.color.gray_cccccc)
    }
}