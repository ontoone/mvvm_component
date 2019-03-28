package com.mansoul.mvvm.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mansoul.mvvm.data.repository.HomeRepo

/**
 * @author Mansoul
 * @create 2019/3/27 15:51
 * @des
 */
@Suppress("UNCHECKED_CAST")
class HomeVMFactory(
    private val homeRepo: HomeRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepo) as T
    }
}