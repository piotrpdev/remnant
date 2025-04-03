package dev.piotrp.remnant.ui.components.report

import android.net.Uri
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import dev.piotrp.remnant.data.model.RemnantModel
import dev.piotrp.remnant.data.model.fakeRemnants
import dev.piotrp.remnant.ui.theme.RemnantTheme
import java.text.DateFormat

@Composable
internal fun RemnantCardList(
    remnants: List<RemnantModel>,
    modifier: Modifier = Modifier,
    onDeleteRemnant: (RemnantModel) -> Unit,
    onClickRemnantDetails: (String) -> Unit
) {
    LazyColumn {
        items(
            items = remnants,
            key = { remnant -> remnant._id }
        ) { remnant ->
            RemnantCard(
                paymentType = remnant.paymentType,
                paymentAmount = remnant.paymentAmount,
                message = remnant.message,
                dateCreated = DateFormat.getDateTimeInstance().format(remnant.dateReminisced),
                dateModified = DateFormat.getDateTimeInstance().format(remnant.dateModified),
                onClickDelete = { onDeleteRemnant(remnant) },
                onClickRemnantDetails = { onClickRemnantDetails(remnant._id) },
                photoUri = Uri.parse(remnant.imageUri)
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
            onDeleteRemnant = {}
        ) { }
    }
    }
