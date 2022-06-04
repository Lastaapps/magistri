package cz.lastaapps.api.login

import cz.lastaapps.api.login.entities.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface AuthRepo {

    val channel: Channel<Throwable>

    suspend fun getTowns(): List<Town>?
    suspend fun getSchoolsForTown(town: Town): List<School>?

    fun hasUsers(): Flow<Boolean>
    fun getDefaultUser(): Flow<UserId?>

    suspend fun loginUser(username: Username, password: Password): UserId?
    suspend fun refreshUser(id: UserId): UserId?
    suspend fun logoutUser(id: UserId)
}
