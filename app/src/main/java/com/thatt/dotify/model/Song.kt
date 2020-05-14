package com.thatt.dotify.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val smallImageURL: String,
    val largeImageURL: String
): Parcelable