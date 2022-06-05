package cz.lastaapps.api.login.auth

import cz.lastaapps.api.login.Constants
import cz.lastaapps.api.login.entities.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.channels.Channel
import org.lighthousegames.logging.logging

internal class AuthRequestsImpl : AuthRequests {

    companion object {
        private val log = logging()
    }

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override val errors = Channel<Throwable>(Channel.BUFFERED)

    override suspend fun tryLogin(
        url: SchoolUrl,
        username: Username,
        password: Password
    ): LoginResponse? =
        try {
            log.i { "Login for user ${username.name} on ${url.url}" }

            println(
                client.post {
                    url(url.url + "/api/login")
                    contentType(ContentType.Application.FormUrlEncoded)
                    setBody("client_id=${Constants.clientId}&grant_type=password&username=${username.name}&password=${password.pass}")
                }.bodyAsText()
            )

            client.post {
                url(url.url + "/api/login")
                contentType(ContentType.Application.FormUrlEncoded)
                setBody("client_id=${Constants.clientId}&grant_type=password&username=${username.name}&password=${password.pass}")
            }.body<LoginResponse>()
        } catch (e: Exception) {
            log.e(e) { "Login failed" }
            errors.send(e)
            null
        }

    override suspend fun tryRefresh(url: SchoolUrl, token: RefreshToken): LoginResponse? =
        try {
            log.i { "Refreshing token on ${url.url}" }

            client.post {
                url(url.url + "/api/login")
                contentType(ContentType.Application.FormUrlEncoded)
                setBody("client_id=${Constants.clientId}&grant_type=refresh_token&refresh_token=${token.token}")
            }.body<LoginResponse>()
        } catch (e: Exception) {
            log.e(e) { "Token refresh failed" }
            errors.send(e)
            null
        }
}