package cz.lastaapps.api.login

import cz.lastaapps.api.login.entities.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface AuthRepo {

    val errors: Channel<Throwable>

    fun hasUsers(): Flow<Boolean>
    fun getDefaultUser(): Flow<UserId?>

    suspend fun loginUser(
        username: Username, password: Password,
        profileName: ProfileName, schoolUrl: SchoolUrl,
        town: String?, school: String?,
    ): UserId?

    suspend fun logoutUser(id: UserId)

    suspend fun refreshUser(id: UserId): UserId?
    suspend fun updatePassword(id: UserId, password: Password): UserId?
    suspend fun updateProfile(id: UserId, profileName: ProfileName)
}
