package cz.lastaapps.api.login.school.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.Collator
import java.util.*


/**Holds the info about a school*/
@Serializable
data class School(
    val id: String,
    val name: String,
    @SerialName("schoolUrl")
    val url: String
) : Comparable<School> {

    override fun compareTo(other: School): Int {
        val collator = Collator.getInstance(Locale.getDefault())

        return collator.compare(name, other.name)
    }
}
