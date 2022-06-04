package cz.lastaapps.api.login

import cz.lastaapps.api.login.auth.entities.Password
import cz.lastaapps.api.login.auth.entities.Username
import cz.lastaapps.api.login.db.entities.UserId
import cz.lastaapps.api.login.school.entities.School
import cz.lastaapps.api.login.school.entities.Town
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
