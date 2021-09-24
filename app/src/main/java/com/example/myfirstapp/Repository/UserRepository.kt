package com.example.myfirstapp.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.myfirstapp.Room.UserDao
import com.example.myfirstapp.Room.UserEntity

class UserRepository(private val userDao: UserDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    //lateinit var  userInfo: User
    lateinit var particularUser:List<UserEntity>
    var userinfo: LiveData<List<UserEntity>> = userDao.getAllUsers()
     fun insert(user: UserEntity) {
        userDao.insert(user)
    }
     fun findByEmail(email:String):List<UserEntity> {
          return userDao.findByEmail(email)
    }
    fun findByEmailLogin(email:String):LiveData<List<UserEntity>> {
        return userDao.findByEmailLogin(email)
    }
}