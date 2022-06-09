package cz.lastaapps.api.login.entities

import java.time.ZonedDateTime

internal data class UserTokens(
    val id: UserId,
    val access: AccessToken,
    val refresh: RefreshToken,
    val expires: ZonedDateTime,
)