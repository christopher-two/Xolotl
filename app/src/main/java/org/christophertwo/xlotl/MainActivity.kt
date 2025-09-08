package org.christophertwo.xlotl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.first
import org.christophertwo.xlotl.presentation.navigation.NavigationApp
import org.christophertwo.xlotl.presentation.theme.XólotlTheme
import org.christophertwo.xlotl.session.api.SessionRepository
import org.christophertwo.xlotl.core.RoutesApp
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XólotlTheme {
                var isUserLoggedIn by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    try {
                        isUserLoggedIn = get<SessionRepository>().isUserLoggedIn().first()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                Surface(
                    color = colorScheme.background,
                    contentColor = colorScheme.onBackground,
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        NavigationApp(
                            navController = rememberNavController(),
                            startDestination = if (!isUserLoggedIn) RoutesApp.Start.route else RoutesApp.Home.route
                        )
                    }
                )
            }
        }
    }
}