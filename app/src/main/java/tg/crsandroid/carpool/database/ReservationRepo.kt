package tg.crsandroid.carpool.database

import tg.crsandroid.carpool.model.Reservation

class ReservationRepo: BaseRepository<Reservation>("reservations", Reservation::class.java) {

}