package com.mansoul.mvvm.ui.image

import androidx.lifecycle.Observer
import com.mansoul.common.base.BaseFragment
import com.mansoul.mvvm.R
import kotlinx.android.synthetic.main.image_fragment.*

class ImageFragment : BaseFragment<ImageViewModel>() {
    override fun providerVMClass(): Class<ImageViewModel> = ImageViewModel::class.java

    companion object {
        fun newInstance() = ImageFragment()
    }

    override fun getLayoutResId(): Int = R.layout.image_fragment


    override fun initView() {
        mViewMode?.getImage()
    }

    override fun observer(vm: ImageViewModel) {
        vm.mImage.observe(this, Observer {
            text.text = it.toString()
        })
    }

}
