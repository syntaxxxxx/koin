package com.dzakyhdr.moviedb.network

import com.dzakyhdr.moviedb.data.remote.model.popular.PopularResponse
import com.dzakyhdr.moviedb.data.remote.model.popular.Result
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET(EndPoint.Popular.urlPopular)
    suspend fun getPopular(
        @Query("api_key") api: String = TOKEN,
        @Query("page") page: Int = 1
    ): PopularResponse

    @GET(EndPoint.Detail.detail)
    suspend fun getDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") api: String = TOKEN
    ): Response<Result>

    companion object {
        private const val TOKEN = "49ee8c89a4ccc0f1da48aee2837c7c15"

    }
}

//object ApiClient {
//    private val logging = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor(logging)
//        .readTimeout(30, TimeUnit.SECONDS)
//        .build()
//
//    val instance: ApiService by lazy {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(EndPoint.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//        retrofit.create(ApiService::class.java)
//    }
//}


object EndPoint {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    object Popular {
        const val urlPopular = "movie/popular"
    }

    object Detail {
        const val detail = "movie/{movieId}"
    }
}