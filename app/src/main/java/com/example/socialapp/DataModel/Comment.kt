package com.example.socialapp.DataModel

import android.os.Parcel
import android.os.Parcelable

data class Comment(
    val content: String,
    val id: Int,
    val lastModifiedDate: String,
    val post: Post,
    val submittedDate: String,
    val user: UserX
): Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }
}