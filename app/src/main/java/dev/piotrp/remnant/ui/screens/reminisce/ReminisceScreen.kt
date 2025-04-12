package dev.piotrp.remnant.ui.screens.reminisce

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.piotrp.remnant.data.model.RemnantModel
import dev.piotrp.remnant.data.model.fakeRemnants
import dev.piotrp.remnant.ui.components.reminisce.AmountPicker
import dev.piotrp.remnant.ui.components.reminisce.ReminisceButton
import dev.piotrp.remnant.ui.components.reminisce.MessageInput
import dev.piotrp.remnant.ui.components.reminisce.ProgressBar
import dev.piotrp.remnant.ui.components.reminisce.RadioButtonGroup
import dev.piotrp.remnant.ui.components.reminisce.WelcomeText
import dev.piotrp.remnant.ui.screens.report.ReportViewModel
import dev.piotrp.remnant.ui.theme.RemnantTheme

@Composable
fun ReminisceScreen(modifier: Modifier = Modifier,
                    reportViewModel: ReportViewModel = hiltViewModel()
) {
    var paymentType by remember { mutableStateOf("Funny") }
    var paymentMessage by remember { mutableStateOf("Found Bigfoot here.") }
    val remnants = reportViewModel.uiRemnants.collectAsState().value

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            WelcomeText()
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                RadioButtonGroup(
                    modifier = modifier,
                    onPaymentTypeChange = { paymentType = it }
                )
            }
            MessageInput(
                modifier = modifier,
                onMessageChange = { paymentMessage = it }
            )
            ReminisceButton (
                modifier = modifier,
                remnant = RemnantModel(type = paymentType,
                    note = paymentMessage),
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReminisceScreenPreview() {
    RemnantTheme {
        PreviewReminisceScreen( modifier = Modifier,
            remnants = fakeRemnants.toMutableStateList())
    }
}

@Composable
fun PreviewReminisceScreen(
    modifier: Modifier = Modifier,
    remnants: SnapshotStateList<RemnantModel>
) {
    var paymentType by remember { mutableStateOf("Funny") }
    var paymentMessage by remember { mutableStateOf("Found Bigfoot here.") }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            WelcomeText()
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                RadioButtonGroup(
                    modifier = modifier,
                    onPaymentTypeChange = { paymentType = it }
                )
            }
            MessageInput(
                modifier = modifier,
                onMessageChange = { paymentMessage = it }
            )
            ReminisceButton (
                modifier = modifier,
                remnant = RemnantModel(type = paymentType,
                    note = paymentMessage),
            )
        }
    }
}