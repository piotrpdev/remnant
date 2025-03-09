package dev.piotrp.remnant.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class RemnantModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remnantType: String = "N/A",
    var message: String = "Saw Bigfoot here.",
    val dateReminisced: Date = Date()
)

val fakeRemnants = List(30) { i ->
    RemnantModel(id = 12345 + i,
        "Funny $i",
        "Message $i",
        Date()
    )
}
