package com.mansoul.common.imageloader

import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * @author Mansoul
 * @create 2019/3/27 10:38
 * @des 图片加载配置选项
 */

// 没有占位图
const val RESOURCE_NONE = -1

class ImageLoaderOptions(
    // 默认占位资源
    var placeHolderResId: Int? = null,
    // 错误时显示的资源
    var errorResId: Int? = null,
    // 是否禁止动画
    var dontAnimate: Boolean = true,
    // 是否淡入淡出动画
    var crossFade: Boolean = false,
    // 淡入淡出动画持续的时间
    var crossDuration: Int? = null,
    // 图片最终显示在ImageView上的宽高度像素
    val imageSize: ImageSize? = null,
    // 裁剪类型(默认为中部裁剪)
    val cropType: CropType = CropType.FIT_CENTER,
    // true表示强制显示的是gif动画,如果url不是gif的资源,那么会回调error()
    val asGif: Boolean = false,
    // 是否跳过内存缓存(默认false不跳过)
    val skipMemoryCache: Boolean = false,
    // 硬盘缓存(默认为all类型)
    val diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC,
    // 设置缩略图的缩放比例0.0f-1.0f,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示
    val thumbnail: Float = 0.0f,
//    val requestListener: ImageLoadingListener<*>
    var loadOnlyWifi: Boolean = false
)

/**
 * 裁剪类型
 */
enum class CropType {

    /**
     * 中间裁剪
     */
    CENTER_CROP,
    /**
     * 适合居中
     */
    FIT_CENTER
}

/**
 * 图片尺寸(最终显示在ImageView上的宽高像素)
 */
data class ImageSize(val width: Int, val height: Int)
