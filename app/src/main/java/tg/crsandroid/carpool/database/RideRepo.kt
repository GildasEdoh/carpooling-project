package tg.crsandroid.carpool.database

import kotlinx.coroutines.tasks.await
import tg.crsandroid.carpool.model.Trajet

class RideRepo: BaseRepository<Trajet> ("trajets", Trajet::class.java) {

}