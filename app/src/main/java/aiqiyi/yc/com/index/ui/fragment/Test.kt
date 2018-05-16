package aiqiyi.yc.com.index.ui.fragment

import java.util.ArrayList

/**
 * Created by wanglin  on 2018/5/15 10:26.
 */

class Test {
    private val list = ArrayList<String>()

    fun setList() {
        for (i in 0..9) {
            list.add(i.toString() + "")
        }
    }
}
