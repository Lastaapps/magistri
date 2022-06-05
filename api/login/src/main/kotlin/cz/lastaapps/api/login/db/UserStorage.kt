package cz.lastaapps.api.login.db

import cz.lastaapps.api.login.entities.*
import kotlinx.coroutines.flow.Flow

internal interface UserStorage {

    suspend fun insertNew(login: UserLogin): UserId
    suspend fun updateUserLogin(login: UserLogin)
    suspend fun updateUserTokens(tokens: UserTokens)
    suspend fun updateUserProfile(profile: UserProfile)

    fun getUserLogin(userId: UserId): Flow<UserLogin?>
    fun getUserTokens(userId: UserId): Flow<UserTokens?>
    fun getUserProfile(userId: UserId): Flow<UserProfile?>

    fun hasUsers(): Flow<Boolean>
    fun userWithProfileName(profileName: ProfileName): Flow<UserId?>
}