package aiqiyi.yc.com.forum.ui.activity

import aiqiyi.yc.com.R
import aiqiyi.yc.com.constant.BusAction
import aiqiyi.yc.com.constant.NetConstant
import aiqiyi.yc.com.forum.contract.UploadImageContract
import aiqiyi.yc.com.forum.presenter.UploadImagePresenter
import aiqiyi.yc.com.forum.ui.adapter.EditForumImageAdapter
import aiqiyi.yc.com.forum.ui.fragment.PhotoFragment
import aiqiyi.yc.com.listener.PhotoHandlerListener
import aiqiyi.yc.com.my.model.bean.UserInfoHelper
import aiqiyi.yc.com.utils.GlideHelper
import aiqiyi.yc.com.utils.IvAvatarHelper
import aiqiyi.yc.com.utils.MyItemDecoration
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import com.kk.utils.LogUtil
import com.vondear.rxtools.RxPhotoTool
import kotlinx.android.synthetic.main.activity_forum_upload.*
import rx.android.schedulers.AndroidSchedulers
import yc.com.base.BaseActivity
import java.io.File
import java.net.URI
import java.util.concurrent.TimeUnit

/**
 *
 * Created by wanglin  on 2018/5/2 16:44.
 */
class EditForumActivity : BaseActivity<UploadImagePresenter>(), UploadImageContract.View {
    var imageList: ArrayList<String>? = null
    var editAdapter: EditForumImageAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_forum_upload
    }

    override fun init() {
        mPresenter = UploadImagePresenter(this, this)
        toolBar.title = ""
        setSupportActionBar(toolBar)
        imageList = ArrayList()
        imageList!!.add("")

        recyclerView.layoutManager = GridLayoutManager(this, 4)
        editAdapter = EditForumImageAdapter(imageList)
        recyclerView.adapter = editAdapter
        recyclerView.addItemDecoration(MyItemDecoration(10.0f, 10.0f))

        toolBar.setNavigationOnClickListener { finish() }
        initListener()
    }

    private fun initListener() {
        RxTextView.textChanges(et_publish).observeOn(AndroidSchedulers.mainThread()).throttleFirst(1, TimeUnit.SECONDS).subscribe {

        }
        RxView.clicks(tv_publish).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            if (imageList!!.size > 0)
                imageList!!.removeAt(0)
            mPresenter.uploadInfo(UserInfoHelper.getUid()!!, et_publish.text.toString().trim(), imageList!!)
        }

        editAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            run {
                if (position == 0) {
                    val photoFragment = PhotoFragment()
                    photoFragment.show(supportFragmentManager, "")

                } else {

                }
            }
        }

    }

    override fun showUploadInfo(data: String?) {
        val url = NetConstant.base_url + data!!
        imageList!!.add(url)
        editAdapter!!.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (null != data)
            IvAvatarHelper.onActivityResult(this, requestCode, resultCode, data, object : PhotoHandlerListener {
                override fun onPhotoHandler(uri: Uri?) {
                    val path = RxPhotoTool.getImageAbsolutePath(this@EditForumActivity, uri)
                    val file = File(path)
                    mPresenter.uploadImage(path.substring(path.lastIndexOf("/") + 1), file)
                }
            })
    }

//    @Subscribe(thread = EventThread.MAIN_THREAD,
//            tags = [(Tag(BusAction.GET_PICTURE))])
//    fun getPhoto(uri: Uri) {
//        val path = RxPhotoTool.getImageAbsolutePath(this, uri)
//        val file = File(path)
//        mPresenter.uploadImage(path.substring(path.lastIndexOf("/") + 1), file)
//
//    }
}