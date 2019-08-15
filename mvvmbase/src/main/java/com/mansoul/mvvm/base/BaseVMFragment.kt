package com.mansoul.mvvm.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mansoul.mvvm.http.exception.ExceptionEngine
import com.mansoul.mvvm.http.exception.HttpException
import com.mansoul.mvvm.utils.obtainViewModel
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*

/**
 * @author Mansoul
 * @create 2019/3/21 14:55
 * @des
 */
abstract class BaseVMFragment<VM : BaseVM> : BaseFragment() {

    private val mLaunchManager: MutableList<Job> = mutableListOf()

    private var subscribeTime: Long = 0

    var mViewMode: VM? = null

    override fun onDestroy() {
        super.onDestroy()
        mLaunchManager.clear()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        createVM()
        observer()
        super.onActivityCreated(savedInstanceState)
    }

    open fun netLaunch(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        actionBlock: suspend CoroutineScope.() -> Unit
    ) {
        val job = launch(dispatcher) {
            try {
                onLaunchStart()
                actionBlock.invoke(this)
                onLaunchSuccess()
            } catch (e: Exception) {
                onLaunchError(e)
            } finally {
                onLaunchFinally()
            }
        }
        mLaunchManager.add(job)
        job.invokeOnCompletion {
            job.cancel()
            mLaunchManager.remove(job)
        }
    }

    open fun onLaunchStart() {
        Logger.e("onLaunchStart")
        subscribeTime = System.currentTimeMillis()
        netLoading()
    }

    open fun onLaunchSuccess() {
        Logger.e("onLaunchSuccess")
        netLoadSuccess()
    }

    open fun onLaunchFinally() {
    }

    private fun onLaunchError(throwable: Throwable) {
        Logger.e("onLaunchError")
        netLoadFailed()
        val exception: HttpException = if (throwable is HttpException) {
            throwable
        } else {
            ExceptionEngine.handleException(throwable)
        }
        exception.responseTime = getResponseTime()
        Logger.e(exception.toString())
        handleException(exception)
    }

    open fun handleException(exception: HttpException) {

    }

    private fun createVM() {
        val vmFactory = providerVMFactory()
        providerVMClass().let {
            mViewMode = if (vmFactory == null) {
                obtainViewModel(it)
            } else {
                obtainViewModel(it, vmFactory)
            }
        }
    }

    open fun providerVMFactory(): ViewModelProvider.NewInstanceFactory? {
        return null
    }

    abstract fun providerVMClass(): Class<VM>

    private fun observer() {
        mViewMode?.apply {
            observer(this)
        }
    }

    /**
     * observer [mViewMode]
     */
    open fun observer(vm: VM) {

    }

    private fun getResponseTime(): String {
        val interval = System.currentTimeMillis() - subscribeTime
        val responTime: String
        responTime = if (interval > 1000) {
            "${interval / 1000}s"
        } else {
            "${interval}ms"
        }
        return responTime
    }
}
