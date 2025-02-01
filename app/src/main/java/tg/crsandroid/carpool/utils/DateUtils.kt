package tg.crsandroid.carpool.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Fonction pour formater l'heure
fun formatTimestamp(timestamp: Date?): String {
    if (timestamp == null) return ""
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(timestamp)
}