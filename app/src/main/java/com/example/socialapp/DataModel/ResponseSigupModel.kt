import java.time.LocalDateTime

data class UserDetails(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val roles: List<Role>,
    val about: String,
    val posts: List<Any>, // Replace 'Any' with the actual type of 'posts'
    val comments: List<Any>, // Replace 'Any' with the actual type of 'comments'
    val submittedDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime,
    val authorities: List<Authority>,
    val username: String,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean
)

data class Role(
    val id: Int,
    val name: String,
    val submittedDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime
)

data class Authority(
    val authority: String
)

data class ResponseSigupModel(
    val responseTime: LocalDateTime,
    val status: String,
    val statusCode: Int,
    val message: String,
    val method: String,
    val executionMessage: String,
    val data: UserData
)

data class UserData(
    val userDetails: UserDetails
)
