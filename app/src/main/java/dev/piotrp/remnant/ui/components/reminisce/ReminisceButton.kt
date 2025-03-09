package dev.piotrp.remnant.ui.components.reminisce

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
import dev.piotrp.remnant.ui.screens.reminisce.RemnantViewModel
import dev.piotrp.remnant.ui.screens.report.ReportViewModel
import dev.piotrp.remnant.ui.theme.RemnantTheme
import timber.log.Timber

@Composable
fun ReminisceButton(
    modifier: Modifier = Modifier,
    remnant: RemnantModel,
    remnantViewModel: RemnantViewModel = hiltViewModel(),
    reportViewModel: ReportViewModel = hiltViewModel(),
) {
    val remnants = reportViewModel.uiRemnants.collectAsState().value
    val context = LocalContext.current

    Row {
        Button(
            onClick = {
                remnantViewModel.insert(remnant)
                Timber.i("Remnant info : $remnant")
                Timber.i("Remnant List info : ${remnants.toList()}")
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Reminisce")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.reminisceButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReminisceButtonPreview() {
    RemnantTheme {
        PreviewReminisceButton(
            Modifier,
            RemnantModel(),
            remnants = fakeRemnants.toMutableStateList()
        )
    }
}

@Composable
fun PreviewReminisceButton(
    modifier: Modifier = Modifier,
    remnant: RemnantModel,
    remnants: SnapshotStateList<RemnantModel>,
) {
    val context = LocalContext.current

    Row {
        Button(
            onClick = {
                remnants.add(remnant)
                Timber.i("Remnant info : $remnant")
                Timber.i("Remnant List info : ${remnants.toList()}")
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Reminisce")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.reminisceButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}