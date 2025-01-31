package tg.crsandroid.carpool

sealed class NavigationRoute(val route: String) {
    object Home : NavigationRoute("Home")
    object History : NavigationRoute("History")
    object Profile : NavigationRoute("Profile")
    object Chat : NavigationRoute("Chat")
}