package com.mansoul.common.base

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


/**
 * @author Mansoul
 * @create 2019/3/20 11:02
 * @des
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    companion object {
        var instance: BaseApp? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

}