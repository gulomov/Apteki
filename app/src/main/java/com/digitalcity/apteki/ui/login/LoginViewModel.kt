package com.digitalcity.apteki.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcity.apteki.network.Event
import com.digitalcity.apteki.network.Resource
import com.digitalcity.apteki.network.pojo.LoginResponse
import com.digitalcity.apteki.repository.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {
     val resourceLogin = MutableLiveData<Event<Resource<LoginResponse>>>()

    fun getLogIn(username: String, password: String) {
        viewModelScope.launch {
            repository.getLogin(username,password).onEach {
                resourceLogin.value = Event(it)
            }.launchIn(viewModelScope)
        }
    }
}