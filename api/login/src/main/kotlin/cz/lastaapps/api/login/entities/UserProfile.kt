package cz.lastaapps.api.login.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_profile",
    foreignKeys = [
        ForeignKey(
            entity = UserLogin::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
internal data class UserProfile(
    @PrimaryKey
    val id: UserId,
    @ColumnInfo(name = "profile_name")
    val profileName: ProfileName,
)