package cz.lastaapps.api.login

import cz.lastaapps.api.login.auth.AuthRequests
import cz.lastaapps.api.login.db.UserStorage
import cz.lastaapps.api.login.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class AuthRepoImpl internal constructor(
    private val authRequests: AuthRequests,
    private val userStorage: UserStorage,
    private val scope: CoroutineScope,
) : AuthRepo {

    override val errors = Channel<Throwable>(Channel.BUFFERED)

    init {
        scope.launch {
            authRequests.errors.receiveAsFlow().collect { errors.send(it) }
        }
    }

    override fun hasUsers(): Flow<Boolean> = userStorage.hasUsers()

    override fun getDefaultUser(): Flow<UserId?> {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(
        username: Username,
        password: Password,
        profileName: ProfileName,
        schoolUrl: SchoolUrl,
        town: String?,
        school: String?,
    ): UserId? {
        if (userStorage.userWithProfileName(profileName).first() != null) {
            errors.send(Throwable("This profile name already exists"))
            return null
        }

        val response = authRequests.tryLogin(schoolUrl, username, password) ?: return null
        val login = UserLogin(
            UserId.useAuto, username, password, schoolUrl, town, school
        )

        val id = userStorage.insertNew(login)
        val tokens = response.toTokens(id)
        val profile = UserProfile(id, profileName)

        userStorage.updateUserTokens(tokens)
        userStorage.updateUserProfile(profile)

        return id
    }

    override suspend fun refreshUser(id: UserId): UserId? {
        val currentLogin = userStorage.getUserLogin(id).first()!!
        val currentTokens = userStorage.getUserTokens(id).first()!!
        val newTokens =
            authRequests.tryRefresh(currentLogin.schoolUrl, currentTokens.refresh) ?: return null

        userStorage.updateUserTokens(newTokens.toTokens(id))

        return id
    }

    override suspend fun updatePassword(id: UserId, password: Password): UserId? {
        val currentLogin = userStorage.getUserLogin(id).first()!!

        val response = authRequests.tryLogin(
            currentLogin.schoolUrl, currentLogin.username, password
        ) ?: return null

        val login = currentLogin.copy(password = password)
        val tokens = response.toTokens(id)

        userStorage.updateUserLogin(login)
        userStorage.updateUserTokens(tokens)
        return id
    }

    override suspend fun updateProfile(id: UserId, profileName: ProfileName) {
        if (userStorage.userWithProfileName(profileName).first() != id) {
            errors.send(Throwable("This profile name already exists"))
        }

        val currentProfile = userStorage.getUserProfile(id).first()!!
        userStorage.updateUserProfile(currentProfile.copy(profileName = profileName))
    }

    override suspend fun logoutUser(id: UserId) {
        TODO("Not yet implemented")
    }

    private fun LoginResponse.toTokens(userId: UserId): UserTokens =
        UserTokens(
            userId, RefreshToken(refreshToken), AccessToken(accessToken),
            ZonedDateTime.now().plusSeconds(expiresIn.toLong()).toInstant()
        )
}