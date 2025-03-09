package ie.setu.donationx.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import kotlin.random.Random

@Entity
data class DonationModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val paymentType: String = "N/A",
    val paymentAmount: Int = 0,
    var message: String = "Go Homer!",
    val dateDonated: Date = Date()
)

val fakeDonations = List(30) { i ->
    DonationModel(id = 12345 + i,
        "PayPal $i",
        i.toInt(),
        "Message $i",
        Date()
    )
}
