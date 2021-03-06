package com.github.grubber.xtorrent.search

import com.github.grubber.xtorrent.search.model.Resource
import com.github.grubber.xtorrent.search.model.ResourceItem
import com.github.grubber.xtorrent.search.source.SearchResourcesRepository
import com.github.grubber.xtorrent.utils.applySchedulers
import com.github.grubber.xtorrent.utils.bind
import com.github.grubber.xtorrent.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by grubber on 16/11/29.
 */
class SearchResourcesPresenter @Inject constructor(private val repository: SearchResourcesRepository,
                                                   private val view: SearchResourcesContract.View) : SearchResourcesContract.Presenter {
    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private val _binder by lazy {
        CompositeSubscription()
    }

    private var _keyword by Delegates.notNull<String>()
    private var _pageNumber = 1

    private val _data by lazy {
        arrayListOf<Pair<Resource, List<ResourceItem>>>()
    }

    override fun setKeyword(keyword: String) {
        _keyword = keyword
    }

    override fun setPageNumber(pageNumber: Int) {
        _pageNumber = pageNumber
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getSearchResources(_keyword, _pageNumber)
                .applySchedulers()
                .bind {
                    next {
                        if (it != null) {
                            _data.addAll(it)
                            if (_pageNumber >= 10) {
                                view.setContentView(arrayListOf())
                            } else {
                                view.setContentView(it)
                            }
                        }
                    }

                    error {
                        Timber.d("### error is ${it?.message}")
                        if (_data.isEmpty()) {
                            view.setErrorView()
                        } else {
                            view.setContentView(null, true)
                        }
                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}