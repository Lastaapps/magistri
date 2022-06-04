package cz.lastaapps.api.login.db

import cz.lastaapps.api.login.db.entities.UserId
import cz.lastaapps.api.login.db.entities.UserLogin
import cz.lastaapps.api.login.db.entities.UserTokens
import kotlinx.coroutines.flow.Flow

interface UserStorage {

    suspend fun updateUserLogin(login: UserLogin)
    suspend fun updateUserTokens(tokens: UserTokens)

    fun getUserLogin(userId: UserId): Flow<UserLogin?>
    fun getUserTokens(userId: UserId): Flow<UserTokens?>
}