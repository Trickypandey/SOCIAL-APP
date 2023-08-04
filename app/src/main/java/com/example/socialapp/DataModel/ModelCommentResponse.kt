package com.example.socialapp.DataModel

data class ModelCommentResponse (
    val status:String,
    val statusCode : Int,
    val message:String,
    val data :ArrayList<CommentData>
)

data class CommentData(
    val id : Int,
    val content : String,
    val user : UserDetail,
    val submittedDate : String
)