package dev.piotrp.remnant.ui.screens.reminisce

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.piotrp.remnant.R
import dev.piotrp.remnant.data.model.RemnantModel
import dev.piotrp.remnant.data.model.fakeRemnants
import dev.piotrp.remnant.ui.components.general.ButtonComponent
import dev.piotrp.remnant.ui.components.reminisce.ReminisceButton
import dev.piotrp.remnant.ui.components.reminisce.MessageInput
import dev.piotrp.remnant.ui.components.reminisce.RadioButtonGroup
import dev.piotrp.remnant.ui.components.reminisce.RemnantTypeDropdownMenu
import dev.piotrp.remnant.ui.components.reminisce.RemnantTypeIntensitySlider
import dev.piotrp.remnant.ui.components.reminisce.WelcomeText
import dev.piotrp.remnant.ui.screens.report.ReportViewModel
import dev.piotrp.remnant.ui.theme.RemnantTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import kotlin.math.roundToInt

// Photo code copied from https://medium.com/@dheerubhadoria/capturing-images-from-camera-in-android-with-jetpack-compose-a-step-by-step-guide-64cd7f52e5de

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

@Composable
fun ReminisceScreen(modifier: Modifier = Modifier,
                    reportViewModel: ReportViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var remnantType by remember { mutableStateOf("Funny") }
    var remnantTypeIntensity by remember { mutableFloatStateOf(1f) }
    var remnantMessage by remember { mutableStateOf("Found Bigfoot here.") }

    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "${context.packageName}.provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            cameraLauncher.launch(uri)
        }
    }

    Column() {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            WelcomeText()
            Spacer(modifier = Modifier.height(10.dp))
            if (capturedImageUri != Uri.EMPTY) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(capturedImageUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = FillBounds,
                    placeholder = painterResource(R.drawable.tramore),
                    modifier = Modifier
                        .height(200.dp)
                        .clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                )
            }
            ButtonComponent(
                isCaptureButton = true,
                value = stringResource(id = R.string.capturePhotoButton),
                onButtonClicked = {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(uri)
                    } else {
                        // Request a permission
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                isEnabled = true
            )
            RemnantTypeDropdownMenu(
                onRemnantTypeChange = { remnantType = it }
            )
            RemnantTypeIntensitySlider {
                remnantTypeIntensity = it
            }
            MessageInput(
                modifier = modifier,
                onMessageChange = { remnantMessage = it }
            )
            ReminisceButton (
                modifier = modifier,
                remnant = RemnantModel(
                    type = remnantType,
                    typeIntensity = remnantTypeIntensity.roundToInt().toFloat(),
                    note = remnantMessage,
                    remnantImageUri = capturedImageUri.toString()
                ),
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
                remnant = RemnantModel(
                    type = paymentType,
                    note = paymentMessage
                ),
            )
        }
    }
}