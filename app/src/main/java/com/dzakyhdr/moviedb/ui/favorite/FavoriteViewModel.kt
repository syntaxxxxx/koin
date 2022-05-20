package com.dzakyhdr.moviedb.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakyhdr.moviedb.data.local.favorite.MovieEntity
import com.dzakyhdr.moviedb.data.remote.MovieRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: MovieRepository) : ViewModel() {

    private var _getListFavorite = MutableLiveData<List<MovieEntity>>()
    val getListFavorite: LiveData<List<MovieEntity>> get() = _getListFavorite


    fun getUser() {
        viewModelScope.launch {
            _getListFavorite.postValue(repository.getFavoriteUser())
        }
    }

}