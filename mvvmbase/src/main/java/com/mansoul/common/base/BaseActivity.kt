package com.mansoul.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mansoul.common.utils.obtainViewModel

/**
 * @author Mansoul
 * @create 2019/3/20 14:50
 * @des
 */
abstract class BaseActivity<VM : BaseVM> : AppCompatActivity() {

    var mViewMode: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        createVM()
        initView()
        observer()
    }

    abstract fun getLayoutResId(): Int

    private fun createVM() {
        providerVMClass()?.let {
            mViewMode = obtainViewModel(it)
        }
    }

    abstract fun initView()

    /**
     * [BaseVM]的实现类
     */
    open fun providerVMClass(): Class<VM>? = null

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