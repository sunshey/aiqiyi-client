package aiqiyi.yc.com.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 *
 * Created by wanglin  on 2018/4/27 17:06.
 */
class MainViewpagerAdapter(ft: FragmentManager, var fragmentList: List<Fragment>) : FragmentPagerAdapter(ft) {
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}