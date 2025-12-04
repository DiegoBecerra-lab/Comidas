package com.example.comidas.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()

    val allUsers: StateFlow<List<User>> = userDao.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getUserByName(name: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            onResult(userDao.getUserByName(name))
        }
    }

    fun registerUser(user: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (userDao.getUserByName(user.name) == null) {
                userDao.insertUser(user)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun clearAllUsers() {
        viewModelScope.launch {
            userDao.deleteAllUsers()
        }
    }
}