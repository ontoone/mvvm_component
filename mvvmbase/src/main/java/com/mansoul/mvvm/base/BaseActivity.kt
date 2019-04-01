package com.mansoul.mvvm.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Mansoul
 * @create 2019/3/21 15:45
 * @des
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun getLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeSuper()
        super.onCreate(savedInstanceState)

        beforeSetContentView(savedInstanceState)

        if (intent != null) {
            handleIntent(intent)
        }

        setContentView(getLayoutResId())

        initView(savedInstanceState)
        loadData()
    }

    open fun beforeSuper() {

    }

    open fun beforeSetContentView(savedInstanceState: Bundle?) {

    }

    open fun handleIntent(intent: Intent) {

    }

    override fun setContentView(layoutResID: Int) {
        if (layoutResID == 0) {
            throw NullPointerException("layoutResID can not be null!")
        }
        super.setContentView(layoutResID)

    }

    open fun loadData() {

    }
}