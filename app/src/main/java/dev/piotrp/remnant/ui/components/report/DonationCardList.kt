package dev.piotrp.remnant.ui.components.report

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import dev.piotrp.remnant.data.DonationModel
import dev.piotrp.remnant.data.fakeDonations
import dev.piotrp.remnant.ui.theme.RemnantTheme
import java.text.DateFormat

@Composable
internal fun DonationCardList(
    donations: List<DonationModel>,
    modifier: Modifier = Modifier,
    onDeleteDonation: (DonationModel) -> Unit,
    onClickDonationDetails: (Int) -> Unit,
) {
    LazyColumn {
        items(
            items = donations,
            key = { donation -> donation.id }
        ) { donation ->
            DonationCard(
                paymentType = donation.paymentType,
                paymentAmount = donation.paymentAmount,
                message = donation.message,
                dateCreated = DateFormat.getDateTimeInstance().format(donation.dateDonated),
                onClickDelete = { onDeleteDonation(donation) },
                onClickDonationDetails = { onClickDonationDetails(donation.id) }
            )
        }
    }
}

@Preview(showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun DonationCardListPreview() {
    RemnantTheme {
        DonationCardList(
            fakeDonations.toMutableStateList(),
            onDeleteDonation = {},
            onClickDonationDetails = { },
        )
        }
    }
