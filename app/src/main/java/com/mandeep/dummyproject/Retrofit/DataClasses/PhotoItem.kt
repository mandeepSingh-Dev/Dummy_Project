package com.mandeep.dummyproject.Retrofit.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PhotoItem(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
):Parcelable