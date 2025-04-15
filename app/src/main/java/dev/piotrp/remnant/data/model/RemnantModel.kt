package dev.piotrp.remnant.data.model

import androidx.room.Entity
import com.google.firebase.firestore.DocumentId
import java.util.Date

@Entity
data class RemnantModel(
    @DocumentId val _id: String = "N/A",
    val type: String = "N/A",
    var note: String = "Saw Bigfoot here.",
    val dateCreated: Date = Date(),
    val dateModified: Date = Date(),
    var email: String = "joe@bloggs.com",
    var avatarImageUri: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var remnantImageUri: String = ""
)

val fakeRemnants = List(30) { i ->
    RemnantModel(
        _id = "12345" + i,
        type = "Funny $i",
        note = "Note $i",
        dateCreated = Date(),
        dateModified = Date(),
        email = "joe" + i + "@bloggs.com",
        avatarImageUri = "",
        latitude = 0.0,
        longitude = 0.0,
        remnantImageUri = ""
    )
}
