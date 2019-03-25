package com.mansoul.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mansoul.common.http.exception.ExceptionEngine
import com.mansoul.common.http.exception.HttpException
import com.mansoul.common.utils.obtainViewModel
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @author Mansoul
 * @create 2019/3/21 14:55
 * @des
 */
abstract class BaseFragment<VM : BaseVM> : Fragment(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private val mLaunchManager: MutableList<Job> = mutableListOf()

    private var subscribeTime: Long = 0

    var mViewMode: VM? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLaunchManager.clear()
    }

    abstract fun getLayoutResId(): Int

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createVM()
        observer()
        initView()
    }

    open fun netLaunch(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        actionBlock: suspend CoroutineScope.() -> Unit
    ) {
        val job = launch(dispatcher) {
            try {
                onLaunchStart()
                actionBlock.invoke(this)
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

    }

    open fun onLaunchStop() {
        Logger.e("onLaunchStop")

    }

    open fun onLaunchFinally() {
        onLaunchStop()
        Logger.e("onLaunchFinally")

    }

    open fun onException(exception: HttpException) {

    }

    private fun onLaunchError(throwable: Throwable) {
        onLaunchStop()
        Logger.e("onLaunchError")
        val exception: HttpException = if (throwable is HttpException) {
            throwable
        } else {
            ExceptionEngine.handleException(throwable)
        }
        exception.responseTime = getResponseTime()
        Logger.e(exception.toString())
        onException(exception)
    }

    abstract fun initView()

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

    /**
     * [BaseVM]的实现类
     */
    abstract fun providerVMClass(): Class<VM>

    private fun observer() {
        mViewMode?.apply {
            observer(this)
        }
    }

    /**
     * 观察[mViewMode]
     */
    open fun observer(vm: VM) {

    }

    private fun getResponseTime(): String {
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
