package com.example.myfirstapp.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")

class UserEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = -1,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "email") val email :String,

    @ColumnInfo(name = "password") val password :String,

    @ColumnInfo(name = "city") val age :String,

    @ColumnInfo(name = "gender") val gender :String

)
