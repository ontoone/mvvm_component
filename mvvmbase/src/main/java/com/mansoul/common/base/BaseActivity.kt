package com.mansoul.common.base

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

        // 初始化
        beforeSetContentView(savedInstanceState)

        // 处理Intent(主要用来获取其中携带的参数)
        if (intent != null) {
            handleIntent(intent)
        }

        setContentView(getLayoutResId())

        initView(savedInstanceState)
        loadData()
    }

    protected fun beforeSuper() {

    }

    protected fun beforeSetContentView(savedInstanceState: Bundle?) {

    }

    override fun setContentView(layoutResID: Int) {
        if (layoutResID == 0) {
            throw NullPointerException("layoutResID can not be null!")
        }
        super.setContentView(layoutResID)

    }

    protected fun handleIntent(intent: Intent) {

    }

    protected fun loadData() {

    }
}