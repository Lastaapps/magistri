package cz.lastaapps.api.login.db

import cz.lastaapps.api.login.entities.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class UserStorageImpl(database: UserDatabase) : UserStorage {
    private val dao = database.userDao

    override suspend fun insertNew(login: UserLogin) =
        dao.insertNew(login)

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

    override fun hasUsers(): Flow<Boolean> = dao.numberOfUsers().map { it > 0 }
    override fun userWithProfileName(profileName: ProfileName): Flow<UserId?> =
        dao.findUserIdForProfileName(profileName)
}