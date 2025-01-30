package com.example.carpooling_project.model

import com.google.type.LatLng
import tg.crsandroid.carpool.model.Trajet

data class Utilisateur(
    var id : String? = "",
    var nom : String? = "",
    var prenom : String? = "",
    var email: String? = "",
    var nomUtilisateur: String? = nom + prenom,

    var reservations: List<String> = mutableListOf(),
    var type: String? = "",
    var numPhone: String? = "",
    var motDePasse: String? = "",
    var trajets: List<Trajet> = mutableListOf(),
    val location: LatLng? = null

) {
    companion object {
        const val COLLECTIONS_USERS = "users"
        
        const val ID_FIELD = "id"
        const val NOM_FIELD = "nom"
        const val PRENOM_FIELD = "prenom"
        const val EMAIL_FIELD = "email"
        const val SEATS_FIELD = "nbrSeats"
        const val TYPE_FIELD = "type"

    }
}
