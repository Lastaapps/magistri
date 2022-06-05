package cz.lastaapps.api.login.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import cz.lastaapps.api.login.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class UserDao {

    @Transaction
    open suspend fun insertNew(userLogin: UserLogin): UserId {
        return getIdForRowId(insert(userLogin))
    }

    @Query("SELECT id FROM user_login WHERE rowId = :rowId")
    protected abstract suspend fun getIdForRowId(rowId: Long): UserId

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(userLogin: UserLogin): Long

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(userTokens: UserTokens)

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(userTokens: UserProfile)


    @Query("SELECT count(id) FROM user_login")
    abstract fun numberOfUsers(): Flow<Int>

    @Query("SELECT id FROM user_profile WHERE profile_name = :profileName")
    abstract fun findUserIdForProfileName(profileName: ProfileName): Flow<UserId?>


    @Query("SELECT * FROM user_login WHERE id = :userId")
    abstract fun getUserLogin(userId: UserId): Flow<UserLogin?>

    @Query("SELECT * FROM user_tokens WHERE id = :userId")
    abstract fun getUserTokens(userId: UserId): Flow<UserTokens?>

    @Query("SELECT * FROM user_profile WHERE id = :userId")
    abstract fun getUserProfile(userId: UserId): Flow<UserProfile?>

    @Query("DELETE FROM user_login WHERE id = :userId")
    protected abstract suspend fun deleteUser(userId: UserId)
}