package com.gorkem.whatzzup.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
        val username: String = "username",
        val userId : String = "userId"
):Parcelable
