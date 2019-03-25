package com.mansoul.mvvm.ui.image

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mansoul.common.base.BaseFragment
import com.mansoul.mvvm.R
import kotlinx.android.synthetic.main.image_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ImageFragment : BaseFragment<ImageViewModel>(), KodeinAware {

    override val kodein by closestKodein()
    private val imageVMFactory: ImageVMFactory by instance()

    override fun providerVMClass(): Class<ImageViewModel> = ImageViewModel::class.java

    override fun providerVMFactory(): ViewModelProvider.NewInstanceFactory? {
        return imageVMFactory
    }

    companion object {
        fun newInstance() = ImageFragment()
    }

    override fun getLayoutResId(): Int = R.layout.image_fragment


    override fun initView() {
        bindUi()
    }

    private fun bindUi() = netLaunch {
        mViewMode?.getImage()
    }

    override fun observer(vm: ImageViewModel) {
        vm.image.observe(this, Observer {
            text.text = it.toString()
        })
    }

}
