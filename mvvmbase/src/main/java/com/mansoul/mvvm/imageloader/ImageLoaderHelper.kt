package com.mansoul.mvvm.imageloader

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

/**
 * @author Mansoul
 * @create 2019/3/27 11:17
 * @des
 */
class ImageLoaderHelper {
    companion object {
        fun with(context: Context): ImageLoaderStrategy {
            return GlideImageLoaderStrategy(context)
        }

        fun with(activity: Activity): ImageLoaderStrategy {
            return GlideImageLoaderStrategy(activity)
        }

        fun with(fragment: Fragment): ImageLoaderStrategy {
            return GlideImageLoaderStrategy(fragment)
        }
    }

}