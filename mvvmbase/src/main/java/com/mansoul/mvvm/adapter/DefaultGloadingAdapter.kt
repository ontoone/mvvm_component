package com.mansoul.mvvm.adapter

import android.view.View
import com.billy.android.loading.Gloading
import com.mansoul.mvvm.adapter.view.DefaultLoadingStatusView

/**
 * @author Mansoul
 * @create 2019/3/26 11:42
 * @des
 */
class DefaultGloadingAdapter : Gloading.Adapter {
    override fun getView(holder: Gloading.Holder, convertView: View?, status: Int): View {
        var loadingStatusView: DefaultLoadingStatusView? = null

        if (convertView != null && convertView is DefaultLoadingStatusView) {
            loadingStatusView = convertView
        }
        if (loadingStatusView == null) {
            loadingStatusView = DefaultLoadingStatusView(holder.context, holder.retryTask)
        }
        loadingStatusView.setStatus(status)
        return loadingStatusView
    }

}