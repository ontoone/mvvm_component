//package com.mansoul.mvvm.view
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.Toolbar
//import com.mansoul.mvvm.R
//
///**
// * @author Mansoul
// * @create 2019/4/3 11:37
// * @des
// */
//class ToolbarHelper() {
//
//    private val ATTRS = intArrayOf(R.attr.windowActionBarOverlay, R.attr.actionBarSize)
//
//    private var mContentView: FrameLayout? = null
//
//    private lateinit var mActivity: AppCompatActivity
//    private var mToolbar: Toolbar? = null
//    private var topMargin: Int = 0
//
//    /**
//     * 构造方法(在[AppCompatActivity]#setContentView(int))方法之后调用)
//     * 注意：视图容器中需包含[Toolbar]，且控件id为toolbar
//     */
//    constructor(activity: AppCompatActivity) : this() {
//        this.mActivity = activity
//        mToolbar = activity.findViewById(R.id.toolbar)
//        initToolbar()
//    }
//
//    /**
//     * 构造方法(在[AppCompatActivity]#setContentView(int))方法之前调用)
//     * 视图容器中无需包含[Toolbar]
//     */
//    constructor(activity: AppCompatActivity, layoutResId: Int) : this() {
//        this.mActivity = activity
//        val layoutInflater = LayoutInflater.from(mActivity)
//        initAttrs()
//        initContentView()
//        initCustomView(layoutInflater, layoutResId)
//        val view = layoutInflater.inflate(R.layout.layout_toolbar_common, mContentView)// 通过inflater获取Toolbar的布局文件
//        mToolbar = view.findViewById(R.id.toolbar)
//    }
//
//    private fun initCustomView(layoutInflater: LayoutInflater, layoutResId: Int) {
//        val view = layoutInflater.inflate(layoutResId, null)
//        view.fitsSystemWindows = true
//        val customParams =
//            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        customParams.topMargin = topMargin
//        mContentView!!.addView(view, customParams)
//    }
//
//    private fun initContentView() {
//        mContentView = FrameLayout(mActivity)
//        mContentView!!.id = R.id.root_content
//        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        mContentView!!.layoutParams = params
//    }
//
//    @SuppressLint("ResourceType")
//    private fun initAttrs() {
//        val typedArray = mActivity.theme.obtainStyledAttributes(ATTRS)
//        try {
//            val actionBarSize = mActivity.resources.getDimension(R.dimen.actionBarSize).toInt()
//            val overly = typedArray.getBoolean(0, false) // 获取主题中定义的悬浮标志
//            val toolBarSize = typedArray.getDimension(1, actionBarSize.toFloat()).toInt() // 获取主题中定义的Toolbar的高度
//            topMargin = if (overly) 0 else toolBarSize// 如果是悬浮状态，则不需要设置间距
//        } finally {
//            typedArray.recycle()
//        }
//    }
//
//    private fun initToolbar() {
//        if (mToolbar != null) {
//            mActivity.setSupportActionBar(mToolbar)
//            mToolbar!!.setContentInsetsRelative(0, 0)
//        }
//    }
//
//
//}