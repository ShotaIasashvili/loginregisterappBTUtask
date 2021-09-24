package com.example.myfirstapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.Repository.UserRepository
import com.example.myfirstapp.Room.UserEntity
import com.example.myfirstapp.Room.UserRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    // - Repository is completely separated from the UI through the ViewModel.
    lateinit var name:String
    lateinit var email:String
    lateinit var gender:String
    lateinit var city:String
    lateinit var particularUser:List<UserEntity>

    init {
        val userDao = UserRoomDatabase.getDatabase(application,viewModelScope).userDao()
        repository = UserRepository(userDao)
    }
    fun findByEmail(emailParent:String) = viewModelScope.launch(Dispatchers.IO) {
        particularUser = repository.findByEmail(emailParent)
        name = "NAME :" + particularUser[0].name
        email = "EMAIL :" + particularUser[0].email
        city = "age :" + particularUser[0].age
        gender = "gender :" +particularUser[0].gender

    }
}


