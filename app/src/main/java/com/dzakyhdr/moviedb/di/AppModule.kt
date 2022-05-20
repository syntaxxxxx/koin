package com.dzakyhdr.moviedb.di

import com.dzakyhdr.moviedb.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {

}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
}