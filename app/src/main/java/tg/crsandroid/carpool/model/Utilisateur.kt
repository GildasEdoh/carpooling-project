package com.example.carpooling_project.model

data class utilisateur(
  var id : String? = "",
  var nom : String? = "",
  var prenom : String? = "",
  var email: String? = "",

  var reservations: List<String> = mutableListOf(),
  var type: String? = "",
  var numPhone: String? = ""
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
