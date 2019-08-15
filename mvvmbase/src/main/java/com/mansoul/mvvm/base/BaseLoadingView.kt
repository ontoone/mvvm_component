package com.mansoul.mvvm.base

/**
 * @author Mansoul
 * @create 2019/5/9 18:46
 * @des
 */
interface BaseLoadingView {
    fun onLoadRetry() {
    }

    fun netLoading() {
    }

    fun netLoadSuccess() {
    }

    fun netLoadFailed() {
    }

    fun showEmpty() {
    }
}