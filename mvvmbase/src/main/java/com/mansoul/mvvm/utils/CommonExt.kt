package com.mansoul.mvvm.utils

import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * @author Mansoul
 * @create 2019/3/21 14:58
 * @des
 */

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this).get(viewModelClass)

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this).get(viewModelClass)

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>, factory: ViewModelProvider.NewInstanceFactory) =
    ViewModelProviders.of(this, factory).get(viewModelClass)

fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()