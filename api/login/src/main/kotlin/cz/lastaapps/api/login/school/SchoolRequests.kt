package cz.lastaapps.api.login.school

import cz.lastaapps.api.login.entities.School
import cz.lastaapps.api.login.entities.Town
import kotlinx.coroutines.channels.Channel

interface SchoolRequests {
    val errors: Channel<Throwable>
    suspend fun loadTowns(): List<Town>?
    suspend fun loadSchools(town: Town): List<School>?
}