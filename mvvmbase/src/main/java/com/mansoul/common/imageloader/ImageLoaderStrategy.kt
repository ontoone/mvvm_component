package com.mansoul.common.imageloader

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import java.io.File

/**
 * @author Mansoul
 * @create 2019/3/26 17:06
 * @des
 */
interface ImageLoaderStrategy {

    /**
     * 加载资源图片
     *
     * @param imageView  ImageView对象
     * @param resourceId 图片资源ID
     * @param options    图片加载配置选项
     */
    fun loadFromResource(imageView: ImageView, resourceId: Int, options: ImageLoaderOptions = ImageLoaderOptions())

    /**
     * 加载Assets图片
     *
     * @param imageView ImageView对象
     * @param assetName Assets图片名称
     * @param options   图片加载配置选项
     */
    fun loadFromAssets(imageView: ImageView, assetName: String, options: ImageLoaderOptions = ImageLoaderOptions())

    /**
     * 加载本地图片
     *
     * @param imageView ImageView对象
     * @param file      本地图片文件
     * @param options   图片加载配置选项
     */
    fun loadFromFile(imageView: ImageView, file: File, options: ImageLoaderOptions = ImageLoaderOptions())

    /**
     * 加载指定URI的图片
     */
    fun loadFromUri(imageView: ImageView, uri: Uri, options: ImageLoaderOptions = ImageLoaderOptions())

    /**
     * 加载网络图片
     *
     * @param imageView ImageView对象
     * @param url       图片地址Url
     * @param options   图片加载配置选项
     */
    fun loadFromUrl(imageView: ImageView, url: String, options: ImageLoaderOptions = ImageLoaderOptions())

    /**
     * 加载网络图片
     *
     * @param imageView    ImageView对象
     * @param url          图片地址Url
     * @param options      图片加载配置选项
     * @param loadOnlyWifi 是否仅在WIFI情况下加载
     */
    fun loadFromUrl(imageView: ImageView, url: String, options: ImageLoaderOptions = ImageLoaderOptions(), loadOnlyWifi: Boolean)

    /**
     * 下载图片
     *
     * @param url              图片地址Url
     * @param options          图片加载配置选项
     */
    fun downloadImage(url: String, options: ImageLoaderOptions = ImageLoaderOptions()): Bitmap?

    /**
     * 下载图片
     *
     * @param url              图片地址Url
     * @param options          图片加载配置选项
     * @param downloadOnlyWifi 是否仅在Wifi情况下下载
     */
    fun downloadImage(url: String, options: ImageLoaderOptions = ImageLoaderOptions(), downloadOnlyWifi: Boolean): Bitmap?

    /**
     * 恢复请求
     */
    fun resumeRequests()

    /**
     * 暂停请求
     */
    fun pauseRequests()

    /**
     * 取消请求
     */
    fun cancleRequests(any: Any)

    /**
     * 清除内存缓存
     */
    fun clearMemoryCache()

    /**
     * 清除磁盘缓存
     */
    fun clearDiskCache()

}