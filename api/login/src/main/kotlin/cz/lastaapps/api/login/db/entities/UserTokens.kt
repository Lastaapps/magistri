package cz.lastaapps.api.login.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.lastaapps.api.login.auth.entities.AccessToken
import cz.lastaapps.api.login.auth.entities.RefreshToken
import java.time.Instant

@Entity
data class UserTokens(
    @PrimaryKey
    val id: UserId,
    val refresh: RefreshToken,
    val access: AccessToken,
    val expires: Instant,
)