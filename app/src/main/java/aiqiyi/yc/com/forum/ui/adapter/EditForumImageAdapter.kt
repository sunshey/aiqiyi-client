package aiqiyi.yc.com.forum.ui.adapter

import aiqiyi.yc.com.R
import aiqiyi.yc.com.utils.GlideHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * Created by wanglin  on 2018/5/2 17:50.
 */
class EditForumImageAdapter(imagList: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.uplaod_image_item, imagList) {
    override fun convert(helper: BaseViewHolder?, item: String?) {

        val pos = helper!!.layoutPosition
        if (pos == 0) {
            helper.setImageResource(R.id.iv_upload, R.mipmap.plug)
        } else {
            GlideHelper.loadImage(mContext, helper.getView(R.id.iv_upload), item!!, R.color.gray_bfbfbf)
        }
    }
}