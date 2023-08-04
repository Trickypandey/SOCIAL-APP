package com.example.socialapp.utils

import UserDetails
import com.example.socialapp.DataModel.UserDetail
import java.text.SimpleDateFormat
import java.util.*

class Common {
    fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM, yy", Locale.getDefault())

        val date = inputFormat.parse(dateString)
        return outputFormat.format(date ?: Date())
    }
    fun isUserAdmin(userDetails: UserDetail): Boolean {
        return userDetails?.roles?.any { it.name == "ROLE_ADMIN" } ?: false
    }
}