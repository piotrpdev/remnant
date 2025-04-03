package dev.piotrp.remnant.data.model

import androidx.room.Entity
import com.google.firebase.firestore.DocumentId
import java.util.Date

@Entity
data class RemnantModel(
    @DocumentId val _id: String = "N/A",
    val paymentType: String = "N/A",
    val paymentAmount: Int = 0,
    var message: String = "Go Homer!",
    val dateReminisced: Date = Date(),
    val dateModified: Date = Date(),
    var email: String = "joe@bloggs.com",
    var imageUri: String = ""
)

val fakeRemnants = List(30) { i ->
    RemnantModel(
        _id = "12345" + i,
        "PayPal $i",
        i.toInt(),
        "Message $i",
        Date(),
        Date()
    )
}
