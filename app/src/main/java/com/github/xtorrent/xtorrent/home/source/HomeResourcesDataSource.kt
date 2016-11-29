package com.github.xtorrent.xtorrent.home.source

import com.github.xtorrent.xtorrent.home.model.HomeResource
import rx.Observable

/**
 * Created by zhihao.zeng on 16/11/29.
 */
interface HomeResourcesDataSource {
    fun getHomeResources(type: HomeResource.Type): Observable<List<HomeResource>>
    fun getHomeResource(url: String): Observable<HomeResource>

    fun saveHomeResource(resource: HomeResource)
}