package com.mansoul.mvvm.ui.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mansoul.common.base.BaseVMFragment
import com.mansoul.mvvm.R
import kotlinx.android.synthetic.main.home_fragment.*
import me.drakeet.multitype.MultiTypeAdapter
import org.kodein.di.generic.instance

class HomeFragment : BaseVMFragment<HomeViewModel>() {

    override fun getLayoutResId(): Int = R.layout.home_fragment
    override fun providerVMClass(): Class<HomeViewModel> = HomeViewModel::class.java

    private val homeVMFactory: HomeVMFactory by instance()

    private lateinit var adapter: MultiTypeAdapter

    override fun providerVMFactory(): ViewModelProvider.NewInstanceFactory? {
        return homeVMFactory
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        adapter = MultiTypeAdapter()

        adapter.register(HomeAdapter())

        recycler_view.adapter = adapter
        netLaunch {
            mViewMode?.getAndroid()
        }
    }

    override fun observer(vm: HomeViewModel) {
        vm.android.observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
    }

}
