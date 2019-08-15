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
open class LifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    /** Back to the background timestamp  */
    private var backgroundStamp: Long = 0
    /** The number of [Activity] that is running  */
    private var activityCount = 0
    /** Whether the mark is returned to the foreground from the background for the first time  */
    private var isFirstFromBackground = true
    /** The collection of names of [Activity] that have been started  */
    private val treeSet = TreeSet<String>()

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        treeSet.add(activity?.javaClass!!.name)
    }

    override fun onActivityStarted(activity: Activity?) {
        if (activityCount == 0) {
            Logger.i("App cuts to the front desk...")
            onComeBack()
            executeTaskFromBackground()
            if (backgroundStamp > 0) {
                val timeInterval = System.currentTimeMillis() - backgroundStamp
                Logger.i("App stays in the background for：$timeInterval ms")
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
            Logger.i("App cut to the background...")
            BaseApp.instance?.setForeground(false)
            onBackground()
            backgroundStamp = System.currentTimeMillis()
        }
    }

    open fun onComeBack() {

    }

    open fun onBackground() {

    }

    override fun onActivityDestroyed(activity: Activity?) {
        treeSet.remove(activity?.javaClass!!.name)
        if (treeSet.isEmpty()) {
            isFirstFromBackground = true
        }
    }

    /**
     * Get the number of [Activity] that is running
     */
    fun getActivityCount(): Int {
        return activityCount
    }

    /**
     * Determine if the [Activity] of the specified name is activated
     */
    fun checkActivityExists(className: String): Boolean {
        return treeSet.contains(className)
    }

    /**
     * Perform tasks from the background to the foreground
     */
    private fun executeTaskFromBackground() {
        if (isFirstFromBackground) {
            isFirstFromBackground = false
            return
        }
    }

}