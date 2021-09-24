package com.example.myfirstapp.Room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(user:UserEntity)

    @Query("SELECT * FROM user_table WHERE email LIKE :email")
     fun findByEmail(email: String): List<UserEntity>

    @Query("SELECT * FROM user_table WHERE email LIKE :email")
    fun findByEmailLogin(email: String): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("DELETE FROM user_table")
     fun deleteAll()
}