package com.mansoul.mvvm.ui.home

import androidx.lifecycle.Observer
import com.mansoul.common.base.BaseFragment
import com.mansoul.mvvm.R
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment<HomeViewModel>() {

    override fun getLayoutResId(): Int = R.layout.home_fragment
    override fun providerVMClass(): Class<HomeViewModel> = HomeViewModel::class.java

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initView() {
        mViewMode?.getImage()
    }

    override fun observer(vm: HomeViewModel) {
        vm.mImage.observe(this, Observer {
            if (it != null) {
                tv.text = it.toString()
            }
        })
    }

}
