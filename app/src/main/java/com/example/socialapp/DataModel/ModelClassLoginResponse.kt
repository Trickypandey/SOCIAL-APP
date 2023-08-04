package com.example.socialapp.DataModel

data class ModelClassLoginResponse(
    val status:String,
    val message:String,
    val data : Login
):java.io.Serializable
 data class  Login (
     val login : LoginData
 )
 data class  LoginData(
     val token :String,
     val userDetails:UserDetail
 )
data class UserDetail(
    val id:Int,
    val name : String,
    val email : String,
    val about : String,
    val roles : ArrayList<RolesResponse>,
    val submittedDate : String,
    val authorities : ArrayList<Authority>
)
data class RolesResponse(
    val id: Int,
    val name : String,
)
data class  Authority(
    val authority:String
)

data class LoginRequest(val email: String, val password: String)