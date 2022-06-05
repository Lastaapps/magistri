package cz.lastaapps.api.login.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LoginResponse(
    @SerialName("bak:ApiVersion")
    val apiVersion: String,
    @SerialName("bak:AppVersion")
    val appVersion: String,
    @SerialName("bak:UserId")
    val userId: String,
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("scope")
    val scope: String,
)