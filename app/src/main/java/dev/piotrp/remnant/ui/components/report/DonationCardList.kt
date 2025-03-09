package dev.piotrp.remnant.ui.components.report

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import dev.piotrp.remnant.data.RemnantModel
import dev.piotrp.remnant.data.fakeRemnants
import dev.piotrp.remnant.ui.theme.RemnantTheme
import java.text.DateFormat

@Composable
internal fun RemnantCardList(
    remnants: List<RemnantModel>,
    modifier: Modifier = Modifier,
    onDeleteRemnant: (RemnantModel) -> Unit,
    onClickRemnantDetails: (Int) -> Unit,
) {
    LazyColumn {
        items(
            items = remnants,
            key = { remnant -> remnant.id }
        ) { remnant ->
            RemnantCard(
                paymentType = remnant.paymentType,
                paymentAmount = remnant.paymentAmount,
                message = remnant.message,
                dateCreated = DateFormat.getDateTimeInstance().format(remnant.dateDonated),
                onClickDelete = { onDeleteRemnant(remnant) },
                onClickRemnantDetails = { onClickRemnantDetails(remnant.id) }
            )
        }
    }
}

@Preview(showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun RemnantCardListPreview() {
    RemnantTheme {
        RemnantCardList(
            fakeRemnants.toMutableStateList(),
            onDeleteRemnant = {},
            onClickRemnantDetails = { },
        )
        }
    }
