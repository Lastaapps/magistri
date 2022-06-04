package cz.lastaapps.api.login.auth

import cz.lastaapps.api.login.Constants
import cz.lastaapps.api.login.entities.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.channels.Channel
import org.lighthousegames.logging.logging

class AuthRequestsImpl(private val schoolUrl: SchoolUrl) : AuthRequests {

    companion object {
        private val log = logging()
    }

    private val client = HttpClient(CIO)

    override val channel = Channel<Throwable>(Channel.BUFFERED)

    override suspend fun tryLogin(username: Username, password: Password): LoginResponse? =
        try {
            log.i { "Login for user ${username.name} on ${schoolUrl.url}" }

            client.post {
                url(schoolUrl.url + "/api/login")
                contentType(ContentType.Application.FormUrlEncoded)
                setBody("client_id=${Constants.clientId}&grant_type=password&username=${username.name}&password=${password.pass}")
            }.body<LoginResponse>()
        } catch (e: Exception) {
            log.e(e) { "Login failed" }
            channel.send(e)
            null
        }

    override suspend fun tryRefresh(token: RefreshToken): LoginResponse? =
        try {
            log.i { "Refreshing token on ${schoolUrl.url}" }

            client.post {
                url(schoolUrl.url + "/api/login")
                contentType(ContentType.Application.FormUrlEncoded)
                setBody("client_id=${Constants.clientId}&grant_type=refresh_token&refresh_token=${token.token}")
            }.body<LoginResponse>()
        } catch (e: Exception) {
            log.e(e) { "Token refresh failed" }
            channel.send(e)
            null
        }
}