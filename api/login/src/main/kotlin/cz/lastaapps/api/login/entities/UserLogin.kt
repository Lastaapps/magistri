package cz.lastaapps.api.login.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_login")
data class UserLogin(
    @PrimaryKey(autoGenerate = true)
    val id: UserId,
    val username: Username,
    val password: Password,
    @ColumnInfo(name = "school_url")
    val schoolUrl: SchoolUrl,
    val town: String?,
    val school: String?,
)