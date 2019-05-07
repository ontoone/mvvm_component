package com.mansoul.wanandroid

import android.os.Bundle
import com.mansoul.mvvm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Mansoul
 * @create 2019/4/2 18:04
 * @des
 */
class MainActivity : BaseActivity() {

    var titles = arrayOf("a", "b", "c")

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.title = ""
        setSupportActionBar(toolbar)

        addTab()
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun addTab() {
        titles.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }

    }

}