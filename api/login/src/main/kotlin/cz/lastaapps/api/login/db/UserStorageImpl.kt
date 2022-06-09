package cz.lastaapps.api.login.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import cz.lastaapps.api.login.entities.*
import cz.lastaapps.menza.db.UserDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class UserStorageImpl(private val db: UserDatabase) : UserStorage {


    override suspend fun insertNew(login: UserLogin): UserId =
        db.transactionWithResult {
            with(login) {
                db.user_loginQueries.insertNew(
                    username, schoolUrl, town, school,
                )
            }
            val rowId = db.user_loginQueries.lastInsertRowId().executeAsOne()
            db.user_loginQueries.getIdForRowId(rowId).executeAsOne()
        }

    override suspend fun updateUserLogin(login: UserLogin) {
        with(login) {
            db.user_loginQueries.updateExisting(username, schoolUrl, town, school, id)
        }
    }

    override suspend fun updateUserTokens(tokens: UserTokens) {
        with(tokens) {
            db.user_tokenQueries.insertOrUpdate(id, access, refresh, expires)
        }
    }

    override suspend fun updateUserProfile(profile: UserProfile) {
        with(profile) {
            db.user_profileQueries.insertOrUpdate(id, profileName)
        }
    }

    override fun getUserLogin(userId: UserId): Flow<UserLogin?> =
        db.user_loginQueries.getUserLogin(userId) { id, username, school_url, town, school ->
            UserLogin(id, username, school_url, town, school)
        }.asFlow().mapToOneOrNull()

    override fun getUserTokens(userId: UserId): Flow<UserTokens?> =
        db.user_tokenQueries.getUserToken(userId) { id, access, refresh, expired ->
            UserTokens(id, access, refresh, expired)
        }.asFlow().mapToOneOrNull()

    override fun getUserProfile(userId: UserId): Flow<UserProfile?> =
        db.user_profileQueries.getUserProfile(userId) { id, profile_name ->
            UserProfile(id, profile_name)
        }.asFlow().mapToOneOrNull()

    override fun hasUsers(): Flow<Boolean> =
        db.generalQueries.numberOfUsers().asFlow().mapToOne().map { it > 0 }

    override fun userWithProfileName(profileName: ProfileName): Flow<UserId?> =
        db.generalQueries.getUserForProfileName(profileName).asFlow().mapToOneOrNull()
}