package com.example.socialapp.DataModel

data class ModelNewCatResponse(
    val status:String,
    val message:String,
    val data:CategoryResponse
)

data class CategoryResponse (
    val categories : Cat
)

data  class Cat (
    val id :Int,
    val catgTitle:String,
    val catgDesc:String,
    val posts : ArrayList<Any>,
    val lastModifiedDate:String
    )


