package com.mansoul.common.adapter.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.billy.android.loading.Gloading.*
import com.mansoul.common.R
import kotlinx.android.synthetic.main.view_default_loading_status.view.*

/**
 * @author Mansoul
 * @create 2019/3/26 13:18
 * @des
 */
@SuppressLint("ViewConstructor")
@Suppress("UNUSED_EXPRESSION")
class DefaultLoadingStatusView(context: Context, retry: Runnable) : FrameLayout(context), View.OnClickListener {

    private var mRetryTask: Runnable? = retry

    override fun onClick(v: View?) {
        mRetryTask?.run()
    }

    init {
        LayoutInflater.from(context).inflate(
            R.layout.view_default_loading_status, this, true
        )
    }

    fun setStatus(status: Int) {
        var show = true
        var onClickListener: View.OnClickListener? = null
        when (status) {
            STATUS_LOAD_SUCCESS -> show = false
            STATUS_LOADING -> {
                progress_bar.visibility = View.VISIBLE
                bt_try.visibility = View.GONE
                text_view.visibility = View.GONE
            }
            STATUS_LOAD_FAILED -> {
                progress_bar.visibility = View.GONE
                bt_try.visibility = View.VISIBLE
                text_view.visibility = View.GONE
                onClickListener = this
                bt_try.setOnClickListener { this }
            }
            STATUS_EMPTY_DATA -> {
                progress_bar.visibility = View.GONE
                bt_try.visibility = View.GONE
                text_view.visibility = View.VISIBLE
            }
        }
        setOnClickListener(onClickListener)
        visibility = if (show) View.VISIBLE else View.GONE
    }

}