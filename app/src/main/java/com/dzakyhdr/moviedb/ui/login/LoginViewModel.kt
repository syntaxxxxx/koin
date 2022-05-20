package com.dzakyhdr.moviedb.ui.login

import androidx.lifecycle.*
import com.dzakyhdr.moviedb.data.local.auth.User
import com.dzakyhdr.moviedb.data.local.auth.UserRepository
import com.dzakyhdr.moviedb.resource.Resource
import com.dzakyhdr.moviedb.utils.UserDataStoreManager
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

    private var _loginStatus = MutableLiveData<Resource<User>>()
    val loginStatus: LiveData<Resource<User>> get() = _loginStatus


    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginStatus.postValue(Resource.loading(null))
            try {
                val data = repository.verifyLogin(email, password)
                _loginStatus.postValue(Resource.success(data, 0))
            } catch (exception: Exception) {
                _loginStatus.postValue(Resource.error(null, exception.message!!))
            }
        }
    }

    fun saveUserDataStore(status: Boolean, id: Int) {
        viewModelScope.launch {
            pref.saveUser(status, id)
        }
    }


    fun getStatus(): LiveData<Boolean>{
        return pref.getStatus().asLiveData()
    }

}