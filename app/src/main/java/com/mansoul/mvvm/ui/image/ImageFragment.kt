package com.mansoul.mvvm.ui.image

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mansoul.common.base.BaseVMFragment
import com.mansoul.common.imageloader.ImageLoaderHelper
import com.mansoul.mvvm.R
import kotlinx.android.synthetic.main.image_fragment.*
import org.kodein.di.generic.instance

class ImageFragment : BaseVMFragment<ImageViewModel>() {

    private val imageVMFactory: ImageVMFactory by instance()

    override fun providerVMClass(): Class<ImageViewModel> = ImageViewModel::class.java

    override fun providerVMFactory(): ViewModelProvider.NewInstanceFactory? {
        return imageVMFactory
    }

    companion object {
        fun newInstance() = ImageFragment()
    }

    override fun getLayoutResId(): Int = R.layout.image_fragment

    override fun onLoadRetry() {
        super.onLoadRetry()
        bindUi()
    }

    override fun initView() {
        bindUi()
    }

    private fun bindUi() = netLaunch {
        mViewMode?.getImage()
    }

    override fun observer(vm: ImageViewModel) {
        vm.image.observe(this, Observer {
            ImageLoaderHelper.with(this)
                .loadFromUrl(image_view, it?.get(0)!!.url)
        })
    }

}
