package tg.crsandroid.carpool.model

import com.google.android.gms.maps.model.LatLng

data class Trajet(
    var id: String = "",
    var lieuDepart: String = "",
    var lieuArrivee: String = "",
    var heureDepart: String = "",
    var heureArrivee: String = "",
    var duree: String = "",
    var nbrSeats: String = "",
    val idConducteur: String = "",
    var reservations: List<String> = mutableListOf(),
    val prix: String = "",
    val lattitute: Double = 0.0,
    val longitude: Double = 0.0
//    val passagers: Any
) {
    companion object {
        const val COLLECTION_TRAJETS = "trajets"

        const val ID_FIELD = "id"
        const val LIEU_DEPART_FIELD = "lieuDepart"
        const val LIEU_ARRIVEE_FIELD = "lieuArrivee"
        const val HEURE_DEPART_FIELD = "heureDepart"
        const val HEURE_ARRIVEE_FIELD = "heureArrivee"
        const val RESERVATIONS = "reservations"
        const val DUREE = "duree"
        const val NBRE_SEAT_FIELD = "duree"
    }
}