package com.github.xtorrent.xtorrent.search.source.remote

import com.github.xtorrent.xtorrent.search.model.Resource
import com.github.xtorrent.xtorrent.search.model.ResourceItem
import com.github.xtorrent.xtorrent.search.source.SearchResourcesDataSource
import rx.Observable
import rx.lang.kotlin.emptyObservable

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class SearchResourcesRemoteDataSource() : SearchResourcesDataSource {
    override fun getSearchResources(): Observable<List<Pair<Resource, List<ResourceItem>>>> {
        // TODO
        return emptyObservable()
    }

    override fun getSearchResource(url: String): Observable<Pair<Resource, List<ResourceItem>>> {
        // TODO
        return emptyObservable()
    }

    override fun saveSearchResource(resource: Resource) {
        // Ignored.
    }

    override fun updateSearchResource(resource: Resource) {
        // Ignored.
    }
}