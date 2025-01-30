package tg.crsandroid.carpool

// Navigation.kt
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Map : Screen("map")
    object RideList : Screen("ride_list")
}