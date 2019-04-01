package com.mansoul.mvvm.callback

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mansoul.mvvm.base.BaseApp
import com.orhanobut.logger.Logger
import java.util.*

/**
 * @author Mansoul
 * @create 2019/3/21 17:47
 * @des
 */
class LifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    /** 回到后台的时间戳  */
    private var backgroundStamp: Long = 0
    /** 正在运行的[Activity]的数量  */
    private var activityCount = 0
    /** 标记是否首次从后台回到前台  */
    private var isFirstFromBackground = true
    /** 已经启动的[Activity]的名称集合  */
    private val treeSet = TreeSet<String>()

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        treeSet.add(activity?.javaClass!!.name)
    }

    override fun onActivityStarted(activity: Activity?) {
        if (activityCount == 0) {
            Logger.i("App切到前台...")
            executeTaskFromBackground()
            if (backgroundStamp > 0) {
                val timeInterval = System.currentTimeMillis() - backgroundStamp
                Logger.i("App在后台停留时间为：$timeInterval ms")
            }
        }
        activityCount++
        Logger.i(activity?.javaClass!!.name + " Started!")
    }

    override fun onActivityResumed(activity: Activity?) {
        BaseApp.instance?.setForeground(true)
    }

    override fun onActivityPaused(activity: Activity?) {}

    override fun onActivityStopped(activity: Activity?) {
        activityCount--
        Logger.i(activity?.javaClass!!.name + " Stopped!")
        if (activityCount == 0) {
            Logger.i("App切到后台...")
            BaseApp.instance?.setForeground(false)
            backgroundStamp = System.currentTimeMillis()
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        treeSet.remove(activity?.javaClass!!.name)
        if (treeSet.isEmpty()) {
            isFirstFromBackground = true
        }
    }

    /**
     * 获取正在运行的[Activity]的数量
     */
    fun getActivityCount(): Int {
        return activityCount
    }

    /**
     * 判断指定名称的[Activity]是否启动
     */
    fun checkActivityExists(className: String): Boolean {
        return treeSet.contains(className)
    }

    /**
     * 执行从后台切到前台的任务
     */
    private fun executeTaskFromBackground() {
        if (isFirstFromBackground) {
            isFirstFromBackground = false
            return
        }
    }

}