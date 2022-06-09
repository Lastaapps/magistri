package cz.lastaapps.api.login.entities

internal data class UserLogin(
    val id: UserId,
    val username: Username,
    val schoolUrl: SchoolUrl,
    val town: String?,
    val school: String?,
)