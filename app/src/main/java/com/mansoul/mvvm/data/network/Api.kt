package com.mansoul.mvvm.data.network

import com.mansoul.mvvm.data.entity.GankToday
import com.mansoul.mvvm.data.entity.Image
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * @author Mansoul
 * @create 2019/3/19 23:28
 * @des
 */
interface Api {

    //分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
    //http://gank.io/api/data/福利/10/1

    /**
     * 获取最新一天的干货
     */
    @GET("today")
    fun gankToday(): Deferred<GankToday>

    /**
     * 获取最新一天的干货
     */
    @GET("data/福利/10/1")
    fun getImages(): Deferred<Image>
}