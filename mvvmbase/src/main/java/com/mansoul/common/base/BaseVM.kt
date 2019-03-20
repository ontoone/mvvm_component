package com.mansoul.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mansoul.common.http.exception.ExceptionEngine
import com.mansoul.common.http.exception.HttpException
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * @author Mansoul
 * @create 2019/3/20 11:47
 * @des
 */
abstract class BaseVM : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val mLaunchManager: MutableList<Job> = mutableListOf()
    private var subscribeTime: Long = 0

    val mException = MutableLiveData<HttpException>()

    protected fun vmLaunch(actionBlock: suspend CoroutineScope.() -> Unit) {
        val job = launch {
            try {
                onStart()
                actionBlock.invoke(this)
            } catch (e: Exception) {
                onError(e)
            } finally {
                onComplete()
            }
        }
        mLaunchManager.add(job)
        job.invokeOnCompletion { mLaunchManager.remove(job) }
    }

    private fun onStart() {
        subscribeTime = System.currentTimeMillis()
    }

    private fun onStop() {}

    fun onComplete() {
        onStop()
    }

    private fun onError(throwable: Throwable) {
        onStop()
        val exception: HttpException = if (throwable is HttpException) {
            throwable
        } else {
            ExceptionEngine.handleException(throwable)
        }
        exception.responseTime = getResponTime()
        Logger.e(exception.toString())
        onException(exception)
    }

    private fun onException(exception: HttpException) {
        mException.value = exception
    }

    override fun onCleared() {
        super.onCleared()
        Logger.i("onCleared")
        clearLaunchTask()
    }

    private fun clearLaunchTask() {
        mLaunchManager.clear()
    }

    private fun getResponTime(): String {
        val interval = System.currentTimeMillis() - subscribeTime
        val responTime: String
        responTime = if (interval > 1000) {// 如果大于1000ms，则换用s来显示
            "${interval / 1000}s"
        } else {
            "${interval}ms"
        }
        return responTime
    }

}