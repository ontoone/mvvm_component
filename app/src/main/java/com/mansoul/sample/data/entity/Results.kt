package com.mansoul.sample.data.entity

import com.google.gson.annotations.SerializedName

data class Results(
    val Android: List<Android>,
    val App: List<App>,
    val iOS: List<IOS>,
    @SerializedName("休息视频")
    val videos: List<Video>,
    @SerializedName("福利")
    val gankImages: List<GankImage>
)