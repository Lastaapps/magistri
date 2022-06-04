package cz.lastaapps.api.login.entities

import kotlinx.serialization.Serializable

@Serializable
data class SchoolsInTown(
    val name: String,
    val schools: List<School>
)