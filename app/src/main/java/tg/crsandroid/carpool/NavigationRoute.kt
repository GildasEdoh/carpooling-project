package tg.crsandroid.carpool

sealed class NavigationRoute(val route: String) {
    object Login : NavigationRoute("login")
    object Chat : NavigationRoute("ChatActivity")
    object Home : NavigationRoute("HomeScreen")
}