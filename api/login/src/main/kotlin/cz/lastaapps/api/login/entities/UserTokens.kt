package cz.lastaapps.api.login.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "user_tokens",
    foreignKeys = [
        ForeignKey(
            entity = UserLogin::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ]
)
data class UserTokens(
    @PrimaryKey
    val id: UserId,
    val refresh: RefreshToken,
    val access: AccessToken,
    val expires: Instant,
)