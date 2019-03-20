package com.mansoul.mvvm

import androidx.lifecycle.Observer
import com.mansoul.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainVM>() {

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun providerVMClass(): Class<MainVM> = MainVM::class.java

    override fun initView() {
        click.setOnClickListener {
            mViewMode?.getWeather()
        }
    }

    override fun observer(vm: MainVM) {
        vm.mWeather.observe(this, Observer {
            text.text = it.string()
        })

        vm.mException.observe(this, Observer {
            text.text = it.toString()
        })
    }


}
