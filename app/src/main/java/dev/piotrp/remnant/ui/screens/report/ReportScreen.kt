package dev.piotrp.remnant.ui.screens.report

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import dev.piotrp.remnant.data.model.RemnantModel
import dev.piotrp.remnant.data.model.fakeRemnants
import dev.piotrp.remnant.ui.components.general.Centre
import dev.piotrp.remnant.ui.components.general.ShowError
import dev.piotrp.remnant.ui.components.general.ShowLoader
import dev.piotrp.remnant.ui.components.report.SearchBar
import dev.piotrp.remnant.ui.components.report.RemnantCardList
import dev.piotrp.remnant.ui.theme.RemnantTheme
import timber.log.Timber


@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    onClickRemnantDetails: (String) -> Unit,
    reportViewModel: ReportViewModel = hiltViewModel(),
    switchChecked: Boolean = true
) {

    val remnants = reportViewModel.uiRemnants.collectAsState().value
    val isError = reportViewModel.iserror.value
    val error = reportViewModel.error.value
    val isLoading = reportViewModel.isloading.value

    val email = reportViewModel.email

    var searchQuery by remember { mutableStateOf("") }

    Timber.i("RS : Remnants List = $remnants")

//    LaunchedEffect(Unit) {
//        reportViewModel.getRemnants()
//    }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            if(isLoading) ShowLoader("Loading Remnants...")
//            ReportText()
//            if(!isError)
//                ShowRefreshList(onClick = { reportViewModel.getRemnants() })
            if (remnants.isEmpty() && !isError)
                Centre(Modifier.fillMaxSize()) {
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            if (!isError) {
                SearchBar(
                    modifier = modifier.padding(vertical = 20.dp),
                    onSearchChange = { searchQuery = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                RemnantCardList(
                    remnants = remnants.filter {
                        it.note.toLowerCase().contains(searchQuery.toLowerCase()) ||
                        it.type.toLowerCase().contains(searchQuery.toLowerCase())
                    }.filter {
                        if (switchChecked || email.isNullOrEmpty()) {
                            true
                        } else {
                            it.email == email
                        }
                    },
                    onClickRemnantDetails = onClickRemnantDetails,
                    onDeleteRemnant = { remnant: RemnantModel ->
                        reportViewModel.deleteRemnant(remnant)
                    },
                   // onRefreshList = { reportViewModel.getRemnants() }
                )
            }
            if (isError) {
                ShowError(headline = error.message!! + " error...",
                    subtitle = error.toString(),
                    onClick = { reportViewModel.getRemnants() })
            }
        }

    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReportScreenPreview() {
    RemnantTheme {
        PreviewReportScreen( modifier = Modifier,
            remnants = fakeRemnants.toMutableStateList()
        )
    }
}

@Composable
fun PreviewReportScreen(
    modifier: Modifier = Modifier,
    remnants: SnapshotStateList<RemnantModel>
) {

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
//            ReportText()
            if(remnants.isEmpty())
                Centre(Modifier.fillMaxSize()) {
                    Text(color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            else {
                SearchBar(
                    modifier = modifier.padding(vertical = 20.dp),
                    onSearchChange = { }
                )
                Spacer(modifier = Modifier.height(10.dp))
                RemnantCardList(
                    remnants = remnants,
                    onDeleteRemnant = {}
                ) { }
            }
        }
    }
}