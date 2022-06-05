package cz.lastaapps.api.login.school

import cz.lastaapps.api.login.Constants
import cz.lastaapps.api.login.entities.School
import cz.lastaapps.api.login.entities.SchoolsInTown
import cz.lastaapps.api.login.entities.Town
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.channels.Channel
import org.lighthousegames.logging.logging

class SchoolRepoImpl : SchoolRepo {

    companion object {
        private val log = logging()
    }

    private val httpClient = HttpClient(CIO) {
        defaultRequest {
            accept(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json()
        }
    }

    override val errors = Channel<Throwable>(capacity = Channel.BUFFERED)

    override suspend fun loadTowns(): List<Town>? =
        try {
            log.i { "Loading towns" }

            httpClient.get {
                url(Constants.townsUrl)
            }.body<List<Town>>()
                .filter { it.name.isNotBlank() } // to remove the one blank town
                .also { log.i { "Loaded ${it.size} towns" } }
        } catch (e: Exception) {
            log.e(e) { "Loading towns failed" }
            errors.send(e)
            null
        }

    override suspend fun loadSchools(town: Town): List<School>? =
        try {
            log.i { "Loading schools for ${town.name}" }

            httpClient.get {
                url("${Constants.schoolsUrl}/${town.urlName}")
            }.body<SchoolsInTown>().schools.also {
                log.i { "Loaded ${it.size} schools" }
            }
        } catch (e: Exception) {
            log.e(e) { "Loading schools for ${town.name} failed" }
            errors.send(e)
            null
        }
}