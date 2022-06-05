package cz.lastaapps.api.login.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.lastaapps.api.login.entities.UserLogin
import cz.lastaapps.api.login.entities.UserProfile
import cz.lastaapps.api.login.entities.UserTokens

@Database(
    entities = [
        UserTokens::class, UserLogin::class, UserProfile::class
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Convertor::class)
internal abstract class UserDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        fun createDatabase(context: Context): UserDatabase =
            Room.databaseBuilder(
                context, UserDatabase::class.java, "user_database"
            ).build()
    }
}