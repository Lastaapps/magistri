package cz.lastaapps.api.login.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import cz.lastaapps.menza.db.UserDatabase
import login.User_login
import login.User_profile
import login.User_token

internal class UserDatabaseDriver(val sqlDriver: SqlDriver) {
    companion object {
        fun createDefault(context: Context): UserDatabaseDriver {
            return UserDatabaseDriver(
                AndroidSqliteDriver(
                    UserDatabase.Schema, context, "user_database.db"
                )
            )
        }
    }
}

internal fun createUserDatabase(driver: UserDatabaseDriver): UserDatabase =
    UserDatabase.invoke(
        driver.sqlDriver,
        User_login.Adapter(
            idAdapter = Adapters.idAdapter,
            usernameAdapter = Adapters.userAdapter,
            school_urlAdapter = Adapters.schoolUrlAdapter,
        ),
        User_profile.Adapter(
            idAdapter = Adapters.idAdapter,
            profile_nameAdapter = Adapters.profileAdapter,
        ),
        User_token.Adapter(
            idAdapter = Adapters.idAdapter,
            accessAdapter = Adapters.accessTokenAdapter,
            refreshAdapter = Adapters.refreshTokenAdapter,
            expiredAdapter = Adapters.zoneDateTimeAdapter,
        ),
    )
