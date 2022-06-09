package cz.lastaapps.api.login.db

import com.squareup.sqldelight.ColumnAdapter
import cz.lastaapps.api.login.entities.*
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

internal object Adapters {
    val idAdapter = object : ColumnAdapter<UserId, Long> {
        override fun decode(databaseValue: Long): UserId = UserId(databaseValue)
        override fun encode(value: UserId): Long = value.id
    }
    val userAdapter = object : ColumnAdapter<Username, String> {
        override fun decode(databaseValue: String): Username = Username(databaseValue)
        override fun encode(value: Username): String = value.name
    }
    val schoolUrlAdapter = object : ColumnAdapter<SchoolUrl, String> {
        override fun decode(databaseValue: String): SchoolUrl = SchoolUrl(databaseValue)
        override fun encode(value: SchoolUrl): String = value.raw
    }
    val profileAdapter = object : ColumnAdapter<ProfileName, String> {
        override fun decode(databaseValue: String): ProfileName = ProfileName(databaseValue)
        override fun encode(value: ProfileName): String = value.name
    }
    val accessTokenAdapter = object : ColumnAdapter<AccessToken, String> {
        override fun decode(databaseValue: String): AccessToken = AccessToken(databaseValue)
        override fun encode(value: AccessToken): String = value.token
    }
    val refreshTokenAdapter = object : ColumnAdapter<RefreshToken, String> {
        override fun decode(databaseValue: String): RefreshToken = RefreshToken(databaseValue)
        override fun encode(value: RefreshToken): String = value.token
    }
    private val timeFormat = DateTimeFormatter.ISO_ZONED_DATE_TIME
    val zoneDateTimeAdapter = object : ColumnAdapter<ZonedDateTime, String> {
        override fun decode(databaseValue: String): ZonedDateTime =
            ZonedDateTime.parse(databaseValue, timeFormat)

        override fun encode(value: ZonedDateTime): String = value.format(timeFormat)
    }
}

//        array<Pos, 3> posses = {pos + UP, pos + DOWN, pos + LEFT, pos + RIGHT};
//        for (auto a : posses) {
//            auto item = map.find(a);
//            if (item != map.end()) {
//                if (set.find(*item) != set.end()) continue;
//                if (*item == Item::WALL) queue.push(*item);
//            }
//        }
