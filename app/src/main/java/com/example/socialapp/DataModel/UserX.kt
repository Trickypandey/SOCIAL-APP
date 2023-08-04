package com.example.socialapp.DataModel

data class UserX(
    val about: String,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val authorities: List<Authority>,
    val comments: List<Any>,
    val credentialsNonExpired: Boolean,
    val email: String,
    val enabled: Boolean,
    val id: Int,
    val lastModifiedDate: String,
    val name: String,
    val password: String,
    val posts: List<Any>,
    val roles: List<Role>,
    val submittedDate: String,
    val username: String
)