package com.xxs.eat.dagger2.module

import com.xxs.eat.presenter.HomeFragmentPresenter
import com.xxs.eat.ui.fragment.HomeFragment
import dagger.Module
import dagger.Provides

@Module class HomeFragmentModule(val homeFragment: HomeFragment) {

    @Provides fun provideHomeFragmentPresenter(): HomeFragmentPresenter {
        return HomeFragmentPresenter(homeFragment)
    }


}