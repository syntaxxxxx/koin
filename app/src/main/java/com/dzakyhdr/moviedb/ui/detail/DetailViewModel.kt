package com.dzakyhdr.moviedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakyhdr.moviedb.data.local.favorite.MovieEntity
import com.dzakyhdr.moviedb.data.remote.ErrorMovie
import com.dzakyhdr.moviedb.data.remote.MovieRepository
import com.dzakyhdr.moviedb.data.remote.model.popular.Result
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {

    private var _detail = MutableLiveData<Result?>()
    val detail: LiveData<Result?> get() = _detail

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _errorStatus = MutableLiveData<String?>()
    val errorStatus: LiveData<String?> get() = _errorStatus

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite get() = _isFavorite

    private val listFavoriteUsers: MutableLiveData<List<MovieEntity>?> = MutableLiveData()

    init {
        getFavoriteUsers()
        _isFavorite.postValue(false)
    }

    fun getDetail(id: Int) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _detail.value = repository.getDetail(id)
            } catch (error: ErrorMovie) {
                _errorStatus.value = error.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun onSnackbarShown() {
        _errorStatus.value = null
    }


    fun showUserIsFavorite(favoriteMovie: MovieEntity) {
        viewModelScope.launch {
            for (it in listFavoriteUsers.value ?: mutableListOf()) {
                if (favoriteMovie.id == it.id) {
                    _isFavorite.postValue(true)
                    break
                } else {
                    _isFavorite.postValue(false)
                }
            }
        }
    }

    fun checkFavoriteUser(favoriteMovie: MovieEntity) {
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                delete(favoriteMovie)
            } else {
                insert(favoriteMovie)
            }
        }
    }

    private fun insert(favoriteUser: MovieEntity) {
        viewModelScope.launch {
            favoriteUser.let {
                repository.insert(favoriteUser)
                getFavoriteUsers()
                _isFavorite.postValue(true)
            }
        }
    }

    private fun delete(favoriteUser: MovieEntity?) {
        viewModelScope.launch {
            if (favoriteUser != null) {
                repository.delete(favoriteUser)
                getFavoriteUsers()
                _isFavorite.postValue(false)
            }
        }
    }

    private fun getFavoriteUsers() {
        viewModelScope.launch {
            listFavoriteUsers.postValue(repository.getFavoriteUser())
        }
    }


}