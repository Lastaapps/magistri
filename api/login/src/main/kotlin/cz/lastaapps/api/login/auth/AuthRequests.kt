package cz.lastaapps.api.login.auth

import cz.lastaapps.api.login.entities.*
import kotlinx.coroutines.channels.Channel

internal interface AuthRequests {
    val errors: Channel<Throwable>
    suspend fun tryLogin(url: SchoolUrl, username: Username, password: Password): LoginResponse?
    suspend fun tryRefresh(url: SchoolUrl, token: RefreshToken): LoginResponse?
}