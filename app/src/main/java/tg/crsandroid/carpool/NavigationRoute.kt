package tg.crsandroid.carpool

sealed class NavigationRoute(val route: String) {
    object Login : NavigationRoute("login")
    object Signup : NavigationRoute("signup")
    object Home : NavigationRoute("home")
}