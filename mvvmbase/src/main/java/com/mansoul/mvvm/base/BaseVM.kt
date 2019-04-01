package com.mansoul.mvvm.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * @author Mansoul
 * @create 2019/3/20 11:47
 * @des
 */
abstract class BaseVM : ViewModel(), CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}