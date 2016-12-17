package com.github.xtorrent.xtorrent

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.github.xtorrent.xtorrent.core.di.*
import com.github.xtorrent.xtorrent.db.DatabaseManager
import com.github.xtorrent.xtorrent.home.source.HomeResourcesRepositoryComponent
import com.github.xtorrent.xtorrent.home.source.HomeResourcesRepositoryModule
import com.github.xtorrent.xtorrent.search.source.SearchResourcesRepositoryComponent
import com.github.xtorrent.xtorrent.search.source.SearchResourcesRepositoryModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class XApplication : MultiDexApplication() {
    companion object {
        fun from(context: Context): XApplication {
            return context.applicationContext as XApplication
        }
    }

    var applicationComponent by Delegates.notNull<ApplicationComponent>()
    var homeResourcesRepositoryComponent by Delegates.notNull<HomeResourcesRepositoryComponent>()
    var searchResourcesRepositoryComponent by Delegates.notNull<SearchResourcesRepositoryComponent>()

    @Inject
    lateinit var databaseManager: DatabaseManager

    override fun onCreate() {
        super.onCreate()

        _setupAnalytics()
        _setupObjectGraph()

        databaseManager.open()
    }

    private fun _setupObjectGraph() {
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .dataModule(DataModule())
                .utilsModule(UtilsModule())
                .build()
        homeResourcesRepositoryComponent = applicationComponent.plus(HomeResourcesRepositoryModule())
        searchResourcesRepositoryComponent = applicationComponent.plus(SearchResourcesRepositoryModule())

        applicationComponent.inject(this)
    }

    private fun _setupAnalytics() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}