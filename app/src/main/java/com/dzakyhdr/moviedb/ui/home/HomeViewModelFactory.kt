package com.dzakyhdr.moviedb.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyhdr.moviedb.data.local.auth.UserRepository
import com.dzakyhdr.moviedb.data.remote.MovieRepository
import com.dzakyhdr.moviedb.di.Injection
import com.dzakyhdr.moviedb.utils.UserDataStoreManager

class HomeViewModelFactory(
    private val repository: MovieRepository,
    private val userRepository: UserRepository?,
    private val pref: UserDataStoreManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository, userRepository!!, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var instance: HomeViewModelFactory? = null
        fun getInstance(
            context: Context,
            pref: UserDataStoreManager
        ): HomeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(
                    Injection.provideRepositoryMovie(context),
                    Injection.provideRepositoryUser(context),
                    pref
                )
            }.also { instance = it }
    }
}