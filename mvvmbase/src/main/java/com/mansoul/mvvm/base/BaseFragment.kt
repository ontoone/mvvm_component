package com.mansoul.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.billy.android.loading.Gloading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import kotlin.coroutines.CoroutineContext

/**
 * @author Mansoul
 * @create 2019/3/26 14:56
 * @des
 */
abstract class BaseFragment : Fragment(), CoroutineScope, KodeinAware, BaseLoadingView {

    override val kodein by closestKodein()

    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    open var mGloadingHolder: Gloading.Holder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            handleBundle(bundle) // 处理Bundle(主要用来获取其中携带的参数)
        }
    }

    open fun handleBundle(bundle: Bundle) {

    }

    open fun netLoadingEnable(): Boolean {
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        mGloadingHolder = Gloading.getDefault().wrap(view).withRetry { onLoadRetry() }
        return mGloadingHolder?.wrapper
    }

    abstract fun getLayoutResId(): Int

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        job = Job()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    abstract fun initView()

    override fun onLoadRetry() {
    }

    override fun netLoading() {
        if (netLoadingEnable()) {
            mGloadingHolder?.showLoading()
        }
    }

    override fun netLoadSuccess() {
        if (netLoadingEnable()) {
            mGloadingHolder?.showLoadSuccess()
        }
    }

    override fun netLoadFailed() {
        if (netLoadingEnable()) {
            mGloadingHolder?.showLoadFailed()
        }
    }

    override fun showEmpty() {
        if (netLoadingEnable()) {
            mGloadingHolder?.showEmpty()
        }
    }
}