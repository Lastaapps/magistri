package cz.lastaapps.api.login

import io.ktor.client.*
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun getClient(): Flow<HttpClient>
}