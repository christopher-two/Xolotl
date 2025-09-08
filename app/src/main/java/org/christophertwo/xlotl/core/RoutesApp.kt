package org.christophertwo.xlotl.core

sealed class RoutesApp(
    val route: String
) {
    object Start : RoutesApp("start_screen")
    object Home : RoutesApp("home_screen")
}