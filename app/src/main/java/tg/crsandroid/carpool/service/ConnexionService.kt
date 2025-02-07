package tg.crsandroid.carpool.service

import tg.crsandroid.carpool.manager.FirebaseAuthManager

class ConnexionService {
    object Conn {
        var managerCon : FirebaseAuthManager = FirebaseAuthManager()
        val ID_CLIENT = "171429724804-08chg2e0kfe5vlosq0ov0c1vecsrvcf6.apps.googleusercontent.com"
    }
}