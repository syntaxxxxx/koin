package com.dzakyhdr.moviedb.di

import androidx.room.Room
import com.dzakyhdr.moviedb.data.local.auth.UserDatabase
import com.dzakyhdr.moviedb.network.ApiClient
import com.dzakyhdr.moviedb.network.ApiService
import com.dzakyhdr.moviedb.network.EndPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<UserDatabase>().userDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            "user"
        )
    }
}

val networkModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(EndPoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
//    single { Local(get()) }
//    single { Remote(get()) }
//    single<RepoInterface> { RepoImpl(get(), get(), get()) }
}