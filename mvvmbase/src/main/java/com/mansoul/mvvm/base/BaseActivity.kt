package com.mansoul.mvvm.base

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.billy.android.loading.Gloading
import com.mansoul.mvvm.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import kotlin.coroutines.CoroutineContext

/**
 * @author Mansoul
 * @create 2019/3/21 15:45
 * @des
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope, KodeinAware, BaseLoadingView {

    override val kodein by closestKodein()

    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    open var mGloadingHolder: Gloading.Holder? = null

    open var mToolBar: Toolbar? = null

    open lateinit var context: Context

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun getLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeSuper()
        super.onCreate(savedInstanceState)

        context = this

        beforeSetContentView(savedInstanceState)

        if (intent != null) {
            handleIntent(intent)
        }

        setContentView(getLayoutResId())

        initToolbar()

        initNetLoadingView()

        job = Job()

        initView(savedInstanceState)

        loadData()
    }

    ////// toolbar start ////////////
    private fun initToolbar() {
        mToolBar = findViewById(toolbarId())
        if (mToolBar == null) return
        val actionBar = supportActionBar
        if (actionBar != null) {
            // 是否显示应用程序图标，默认为true，对应id为android.R.id.home
            actionBar.setDisplayShowHomeEnabled(!hideHomeUp())
            // 是否将应用程序图标转变成可点击的按钮，默认为false。如果设置了setDisplayHomeAsUpEnabled为true，则该设置自动为 true。
            actionBar.setHomeButtonEnabled(!hideHomeUp())
            // 注意：setHomeButtonEnabled和setDisplayShowHomeEnabled共同起作用
            // 如果setHomeButtonEnabled设成false，即使setDisplayShowHomeEnabled设成true，图标也不能点击
            actionBar.setDisplayHomeAsUpEnabled(!hideHomeUp()) // 在应用程序图标的左边显示一个向左的箭头，默认为false。
        }
        if (!hideHomeUp()) {
            if (navigationResId() != 0) {
                mToolBar?.setNavigationIcon(navigationResId()) // 设置导航按钮图标
            } else {
                mToolBar?.setNavigationIcon(R.drawable.ic_back) // 设置默认导航按钮图标
            }
        }
        mToolBar?.setContentInsetsRelative(0, 0)
        mToolBar?.setNavigationOnClickListener { onBackClick() }
        initToolbarTitle(title(), centerTitle(), titleSize())
    }

    open fun titleSize(): Float {
        return 16f
    }

    open fun centerTitle(): Boolean {
        return true
    }

    open fun title(): String {
        return ""
    }

    open fun toolbarId(): Int {
        return R.id.toolbar
    }

    open fun navigationResId(): Int {
        return 0
    }

    /**
     * 是否隐藏返回按钮
     */
    private fun hideHomeUp(): Boolean {
        return false
    }

    /**
     * 初始化标题栏
     *
     * @param title    标题
     */
    open fun initToolbarTitle(title: CharSequence = "", centerTitle: Boolean = true, titleSize: Float = 16f) {
        if (mToolBar != null) {
            val params = Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            if (centerTitle) {
                params.gravity = Gravity.CENTER
            }
            val tvTitle = TextView(this)
            tvTitle.gravity = Gravity.CENTER
            tvTitle.setTextColor(ContextCompat.getColor(this, toolbarTitleColor()))
            tvTitle.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            tvTitle.textSize = titleSize
            tvTitle.text = title
            mToolBar?.addView(tvTitle, params)
        }
    }

    open fun toolbarTitleColor(): Int {
        return R.color.white
    }


    ////// toolbar end ////////////

    private fun initNetLoadingView() {
        if (!netLoadingEnable()) return
        mGloadingHolder = Gloading.getDefault().wrap(this).withRetry {
            onLoadRetry()
        }
    }

    open fun netLoadingEnable(): Boolean {
        return true
    }

    override fun netLoading() {
        mGloadingHolder?.showLoading()
    }

    override fun netLoadSuccess() {
        mGloadingHolder?.showLoadSuccess()
    }

    override fun netLoadFailed() {
        mGloadingHolder?.showLoadFailed()
    }

    override fun showEmpty() {
        mGloadingHolder?.showEmpty()
    }

    override fun onLoadRetry() {

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

    override fun onBackPressed() {
        super.onBackPressed()
    }

    /**
     * 返回事件处理
     */
    fun onBackClick() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }
}