package com.mansoul.sample.ui.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mansoul.sample.data.repository.ImageRepo

/**
 * @author Mansoul
 * @create 2019/3/25 16:59
 * @des
 */
@Suppress("UNCHECKED_CAST")
class ImageVMFactory(
    private val imageRepo: ImageRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageViewModel(imageRepo) as T
    }
}