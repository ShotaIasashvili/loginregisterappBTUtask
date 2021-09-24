package com.example.myfirstapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.Repository.UserRepository
import com.example.myfirstapp.Room.UserEntity
import com.example.myfirstapp.Room.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class UserViewModelLogin(application: Application) :AndroidViewModel(application) {
    var inputEmail = MutableLiveData<String>()
    var inputPassword = MutableLiveData<String>()
    private val repository: UserRepository
    lateinit var usersList:List<UserEntity>
    var userInfo: LiveData<List<UserEntity>>
    var checkUser = MutableLiveData<Boolean>();

    init {
        val userDao = UserRoomDatabase.getDatabase(application,viewModelScope).userDao()
        repository = UserRepository(userDao)
        userInfo = repository.userinfo
    }
    fun findByEmail() = viewModelScope.launch(Dispatchers.IO) {
        usersList = repository.findByEmail(inputEmail.value!!)
        if(usersList.isNotEmpty()) {
            if(usersList[0].email == inputEmail.value!! && usersList[0].password == inputPassword.value!!) {
                checkUser.postValue(true)
            }
            else {
                checkUser.postValue(false)
            }
        }
        else {
            checkUser.postValue(false)
        }
    }
}