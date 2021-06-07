package com.tryzens.task.rest.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Permissions(
    @SerializedName("admin") val admin: Boolean,
    @SerializedName("push") val push: Boolean,
    @SerializedName("pull") val pull: Boolean
): Parcelable
