package dev.piotrp.remnant.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class RemnantModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val paymentType: String = "N/A",
    val paymentAmount: Int = 0,
    var message: String = "This was nice.",
    val dateReminisced: Date = Date()
)

val fakeRemnants = List(30) { i ->
    RemnantModel(id = 12345 + i,
        "PayPal $i",
        i.toInt(),
        "Message $i",
        Date()
    )
}
