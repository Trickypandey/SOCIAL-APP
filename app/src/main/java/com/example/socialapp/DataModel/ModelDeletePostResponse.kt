package com.example.socialapp.DataModel

import com.google.gson.JsonElement

data class ModelDeletePostResponse(
    val status:String,
    val statusCode: Int,
    val message:String,
    val data : JsonElement,
)
