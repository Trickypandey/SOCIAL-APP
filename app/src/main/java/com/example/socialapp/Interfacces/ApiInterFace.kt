package com.example.socialapp.Interfacces

import ResponseSigupModel
import com.example.socialapp.DataModel.*
import com.google.gson.JsonElement
import retrofit2.http.*

interface ApiInterFace {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): retrofit2.Call<ModelClassLoginResponse>


    @GET("posts/get")
    fun getAllPost(@Header("Authorization") auth :String):retrofit2.Call<ModelAllpostresponse>


    @POST("posts/add")
    fun addPost(@Body map: HashMap<String,String>,@Header("Authorization") auth :String,@Query("catTitle") cat:String,@Query("userId") userId : Int):retrofit2.Call<ModelAllpostresponse>


    @GET("posts/getPostByKeyword")
    fun searchPost(@Query("keyword") keyword: String,@Header("Authorization") auth :String):retrofit2.Call<ModelAllpostresponse>

    @GET("categories/get")
    fun getcategories(@Header("Authorization") auth :String):retrofit2.Call<ResponseCategory>

    @POST("auth/signUp")
    fun  addUser(@Body map: HashMap<String,String>): retrofit2.Call<ResponseSigupModel>

    @GET("posts/getPostByUser/{id}")
    fun getPostByUser(@Header("Authorization") auth :String,@Path("id") id:Int):retrofit2.Call<ModelAllpostresponse>

    @GET("posts/getPostByCategory/{id}")
    fun getPostbyCategory(@Header("Authorization") auth :String,@Path("id") id:Int):retrofit2.Call<ModelAllpostresponse>

    @POST("categories/add")
    fun addNewCat(@Header("Authorization") auth :String,@Body map: HashMap<String,String>): retrofit2.Call<ModelNewCatResponse>

    @GET("comments/getByPostId/{id}")
    fun getComment(@Header("Authorization") auth :String,@Path("id") id: Int): retrofit2.Call<CommentResponse>

    @POST("comments/add")
    fun addComment(@Header("Authorization") auth :String,@Query("userId") uid:Int,@Query("postId") postId:Int, @Body requestBody: HashMap<String, String>): retrofit2.Call<CommentResponse>

    @DELETE("posts/delete/{id}")
    fun deletePost(@Header("Authorization") auth :String,@Path("id") id: Int): retrofit2.Call<ModelDeletePostResponse>
}