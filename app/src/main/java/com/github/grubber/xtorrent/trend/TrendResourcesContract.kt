package com.github.grubber.xtorrent.trend

import com.github.grubber.xtorrent.base.BasePresenter
import com.github.grubber.xtorrent.base.BaseView
import com.github.grubber.xtorrent.trend.model.TrendResource

/**
 * Created by grubber on 16/12/27.
 */
interface TrendResourcesContract {
    interface View : BaseView<Presenter> {
        fun setErrorView()
        fun setContentView(resources: List<TrendResource>)
    }

    interface Presenter : BasePresenter {
        fun setType(type: String)
    }
}