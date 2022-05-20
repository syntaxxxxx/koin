//package com.dzakyhdr.moviedb.di
//
//import android.content.Context
//import com.dzakyhdr.moviedb.data.local.MovieLocalDataSource
//import com.dzakyhdr.moviedb.data.local.auth.UserDatabase
//import com.dzakyhdr.moviedb.data.local.auth.UserRepository
//import com.dzakyhdr.moviedb.data.remote.MovieRemoteDataSource
//import com.dzakyhdr.moviedb.data.remote.MovieRepository
//
//object Injection {
//
//    fun provideRepositoryUser(context: Context): UserRepository? {
//        return UserRepository.getInstance(context)
//    }
//
//    fun provideRepositoryMovie(context: Context): MovieRepository {
//        val database = UserDatabase.getInstance(context)
//        val dao = database.movieDao()
//        val remoteDataSource = MovieRemoteDataSource()
//        val localDataSource = MovieLocalDataSource(dao)
//        return MovieRepository.getInstance(remoteDataSource, localDataSource)
//    }
//}