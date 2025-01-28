package tg.crsandroid.carpool.database

import tg.crsandroid.carpool.model.Trajet

class RideRepo: BaseRepository<Trajet> ("trajets", Trajet::class.java) {

}