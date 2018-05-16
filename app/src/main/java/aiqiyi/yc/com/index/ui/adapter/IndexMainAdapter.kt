package aiqiyi.yc.com.index.ui.adapter

import aiqiyi.yc.com.R
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 *
 * Created by wanglin  on 2018/5/15 10:07.
 */
class IndexMainAdapter(context: Context, fm: FragmentManager, private var fragmentList: List<Fragment>) : FragmentPagerAdapter(fm) {

    var titles: Array<String>? = null

    init {
        titles = context.resources.getStringArray(R.array.movie_type)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles!![position]
    }
}