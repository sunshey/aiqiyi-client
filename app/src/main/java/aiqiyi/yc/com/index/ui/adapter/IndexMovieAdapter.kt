package aiqiyi.yc.com.index.ui.adapter

import aiqiyi.yc.com.R
import aiqiyi.yc.com.index.model.bean.MovieInfo
import aiqiyi.yc.com.utils.GlideHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * Created by wanglin  on 2018/4/28 08:42.
 */
class IndexMovieAdapter(moviewInfos: List<MovieInfo>?) : BaseQuickAdapter<MovieInfo, BaseViewHolder>(R.layout.index_movie_item,moviewInfos) {
    override fun convert(helper: BaseViewHolder?, item: MovieInfo?) {
        helper!!.setText(R.id.tv_name, item!!.name).setText(R.id.tv_score, item.score)
                .setText(R.id.tv_actor, item.actor)
        GlideHelper.loadImage(mContext, helper.getView(R.id.iv_cover), item.cover, R.color.gray)

    }

}