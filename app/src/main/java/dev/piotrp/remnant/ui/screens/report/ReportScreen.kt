package dev.piotrp.remnant.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.piotrp.remnant.R
import dev.piotrp.remnant.data.DonationModel
import dev.piotrp.remnant.data.fakeDonations
import dev.piotrp.remnant.ui.components.general.Centre
import dev.piotrp.remnant.ui.components.report.DonationCardList
import dev.piotrp.remnant.ui.components.report.ReportText
import dev.piotrp.remnant.ui.theme.RemnantTheme


@Composable
fun ReportScreen(modifier: Modifier = Modifier,
                 onClickDonationDetails: (Int) -> Unit,
                 reportViewModel: ReportViewModel = hiltViewModel()) {

    val donations = reportViewModel.uiDonations.collectAsState().value

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            ReportText()
            if(donations.isEmpty())
                Centre(Modifier.fillMaxSize()) {
                    Text(color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            else
                DonationCardList(
                    donations = donations,
                    onClickDonationDetails = onClickDonationDetails,
                    onDeleteDonation = {
                            donation: DonationModel ->
                                reportViewModel.deleteDonation(donation)
                    }
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    RemnantTheme {
        PreviewReportScreen( modifier = Modifier,
            donations = fakeDonations.toMutableStateList()
        )
    }
}

@Composable
fun PreviewReportScreen(modifier: Modifier = Modifier,
                        donations: SnapshotStateList<DonationModel>
) {

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            ReportText()
            if(donations.isEmpty())
                Centre(Modifier.fillMaxSize()) {
                    Text(color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            else
                DonationCardList(
                    donations = donations,
                    onDeleteDonation = {},
                    onClickDonationDetails = { }
                )
        }
    }
}