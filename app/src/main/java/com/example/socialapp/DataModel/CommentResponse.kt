package com.example.socialapp.DataModel

data class CommentResponse(
    val `data`: Data,
    val executionMessage: String,
    val message: String,
    val method: String,
    val responseTime: String,
    val status: String,
    val statusCode: Int
)