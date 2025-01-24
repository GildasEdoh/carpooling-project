package tg.crsandroid.carpool.model

data class Reservation (
    var id : String? = "",
    var idConducteur: String? = "",
    var idPassagers:List<String> = mutableListOf(),
    var date: String? = "",
    var idTrajet: String? = ""
) {
    companion object {
        const val COLLECTIONS_RESERVATIONS = "reservations"

        const val ID = "id"
        const val ID_CONDUCTEUR_FIELD = "idConducteur"
        const val ID_PASSAGERS_FIELD = "idPassagers"
        const val DATE_FIELD = "date"
        const val ID_TRAJET_FIELD = "idTrajet"
    }
}