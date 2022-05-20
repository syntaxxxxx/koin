package com.dzakyhdr.moviedb.data.remote

import androidx.lifecycle.LiveData
import com.dzakyhdr.moviedb.data.local.MovieLocalDataSource
import com.dzakyhdr.moviedb.data.local.favorite.MovieEntity
import com.dzakyhdr.moviedb.data.remote.model.popular.Result

class MovieRepository(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) {

    suspend fun getPopularMovie(): List<Result>? {
        return movieRemoteDataSource.getMovies()
    }

    suspend fun getDetail(id: Int): Result {
        return movieRemoteDataSource.getDetail(id)
    }

    suspend fun insert(movie: MovieEntity) = localDataSource.insert(movie)

    suspend fun delete(movie: MovieEntity) = localDataSource.delete(movie)

    suspend fun getFavoriteUser(): List<MovieEntity> = localDataSource.getFavoriteMovie()

    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(
            remoteDataSource: MovieRemoteDataSource,
            localDataSource: MovieLocalDataSource
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localDataSource)
            }.also { instance = it }
    }
}