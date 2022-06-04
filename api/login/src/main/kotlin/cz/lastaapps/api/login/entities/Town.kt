package cz.lastaapps.api.login.entities

import android.net.Uri
import kotlinx.serialization.Serializable
import java.text.Collator
import java.util.*

/**holds the info about a town*/
@Serializable
data class Town(
    val name: String,
    val schoolCount: Int,
) : Comparable<Town> {

    override fun compareTo(other: Town): Int {
        val collator = Collator.getInstance(Locale.getDefault())

        return collator.compare(name, other.name)
    }

    /**
     * @return the part of name before any '.' character URI encoded
     * */
    val urlName
        get(): String {
            val index = name.indexOf('.')
            val fileName = if (index >= 0) name.substring(0, index) else name
            return Uri.encode(fileName)
        }
}
