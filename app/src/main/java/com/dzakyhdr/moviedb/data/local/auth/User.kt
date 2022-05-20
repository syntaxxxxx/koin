package com.dzakyhdr.moviedb.data.local.auth

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "email")
    var email: String = "",
    @ColumnInfo(name = "username")
    var username: String = "",
    @ColumnInfo(name = "fullname")
    var fullname: String = "",
    @ColumnInfo(name = "ttl")
    var ttl: String = "",
    @ColumnInfo(name = "address")
    var address: String = "",
    @ColumnInfo(name = "password")
    var password: String = "",
    @ColumnInfo(name = "image")
    var image: String = ""
) : Parcelable
