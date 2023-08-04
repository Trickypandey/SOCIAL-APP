package com.example.socialapp.DataModel

import com.google.gson.JsonElement

data class ModelAllpostresponse(
    val status:String,
    val message:String,
    val data:UserPost
):java.io.Serializable

data class UserPost(
   val posts:ArrayList<Post>
):java.io.Serializable

data class Post(
    val id:Int,
    val title:String,
    val content:String,
    val imageName:String,
    val category:Category,
    val user:User,
    val lastModifiedDate:String
):java.io.Serializable

data class Category(
    val id: Int
):java.io.Serializable

data class User(
    val id:Int,
    val name :String,
    val email : String
):java.io.Serializable

data class ResponseCategory(
    val status:String,
    val message:String,
    val data:CategoryClass
)

data class CategoryClass(
    val categories : ArrayList<ListCategory>
)
data class ListCategory(
    val id :Int,
    val catgTitle:String,
    val catgDesc:String,
    val posts : ArrayList<Any>,
    val lastModifiedDate:String
)

data class  ModelAddPOst(
    val status:String,
    val message:String,
    val data:JsonElement
)