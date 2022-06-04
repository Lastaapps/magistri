package cz.lastaapps.api.login

import cz.lastaapps.api.login.entities.AccessToken
import cz.lastaapps.api.login.entities.RefreshToken
import cz.lastaapps.api.login.entities.SchoolUrl
import cz.lastaapps.api.login.entities.UserId
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserRepoImpl(
    private val id: UserId,
    private val storage: UserStorage,
    private val tokenRefresh: AuthRepo,
) : UserRepo {

    private val client by lazy {
        schoolUrl().map { url ->
            HttpClient(CIO) {
                install(DefaultRequest) {
                    url(url.api)
                }
                install(Auth) {
                    bearer {
                        loadTokens {
                            with(storage.getUserTokens(id).first()!!) {
                                BearerTokens(access, refresh)
                            }
                        }
                        refreshTokens {
                            tokenRefresh.refreshUser(id)!!
                            with(storage.getUserTokens(id).first()!!) {
                                BearerTokens(access, refresh)
                            }
                        }
                    }
                }
            }
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun BearerTokens(access: AccessToken, refresh: RefreshToken) =
        BearerTokens(access.token, refresh.token)

    override suspend fun getClient(): Flow<HttpClient> = client

    private fun schoolUrl(): Flow<SchoolUrl> {
        return storage.getUserLogin(id).map { it!!.schoolUrl }
    }
}