package com.mansoul.mvvm

import com.mansoul.common.base.BaseApp
import com.mansoul.common.http.buildHttpClient
import com.mansoul.mvvm.data.db.MyDatabase
import com.mansoul.mvvm.data.network.Api
import com.mansoul.mvvm.data.network.ImageNetworkSource
import com.mansoul.mvvm.data.repository.ImageRepo
import com.mansoul.mvvm.ui.image.ImageVMFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * @author Mansoul
 * @create 2019/3/25 15:06
 * @des
 */
class App : BaseApp(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))
        import(db)
        import(network)
        bind() from singleton { ImageRepo(instance(), instance()) }
        bind() from provider { ImageVMFactory(instance()) }
    }

    private val db = Kodein.Module {
        bind() from singleton { MyDatabase.getDatabase(this@App) }
        bind() from singleton { instance<MyDatabase>().imageDao() }
    }

    private val network = Kodein.Module {
        bind() from singleton {
            val httpClient = buildHttpClient {
                context = instance
                baseUrl = "http://gank.io/api/"
                logEnabled = true
            }
            httpClient.init()
            return@singleton httpClient
        }

        bind() from singleton { Api(instance()) }
        bind() from singleton { ImageNetworkSource(instance()) }
    }

}