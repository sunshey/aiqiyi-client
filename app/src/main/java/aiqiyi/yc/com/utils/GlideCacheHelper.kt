package aiqiyi.yc.com.utils

import android.content.Context
import android.os.Looper
import android.text.TextUtils

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory

import java.io.File

/**
 * Created by wanglin  on 2018/2/7 10:34.
 */

class GlideCacheHelper(private val context: Context) {
    private val cache_path: String = context.cacheDir.toString() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR


    val cacheSize: String
        get() {
            return try {
                ACache.getFormatSize(ACache.getFolderSize(File(cache_path)).toDouble())
            } catch (e: Exception) {
                e.printStackTrace()
                "获取失败"
            }

        }

    init {
        //                DiskCache.Factory.DEFAULT_DISK_CACHE_DIR;
    }

    fun clearCache(): Boolean {

        clearMemoryCache()
        //        clearDiskCache();
        return deleteFolderFile(cache_path, true)

    }


    private fun clearMemoryCache(): Boolean {

        //运行在主线程
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(context).clearMemory()
                return true
            }
        } catch (e: Exception) {
        }

        return false
    }

    private fun clearDiskCache(): Boolean {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Thread(Runnable { Glide.get(context).clearDiskCache() }).start()
            } else {
                Glide.get(context).clearDiskCache()
            }
            return true

        } catch (e: Exception) {

        }

        return false
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private fun deleteFolderFile(filePath: String, deleteThisPath: Boolean): Boolean {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                val file = File(filePath)
                if (file.isDirectory) {
                    val files = file.listFiles()
                    for (file1 in files) {
                        deleteFolderFile(file1.absolutePath, true)
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory) {
                        file.delete()
                    } else {
                        if (file.listFiles().isEmpty()) {
                            file.delete()
                        }
                    }
                }
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return false
    }

    companion object {

        private var instance: GlideCacheHelper? = null

        fun getInstance(context: Context): GlideCacheHelper {
            if (instance == null)
                synchronized(GlideCacheHelper::class.java) {
                    if (instance == null) {
                        instance = GlideCacheHelper(context)
                    }
                }
            return instance!!
        }
    }

}
