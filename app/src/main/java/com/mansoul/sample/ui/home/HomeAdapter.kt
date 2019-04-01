package com.mansoul.sample.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mansoul.mvvm.imageloader.ImageLoaderHelper
import com.mansoul.sample.R
import com.mansoul.sample.data.entity.Android
import me.drakeet.multitype.ItemViewBinder

/**
 * @author Mansoul
 * @create 2019/3/27 19:22
 * @des
 */
class HomeAdapter : ItemViewBinder<Android, HomeAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onBindViewHolder(holder: ViewHolder, item: Android) {
        holder.title.text = item.desc
        ImageLoaderHelper.with(context).loadFromUrl(holder.iv, if (item.images.isNotEmpty()) item.images[0] else "")
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        context = parent.context
        return ViewHolder(inflater.inflate(R.layout.item_home, parent, false))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.title)
        var iv: ImageView = itemView.findViewById(R.id.iv)
    }
}