package com.mansoul.mvvm.base

import android.app.Application
import android.content.Context
import com.billy.android.loading.Gloading
import com.mansoul.mvvm.BuildConfig
import com.mansoul.mvvm.adapter.DefaultGloadingAdapter
import com.mansoul.mvvm.callback.LifecycleCallbacks
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


/**
 * @author Mansoul
 * @create 2019/3/20 11:02
 * @des
 */
open class BaseApp : Application() {

    private var isForeground: Boolean = false

    companion object {
        var instance: BaseApp? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Logger.addLogAdapter(AndroidLogAdapter())
        registerActivityLifecycleCallbacks(providerLifecycleCallbacks())
        initStatusView()
    }

    open fun providerLifecycleCallbacks(): ActivityLifecycleCallbacks {
        return LifecycleCallbacks()
    }

    private fun initStatusView() {
        Gloading.debug(BuildConfig.DEBUG)
        Gloading.initDefault(DefaultGloadingAdapter())
    }

    open fun providerGloadingAdapter(): Gloading.Adapter {
        return DefaultGloadingAdapter()
    }

    fun isForeground(): Boolean {
        return isForeground
    }

    fun setForeground(foreground: Boolean) {
        isForeground = foreground
    }

}