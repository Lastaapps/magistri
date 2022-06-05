package cz.lastaapps.bakalari

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cz.lastaapps.bakalari.ui.theme.AppTheme
import cz.lastaapps.ui.login.LoginUI
import cz.lastaapps.ui.login.LoginViewModel
import cz.lastaapps.ui.login.SchoolsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val schoolsViewModel: SchoolsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition { false }

        setContent {
            AppTheme {
                Surface(Modifier.fillMaxSize()) {
                    LoginUI(loginViewModel, schoolsViewModel)
                }
            }
        }
    }
}