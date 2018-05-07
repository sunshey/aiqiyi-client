package aiqiyi.yc.com.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.vondear.rxtools.RxImageTool

/**
 *
 * Created by wanglin  on 2018/5/2 18:19.
 */
class MyItemDecoration(var width: Float, var height: Float) : RecyclerView.ItemDecoration() {
    constructor(height: Float) : this(0f, height)

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect!!.set(0, 0, RxImageTool.dip2px(width), RxImageTool.dip2px(height))

    }
}