package cz.lastaapps.api.login.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.lastaapps.api.login.auth.entities.Password
import cz.lastaapps.api.login.auth.entities.SchoolUrl
import cz.lastaapps.api.login.auth.entities.Username

@Entity
data class UserLogin(
    @PrimaryKey
    val id: UserId,
    val username: Username,
    val password: Password,
    val schoolUrl: SchoolUrl,
    val town: String?,
    val school: String?,
)