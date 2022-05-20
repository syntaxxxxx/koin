package com.dzakyhdr.moviedb.data.local.auth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dzakyhdr.moviedb.data.local.favorite.MovieDao
import com.dzakyhdr.moviedb.data.local.favorite.MovieEntity

@Database(entities = [User::class, MovieEntity::class], version = 3, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
}