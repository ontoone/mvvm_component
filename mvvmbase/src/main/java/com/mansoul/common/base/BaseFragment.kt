package com.mansoul.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mansoul.common.utils.obtainViewModel

/**
 * @author Mansoul
 * @create 2019/3/21 14:55
 * @des
 */
abstract class BaseFragment<VM : BaseVM> : Fragment() {

    var mViewMode: VM? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    abstract fun getLayoutResId(): Int

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createVM()
        observer()
        initView()
    }

    abstract fun initView()

    private fun createVM() {
        providerVMClass()?.let {
            mViewMode = obtainViewModel(it)
        }
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
}
