package com.mansoul.common.imageloader

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.orhanobut.logger.Logger

/**
 * @author Mansoul
 * @create 2019/3/26 16:56
 * @des
 */
@GlideModule
class MGlideModule : AppGlideModule() {

    private val DISK_SIZE = 200 * 1024 * 1024 // 200MB
    private val MEMORY_SIZE = Runtime.getRuntime().maxMemory().toInt() / 4  // 取最大内存的1/4作为最大缓存
    private val DISK_CACHE_NAME = "image"

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        Logger.e("GlideModule初始化...")

        // 定义缓存大小和位置
        ExternalPreferredCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR

        // 外部存储
        builder.setDiskCache(
            ExternalPreferredCacheDiskCacheFactory(
                context,
                DISK_CACHE_NAME,
                DISK_SIZE.toLong()
            )
        )

        // 自定义内存和图片池大小
        builder.setMemoryCache(LruResourceCache(MEMORY_SIZE.toLong())) // 设置缓存内存大小
        builder.setBitmapPool(LruBitmapPool(MEMORY_SIZE.toLong())) // 设置图片池大小

        // 定义图片格式
//        builder.setDefaultRequestOptions(
//            RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
//        )
    }
}