package com.mansoul.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.billy.android.loading.Gloading
import com.mansoul.common.http.exception.ExceptionEngine
import com.mansoul.common.http.exception.HttpException
import com.mansoul.common.utils.obtainViewModel
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import kotlin.coroutines.CoroutineContext

/**
 * @author Mansoul
 * @create 2019/3/21 14:55
 * @des
 */
abstract class BaseVMFragment<VM : BaseVM> : Fragment(), CoroutineScope, KodeinAware {

    override val kodein by closestKodein()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private val mLaunchManager: MutableList<Job> = mutableListOf()

    private var subscribeTime: Long = 0

    open var mGloadingHolder: Gloading.Holder? = null

    var mViewMode: VM? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        mGloadingHolder = Gloading.getDefault().wrap(view).withRetry { onLoadRetry() }
        return mGloadingHolder?.wrapper
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
        showLoading()
    }

    open fun onLaunchSuccess() {
        Logger.e("onLaunchSuccess")
        showLoadSuccess()
    }

    open fun onLaunchFinally() {
    }

    private fun onLaunchError(throwable: Throwable) {
        Logger.e("onLaunchError")
        showLoadFailed()
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


    open fun onLoadRetry() {
    }

    open fun showLoading() {
        mGloadingHolder?.showLoading()
    }

    open fun showLoadSuccess() {
        mGloadingHolder?.showLoadSuccess()
    }

    open fun showLoadFailed() {
        mGloadingHolder?.showLoadFailed()
    }

    open fun showEmpty() {
        mGloadingHolder?.showEmpty()
    }
}
