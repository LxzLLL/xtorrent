package com.github.grubber.xtorrent.home

import com.github.grubber.xtorrent.core.di.scope.FragmentScope
import dagger.Subcomponent

/**
 * Created by grubber on 16/11/29.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(HomeResourcesPresenterModule::class))
interface HomeResourcesPresenterComponent {
    fun inject(homeFragment: HomeFragment)
}