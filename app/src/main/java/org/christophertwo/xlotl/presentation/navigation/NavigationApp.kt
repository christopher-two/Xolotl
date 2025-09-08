package org.christophertwo.xlotl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.christophertwo.xlotl.core.RoutesApp
import org.christophertwo.xlotl.presentation.screens.start.StartRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationApp(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(RoutesApp.Start.route) {
            StartRoot(
                viewModel = koinViewModel(),
                navController = navController
            )
        }
        composable(RoutesApp.Home.route) {
        }
    }
}