package cz.lastaapps.api.login.entities

import kotlinx.serialization.Serializable

@Serializable
internal data class SchoolsInTown(
    val name: String,
    val schools: List<School>
)