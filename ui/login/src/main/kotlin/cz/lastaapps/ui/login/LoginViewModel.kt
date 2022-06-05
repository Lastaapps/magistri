package cz.lastaapps.ui.login

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.lastaapps.api.login.AuthRepo
import cz.lastaapps.api.login.entities.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepo,
) : ViewModel() {

    val errors = Channel<String>(Channel.BUFFERED)
    val loggedIn = Channel<UserId>(Channel.BUFFERED)

    val loginInProgress = MutableStateFlow(false)

    fun doLogin(
        username: String,
        password: String,
        profileName: String,
        url: String,
        town: Town?,
        school: School?,
    ) {
        if (loginInProgress.value) return
        loginInProgress.value = true
        viewModelScope.launch {
            try {
                validateInput(username, password, profileName, url)
            } catch (e: Exception) {
                errors.send(e.message ?: "Unknown error")
                e.printStackTrace()
            }
            authRepo.loginUser(
                Username(username), Password(password), ProfileName(profileName),
                SchoolUrl(url), town?.name, school?.name
            )?.let {
                loggedIn.send(it)
            }

            loginInProgress.value = false
        }
    }

    private fun validateInput(
        username: String,
        password: String,
        profileName: String,
        url: String,
    ) {
        check(username.isNotBlank()) { "Username cannot be blank" }
        check(password.isNotBlank()) { "Password cannot be blank" }
        check(profileName.isNotBlank()) { "Profile name cannot be blank" }
        check(url.isNotBlank()) { "Url cannot be blank" }
        Uri.parse(url)
    }

    init {
        viewModelScope.launch {
            authRepo.errors.receiveAsFlow().collect {
                errors.send(it.message ?: "Unknown")
            }
        }
    }
}