package com.gorkem.whatzzup.domain.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize


@Parcelize
data class Note(
    var title: String? = null,
    var content: String? = null,
    var imageUrl: String? = null,
    var userId: String? = null,
    var timeAdded: Timestamp? = null,
    var username: String? = null
) : Parcelable