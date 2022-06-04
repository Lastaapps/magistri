package cz.lastaapps.api.login.db

import androidx.room.TypeConverter
import cz.lastaapps.api.login.entities.*
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Convertor {
    @TypeConverter
    fun userIdFrom(from: UserId): Long {
        return from.id
    }

    @TypeConverter
    fun userIdTo(to: Long): UserId {
        return UserId(to)
    }

    @TypeConverter
    fun usernameFrom(from: Username) = from.name

    @TypeConverter
    fun usernameTo(to: String) = Username(to)

    @TypeConverter
    fun passwordFrom(from: Password) = from.pass

    @TypeConverter
    fun passwordTo(to: String) = Password(to)

    @TypeConverter
    fun schoolUrlFrom(from: SchoolUrl) = from.raw

    @TypeConverter
    fun schoolUrlTo(to: String) = SchoolUrl(to)

    @TypeConverter
    fun accessFrom(from: AccessToken) = from.token

    @TypeConverter
    fun accessTo(to: String) = AccessToken(to)

    @TypeConverter
    fun refreshFrom(from: RefreshToken) = from.token

    @TypeConverter
    fun refreshTo(to: String) = RefreshToken(to)

    @TypeConverter
    fun instantFrom(from: Instant) = ZonedDateTime.ofInstant(from, ZoneId.of("UTC"))
        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)

    @TypeConverter
    fun instantTo(to: String) =
        ZonedDateTime.parse(to, DateTimeFormatter.ISO_ZONED_DATE_TIME).toInstant()

//    @TypeConverter
//    fun From(from: ) = from.
//    @TypeConverter
//    fun To(to: ) = (to)
}