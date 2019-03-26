package com.mansoul.mvvm.ui.home

import com.mansoul.common.base.BaseVMFragment
import com.mansoul.mvvm.R

class HomeFragment : BaseVMFragment<HomeViewModel>() {

    override fun getLayoutResId(): Int = R.layout.home_fragment
    override fun providerVMClass(): Class<HomeViewModel> = HomeViewModel::class.java

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initView() {
    }


}
