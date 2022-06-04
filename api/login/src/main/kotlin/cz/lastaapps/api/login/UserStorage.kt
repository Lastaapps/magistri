package cz.lastaapps.api.login

import cz.lastaapps.api.login.entities.UserId
import cz.lastaapps.api.login.entities.UserLogin
import cz.lastaapps.api.login.entities.UserProfile
import cz.lastaapps.api.login.entities.UserTokens
import kotlinx.coroutines.flow.Flow

interface UserStorage {

    suspend fun updateUserLogin(login: UserLogin)
    suspend fun updateUserTokens(tokens: UserTokens)
    suspend fun updateUserProfile(profile: UserProfile)

    fun getUserLogin(userId: UserId): Flow<UserLogin?>
    fun getUserTokens(userId: UserId): Flow<UserTokens?>
    fun getUserProfile(userId: UserId): Flow<UserProfile?>
}