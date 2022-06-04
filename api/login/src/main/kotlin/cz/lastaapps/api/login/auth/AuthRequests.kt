package cz.lastaapps.api.login.auth

import cz.lastaapps.api.login.auth.entities.LoginResponse
import cz.lastaapps.api.login.auth.entities.Password
import cz.lastaapps.api.login.auth.entities.RefreshToken
import cz.lastaapps.api.login.auth.entities.Username
import kotlinx.coroutines.channels.Channel

interface AuthRequests {
    val channel: Channel<Throwable>
    suspend fun tryLogin(username: Username, password: Password): LoginResponse?
    suspend fun tryRefresh(token: RefreshToken): LoginResponse?
}