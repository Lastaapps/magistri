package cz.lastaapps.api.login

import cz.lastaapps.api.login.db.UserDatabase
import cz.lastaapps.api.login.entities.UserId
import cz.lastaapps.api.login.entities.UserLogin
import cz.lastaapps.api.login.entities.UserProfile
import cz.lastaapps.api.login.entities.UserTokens
import kotlinx.coroutines.flow.Flow

class UserStorageImpl(database: UserDatabase) : UserStorage {
    private val dao = database.userDao

    override suspend fun updateUserLogin(login: UserLogin) {
        dao.insert(login)
    }

    override suspend fun updateUserTokens(tokens: UserTokens) {
        dao.insert(tokens)
    }

    override suspend fun updateUserProfile(profile: UserProfile) {
        dao.insert(profile)
    }

    override fun getUserLogin(userId: UserId): Flow<UserLogin?> = dao.getUserLogin(userId)
    override fun getUserTokens(userId: UserId): Flow<UserTokens?> = dao.getUserTokens(userId)
    override fun getUserProfile(userId: UserId): Flow<UserProfile?> = dao.getUserProfile(userId)

}