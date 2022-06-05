package cz.lastaapps.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cz.lastaapps.ui.common.locals.LocalSnackBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUI(
    loginViewModel: LoginViewModel,
    schoolsViewModel: SchoolsViewModel,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    CompositionLocalProvider(LocalSnackBarState provides snackBarHostState) {
        Scaffold(
            Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { insets ->
            Box(Modifier.padding(insets)) {
                LoginContent(
                    loginViewModel, schoolsViewModel,
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun LoginContent(
    loginViewModel: LoginViewModel,
    schoolsViewModel: SchoolsViewModel,
    modifier: Modifier = Modifier,
) {
    val enabled = !loginViewModel.loginInProgress.collectAsState().value
    val town by schoolsViewModel.selectedTown.collectAsState(null)
    val school by schoolsViewModel.selectedSchool.collectAsState(null)
    var url by rememberSaveable(school) { mutableStateOf(school?.url ?: "") }

    var profileName by rememberSaveable() { mutableStateOf(Prefill.profileName) }
    var username by rememberSaveable() { mutableStateOf(Prefill.username) }
    var password by rememberSaveable() { mutableStateOf(Prefill.password) }

    val onLogin: () -> Unit = {
        loginViewModel.doLogin(
            username, password, profileName, url, town, school
        )
    }

    val snackBarHostState = LocalSnackBarState.current
    LaunchedEffect(snackBarHostState) {
        val errors = loginViewModel.errors.receiveAsFlow()
        errors.collect {
            snackBarHostState.showSnackbar(it)
        }
    }
    LaunchedEffect(snackBarHostState) {
        val errors = schoolsViewModel.errors.receiveAsFlow()
        errors.collect {
            snackBarHostState.showSnackbar(it)
        }
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        loginViewModel.loggedIn.receiveAsFlow().collectLatest {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "User logged in", Toast.LENGTH_LONG).show()
            }
        }
    }

    Box(modifier, contentAlignment = Alignment.Center) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .sizeIn(maxWidth = 512.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Label()

            SchoolUI(schoolsViewModel, url, { url = it }, enabled)

            Fields(
                profileName, { profileName = it },
                username, { username = it },
                password, { password = it },
                onLogin, enabled,
            )

            Button(
                onClick = onLogin,
                enabled = enabled,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
private fun Label(modifier: Modifier = Modifier) {
    Text("Magistři", style = MaterialTheme.typography.headlineLarge, modifier = modifier)
    Text("Log in with you school Bakaláři account")
}

@Composable
private fun Fields(
    profileName: String, onProfileName: (String) -> Unit,
    username: String, onUsername: (String) -> Unit,
    password: String, onPassword: (String) -> Unit,
    onLogin: () -> Unit, enabled: Boolean,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    TextField(
        profileName, onProfileName,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Profile name") },
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Face, null) },
        keyboardOptions = KeyboardOptions(
            autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Next,
        ),
        enabled = enabled,
    )
    TextField(
        username, onUsername,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Username") },
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Person, null) },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false, keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next,
        ),
        enabled = enabled,
    )
    TextField(
        password, onPassword,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Password") },
        leadingIcon = { Icon(Icons.Default.Key, null) },
        singleLine = true,
        visualTransformation =
        if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    if (showPassword) "Hide password" else "Show password",
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false, keyboardType = KeyboardType.Password, imeAction = ImeAction.Go,
        ),
        keyboardActions = KeyboardActions { onLogin() },
        enabled = enabled,
    )
}
