package com.mansoul.common.base

import android.app.Application
import android.content.Context
import com.billy.android.loading.Gloading
import com.mansoul.common.BuildConfig
import com.mansoul.common.adapter.DefaultGloadingAdapter
import com.mansoul.common.callback.LifecycleCallbacks
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


/**
 * @author Mansoul
 * @create 2019/3/20 11:02
 * @des
 */
open class BaseApp : Application() {

    /**
     * 是否处于前台
     */
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
        registerActivityLifecycleCallbacks(LifecycleCallbacks())
        initStatusView()
    }

    private fun initStatusView() {
        Gloading.debug(BuildConfig.DEBUG)
        Gloading.initDefault(
            if (providerGloadingAdapter() != null) providerGloadingAdapter()
            else DefaultGloadingAdapter()
        )
    }

    open fun providerGloadingAdapter(): Gloading.Adapter? {
        return null
    }

    /**
     * 判断APP是否在前台
     */
    fun isForeground(): Boolean {
        return isForeground
    }

    /**
     * 设置APP是否在前台
     */
    fun setForeground(foreground: Boolean) {
        isForeground = foreground
    }

}