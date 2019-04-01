package com.mansoul.mvvm.base

import com.mansoul.mvvm.utils.obtainViewModel

/**
 * @author Mansoul
 * @create 2019/3/20 14:50
 * @des
 */
abstract class BaseVMActivity<VM : BaseVM> : BaseActivity() {

    var mViewMode: VM? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        createVM()
        observer()
    }

    private fun createVM() {
        providerVMClass().let {
            mViewMode = obtainViewModel(it)
        }
    }

    /**
     * observer data[mViewMode]
     */
    open fun observer(vm: VM) {

    }

    abstract fun providerVMClass(): Class<VM>

    private fun observer() {
        mViewMode?.apply {
            observer(this)
        }
    }
}