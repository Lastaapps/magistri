package cz.lastaapps.api.login.auth.entities

@JvmInline
value class SchoolUrl(private val schoolUrl: String) {
    val raw: String get() = schoolUrl
    val url: String get() = schoolUrl.removeSuffix("/")
    val api: String get() = "$url/api/3"
}