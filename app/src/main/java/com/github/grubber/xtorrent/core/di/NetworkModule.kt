package com.github.grubber.xtorrent.core.di

import android.content.Context
import com.github.grubber.xtorrent.BuildConfig
import com.github.grubber.xtorrent.core.di.qualifier.ForApplication
import com.github.grubber.xtorrent.core.di.scope.ApplicationScope
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by grubber on 2016/12/25.
 */
@Module
class NetworkModule {
    private val OKHTTP_CLIENT_DISK_CACHE_NAME = "http-cache"
    private val OKHTTP_CLIENT_DISK_CACHE_SIZE = 20 * 1024 * 1024L

    private fun createOkHttpClient(context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(20000L, TimeUnit.MILLISECONDS)
                .writeTimeout(15000L, TimeUnit.MILLISECONDS)
                .addInterceptor(
                        HttpLoggingInterceptor().setLevel(
                                if (BuildConfig.DEBUG) {
                                    HttpLoggingInterceptor.Level.BODY
                                } else {
                                    HttpLoggingInterceptor.Level.NONE
                                }
                        )
                )
                .cache(
                        Cache(
                                File(
                                        if (BuildConfig.DEBUG) {
                                            context.externalCacheDir
                                        } else {
                                            context.cacheDir
                                        }, OKHTTP_CLIENT_DISK_CACHE_NAME
                                ),
                                OKHTTP_CLIENT_DISK_CACHE_SIZE
                        )
                )
        return builder.build()
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(@ForApplication context: Context): OkHttpClient {
        return createOkHttpClient(context)
    }

    @Provides
    @ApplicationScope
    fun providePicasso(@ForApplication context: Context, okHttpClient: OkHttpClient): Picasso {
        val picasso = Picasso.Builder(context)
                .downloader(OkHttp3Downloader(okHttpClient))
                .listener { picasso, uri, exception ->
                    Timber.e(exception, "### Failed to load image: $uri.")
                }
                .build()
        picasso.setIndicatorsEnabled(BuildConfig.DEBUG)
        return picasso
    }
}