package com.mansoul.mvvm.imageloader

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.target.Target
import com.mansoul.mvvm.imageloader.GlideApp
import com.mansoul.mvvm.imageloader.GlideRequest
import com.mansoul.mvvm.imageloader.GlideRequests
import com.mansoul.mvvm.utils.isOnMainThread
import java.io.File

/**
 * @author Mansoul
 * @create 2019/3/27 11:11
 * @des    Glide图片加载策略
 */
@Suppress("NAME_SHADOWING")
class GlideImageLoaderStrategy : ImageLoaderStrategy {

    private var context: Context
    private var requestManager: GlideRequests

    constructor(context: Context) {
        this.context = context
        requestManager = GlideApp.with(context)
    }

    constructor(activity: Activity) {
        this.context = activity
        requestManager = GlideApp.with(activity)
    }

    constructor(fragment: Fragment) {
        this.context = fragment.requireContext()
        requestManager = GlideApp.with(fragment)
    }

    override fun loadFromResource(imageView: ImageView, resourceId: Int, options: ImageLoaderOptions) {
        load(imageView, resourceId, options)
    }

    override fun loadFromAssets(imageView: ImageView, assetName: String, options: ImageLoaderOptions) {
        load(imageView, "file:///android_asset/$assetName", options)
    }

    override fun loadFromFile(imageView: ImageView, file: File, options: ImageLoaderOptions) {
        load(imageView, file, options)
    }

    override fun loadFromUri(imageView: ImageView, uri: Uri, options: ImageLoaderOptions) {
        load(imageView, uri, options)
    }

    override fun loadFromUrl(imageView: ImageView, url: String, options: ImageLoaderOptions) {
        loadFromUrl(imageView, url, options, false)
    }

    override fun loadFromUrl(
        imageView: ImageView,
        url: String,
        options: ImageLoaderOptions,
        loadOnlyWifi: Boolean
    ) {
        options.loadOnlyWifi = loadOnlyWifi
        load(imageView, url, options)
    }

    override fun downloadImage(url: String, options: ImageLoaderOptions): Bitmap? {
        return null
    }

    override fun downloadImage(url: String, options: ImageLoaderOptions, downloadOnlyWifi: Boolean): Bitmap? {
        return null
    }

    override fun resumeRequests() {
        requestManager.resumeRequests()
    }

    override fun pauseRequests() {
        requestManager.pauseRequests()
    }

    override fun cancleRequests(any: Any) {
        when (any) {
            is View -> requestManager.clear(any)
            is FutureTarget<*> -> requestManager.clear(any)
            is Target<*> -> requestManager.clear(any)
        }
    }

    override fun clearMemoryCache() {
        if (!isOnMainThread()) {
            throw IllegalArgumentException("You must call this method on the main thread.")
        }
        Glide.get(context).clearMemory()
    }

    override fun clearDiskCache() {
        if (isOnMainThread()) {
            throw IllegalArgumentException("You must call this method on a background thread.")
        }
        Glide.get(context).clearDiskCache()
    }

    private fun <ModelType> load(
        imageView: ImageView,
        model: ModelType,
        options: ImageLoaderOptions?
    ) {
        var options = options
        if (options == null) {
            options = ImageLoaderOptions()
        }
        val request = requestManager.load(model)
        initImageLoaderOptions(request, imageView, model, options)
        request.into(imageView)
    }

    @SuppressLint("CheckResult")
    private fun <ModelType> initImageLoaderOptions(
        request: GlideRequest<Drawable>,
        imageView: ImageView,
        model: ModelType,
        options: ImageLoaderOptions
    ) {
        request.skipMemoryCache(options.skipMemoryCache)
        request.diskCacheStrategy(options.diskCacheStrategy)

        options.placeHolderResId?.let { request.placeholder(it) }
        options.errorResId?.let { request.error(it) }
        options.imageSize?.let {
            if (it.width > 0 && it.height > 0) {
                // 设置加载的图片大小
                request.override(it.width, it.height)
            }
        }


        if (options.asGif) {
            requestManager.asGif()
        }
        if (options.dontAnimate) {
            request.dontAnimate()
        } else if (options.crossFade) {
            options.crossDuration?.let {
                request.transition(
                    DrawableTransitionOptions().crossFade(it)
                )
            }
        }
        if (options.cropType === CropType.CENTER_CROP) {
            // 当图片比ImageView大的时候，会把超过ImageView的部分裁剪掉，尽可能的让ImageView完全填充，但图像可能不会全部显示
            request.centerCrop()
        } else if (options.cropType === CropType.FIT_CENTER) {
            // 会自适应ImageView的大小，并且会完整的显示图片在ImageView中，但是ImageView可能不会完全填充
            request.fitCenter()
        }
        if (options.thumbnail > 0.0f) {
            request.thumbnail(options.thumbnail)
        }
        if (options.loadOnlyWifi) {
            request.onlyRetrieveFromCache(true)
        }
    }

}
