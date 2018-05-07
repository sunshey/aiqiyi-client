package aiqiyi.yc.com.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 *
 * Created by wanglin  on 2018/4/28 08:56.
 */
class GlideHelper {
    companion object {
        fun loadImage(context: Context, imageView: ImageView, url: String, placeholder: Int, isCrop: Boolean, isCircle: Boolean) {
            val options = RequestOptions()
            options.skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.DATA).error(placeholder).placeholder(placeholder)
            if (isCrop) options.centerCrop()
            if (isCircle) options.circleCrop()
            Glide.with(context).load(url).apply(options).thumbnail(0.1f).into(imageView)
        }

        fun loadImage(context: Context, imageView: ImageView, url: String, imgId: Int) {
            loadImage(context, imageView, url, imgId, true, false)
        }

        fun loadImage2(context: Context, imageView: ImageView, url: String, imgId: Int) {
            loadImage(context, imageView, url, imgId, false, true)
        }
    }

}