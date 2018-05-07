package aiqiyi.yc.com.utils

import aiqiyi.yc.com.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ActivityCompat

import com.hwangjr.rxbus.RxBus
import com.vondear.rxtools.RxPhotoTool
import com.yalantis.ucrop.UCrop


import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import aiqiyi.yc.com.constant.BusAction
import aiqiyi.yc.com.listener.PhotoHandlerListener

import android.app.Activity.RESULT_OK
import com.kk.utils.LogUtil
import com.yalantis.ucrop.UCropActivity

/**
 * Created by wanglin  on 2018/3/15 08:57.
 */

object IvAvatarHelper {

    fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent, photoHandlerListener: PhotoHandlerListener) {
        when (requestCode) {
            RxPhotoTool.GET_IMAGE_FROM_PHONE//选择相册之后的处理
            -> if (resultCode == RESULT_OK) {
                //
                //              RxPhotoTool.cropImage(MainActivity.this,data.getData() );// 裁剪图片

                initUCrop(activity, data.data)
                LogUtil.msg("data : ${data.data}")
            }
            RxPhotoTool.GET_IMAGE_BY_CAMERA//选择照相机之后的处理
            -> if (resultCode == RESULT_OK) {
                /* data.getExtras().get("data");*/
                //                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
//                if (RxPhotoTool.imageUriFromCamera != null)
                initUCrop(activity, RxPhotoTool.imageUriFromCamera!!)
            }
            RxPhotoTool.CROP_IMAGE//普通裁剪后的处理
            ->
                //                    roadImageView(RxPhotoTool.cropImageUri, ivAvatar);
                RxBus.get().post(BusAction.GET_PICTURE, RxPhotoTool.cropImageUri)

            UCrop.REQUEST_CROP//UCrop裁剪之后的处理
            -> if (resultCode == RESULT_OK) {
                val resultUri = UCrop.getOutput(data)
                //                       roadImageView(resultUri, ivAvatar);
//                RxBus.get().post(BusAction.GET_PICTURE, resultUri)
                photoHandlerListener.onPhotoHandler(resultUri)
            }
        }
    }


    fun initUCrop(activity: Activity, uri: Uri?) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        val timeFormatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
        val time = System.currentTimeMillis()
        val imageName = timeFormatter.format(Date(time))

        val destinationUri = Uri.fromFile(File(activity.cacheDir, imageName + ".png"))

        val options = UCrop.Options()
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL)
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.red_f14343))
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.red_f14343))

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5f)
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666)
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri!!, destinationUri)
                .withAspectRatio(1f, 1f)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(activity)
    }
}
