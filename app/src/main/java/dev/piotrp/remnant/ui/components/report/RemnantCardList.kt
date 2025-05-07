package dev.piotrp.remnant.ui.components.report

import android.content.res.Configuration
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
import kotlin.math.roundToInt

@Composable
internal fun RemnantCardList(
    remnants: List<RemnantModel>,
    modifier: Modifier = Modifier,
    defaultExpanded: Boolean = false,
    onDeleteRemnant: (RemnantModel) -> Unit,
    onClickRemnantDetails: (String) -> Unit
) {
    LazyColumn {
        items(
            items = remnants,
            key = { remnant -> remnant._id }
        ) { remnant ->
            RemnantCard(
                paymentType = remnant.type,
                typeIntensity = remnant.typeIntensity.roundToInt().toString(),
                message = remnant.note,
                dateCreated = DateFormat.getDateTimeInstance().format(remnant.dateCreated),
                dateModified = DateFormat.getDateTimeInstance().format(remnant.dateModified),
                onClickDelete = { onDeleteRemnant(remnant) },
                onClickRemnantDetails = { onClickRemnantDetails(remnant._id) },
                defaultExpanded = defaultExpanded,
                photoUri = Uri.parse(remnant.avatarImageUri),
                remnantPhotoUri = Uri.parse(remnant.remnantImageUri)
            )
        }
    }

}

@Preview(showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RemnantCardListPreview() {
    RemnantTheme {
        RemnantCardList(
            fakeRemnants.toMutableStateList(),
            defaultExpanded = true,
            onDeleteRemnant = {}
        ) { }
    }
}
