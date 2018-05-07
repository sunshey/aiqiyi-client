package aiqiyi.yc.com.forum.ui.adapter

import aiqiyi.yc.com.R
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kk.utils.LogUtil

/**
 *
 * Created by wanglin  on 2018/5/2 09:37.
 */
class ForumAdapter( context: Context,ft: FragmentManager, var list: List<Fragment>) : FragmentPagerAdapter(ft) {
    var titles: Array<String>? = null

    init {
        titles = context.resources.getStringArray(R.array.forum)
    }

    override fun getItem(position: Int): Fragment {
        return list[position]

    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles!![position]
    }
}