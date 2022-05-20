package com.dzakyhdr.moviedb.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakyhdr.moviedb.data.local.auth.User
import com.dzakyhdr.moviedb.data.local.auth.UserRepository
import com.dzakyhdr.moviedb.utils.Event
import com.dzakyhdr.moviedb.utils.UserDataStoreManager
import kotlinx.coroutines.launch

class UpdateProfileViewModel(private val repository: UserRepository, private val pref: UserDataStoreManager) : ViewModel() {
    private val _saved = MutableLiveData<Event<Boolean>>()
    val saved: LiveData<Event<Boolean>> get() = _saved

    fun update(user: User
    ) {
        if (user.email.isEmpty() || user.username.isEmpty() || user.fullname.isEmpty() || user.ttl.isEmpty() || user.address.isEmpty() || user.password.isEmpty() || user.id == 0 || user.image.isEmpty()) {
            _saved.value = Event(false)
        } else {
            _saved.value = Event(true)
            viewModelScope.launch {
                repository.update(user)
            }
        }


    }

}