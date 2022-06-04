package cz.lastaapps.api.login.entities

//@JvmInline value
data class UserId(val id: Long) {
    companion object {
        val useAuto = UserId(0)
    }
}