package dev.piotrp.remnant.ui.components.donate

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.piotrp.remnant.R
import dev.piotrp.remnant.data.RemnantModel
import dev.piotrp.remnant.data.fakeRemnants
import dev.piotrp.remnant.ui.screens.donate.DonateViewModel
import dev.piotrp.remnant.ui.screens.report.ReportViewModel
import dev.piotrp.remnant.ui.theme.RemnantTheme
import timber.log.Timber

@Composable
fun DonateButton(
    modifier: Modifier = Modifier,
    remnant: RemnantModel,
    donateViewModel: DonateViewModel = hiltViewModel(),
    reportViewModel: ReportViewModel = hiltViewModel(),
    onTotalDonatedChange: (Int) -> Unit
) {
    val remnants = reportViewModel.uiRemnants.collectAsState().value
    var totalDonated = remnants.sumOf { it.paymentAmount }
    val context = LocalContext.current
    val message = stringResource(R.string.limitExceeded,remnant.paymentAmount)

    Row {
        Button(
            onClick = {
                if(totalDonated + remnant.paymentAmount <= 10000) {
                    totalDonated+=remnant.paymentAmount
                    onTotalDonatedChange(totalDonated)
                    donateViewModel.insert(remnant)
                    Timber.i("Remnant info : $remnant")
                    Timber.i("Remnant List info : ${remnants.toList()}")
                }
                else
                    Toast.makeText(context,message,
                        Toast.LENGTH_SHORT).show()
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Donate")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.donateButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier.weight(1f))
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(R.string.total) + " €")
                }


                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary)
                ) {
                    append(totalDonated.toString())
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun DonateButtonPreview() {
    RemnantTheme {
        PreviewDonateButton(
            Modifier,
            RemnantModel(),
            remnants = fakeRemnants.toMutableStateList()
        ) {}
    }
}

@Composable
fun PreviewDonateButton(
    modifier: Modifier = Modifier,
    remnant: RemnantModel,
    remnants: SnapshotStateList<RemnantModel>,
    onTotalDonatedChange: (Int) -> Unit
) {

    var totalDonated = remnants.sumOf { it.paymentAmount }
    val context = LocalContext.current
    val message = stringResource(R.string.limitExceeded,remnant.paymentAmount)

    Row {
        Button(
            onClick = {
                if(totalDonated + remnant.paymentAmount <= 10000) {
                    totalDonated+=remnant.paymentAmount
                    onTotalDonatedChange(totalDonated)
                    remnants.add(remnant)
                    Timber.i("Remnant info : $remnant")
                    Timber.i("Remnant List info : ${remnants.toList()}")
                }
                else
                    Toast.makeText(context,message,
                        Toast.LENGTH_SHORT).show()
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Donate")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.donateButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier.weight(1f))
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(R.string.total) + " €")
                }


                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary)
                ) {
                    append(totalDonated.toString())
                }
            })
    }
}