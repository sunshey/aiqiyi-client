package aiqiyi.yc.com.forum.ui.adapter

import aiqiyi.yc.com.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 * Created by wanglin  on 2018/1/26 10:41.
 */

class PhotoAdapter(data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.dialog_photo_item, data) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_title, item)
        val position = helper.adapterPosition
        if (position == mData.size - 1) {
            helper.setVisible(R.id.divider, false)
        }

    }
}
