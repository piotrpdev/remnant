package dev.piotrp.remnant.ui.components.report

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.piotrp.remnant.R
import dev.piotrp.remnant.ui.theme.RemnantTheme
import dev.piotrp.remnant.ui.theme.endGradientColor
import dev.piotrp.remnant.ui.theme.startGradientColor
import java.text.DateFormat
import java.util.Date

@Composable
fun RemnantCard(
    paymentType: String,
    typeIntensity: String,
    message: String,
    dateCreated: String,
    dateModified: String,
    onClickDelete: () -> Unit,
    onClickRemnantDetails: () -> Unit,
    defaultExpanded: Boolean = false,
    photoUri: Uri,
    remnantPhotoUri: Uri
) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 2.dp)
    ) {
        RemnantCardContent(paymentType,
            typeIntensity,
            message,
            dateCreated,
            dateModified,
            onClickDelete,
            onClickRemnantDetails,
            defaultExpanded,
            photoUri,
            remnantPhotoUri
        )
    }
}

@Composable
private fun RemnantCardContent(
    paymentType: String,
    typeIntensity: String,
    message: String,
    dateCreated: String,
    dateModified: String,
    onClickDelete: () -> Unit,
    onClickRemnantDetails: () -> Unit,
    defaultExpanded: Boolean = false,
    photoUri: Uri,
    remnantPhotoUri: Uri
) {
    var expanded by remember { mutableStateOf(defaultExpanded) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(2.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .background(brush = Brush.horizontalGradient(
                colors = listOf(
                    startGradientColor,
                    endGradientColor,
                )
            ))
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(remnantPhotoUri)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(200.dp)
                        .clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "$paymentType ($typeIntensity)",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = Color.White
            )
            if (expanded) {
                Text(modifier = Modifier.padding(vertical = 16.dp), text = message, color = Color.White)
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photoUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Modified $dateModified", style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
        }
        Column {
            Row {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess
                        else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded) {
                            stringResource(R.string.show_less)
                        } else {
                            stringResource(R.string.show_more)
                        },
                        tint = Color.White
                    )
                }
            }
            Row {
                Column {
                    FilledTonalIconButton(onClick = onClickRemnantDetails) {
                        Icon(Icons.Filled.Edit, "Edit Remnant")
                    }

                    FilledTonalIconButton(onClick = {
                        showDeleteConfirmDialog = true
                    }) {
                        Icon(Icons.Filled.Delete, "Delete Remnant")
                    }

                    if (showDeleteConfirmDialog) {
                        showDeleteAlert(
                            onDismiss = { showDeleteConfirmDialog = false },
                            onDelete = onClickDelete,
//                            onRefresh = onRefreshList
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun showDeleteAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss ,
        title = { Text(stringResource(id = R.string.confirm_delete)) },
        text = { Text(stringResource(id = R.string.confirm_delete_message)) },
        confirmButton = {
            Button(
                onClick = {
                    onDelete()
                    //onRefresh()
                }
            ) { Text("Yes") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("No") }
        }
    )
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RemnantCardPreview() {
    RemnantTheme {
        RemnantCard(
            paymentType = "Funny",
            typeIntensity = "5",
            message = """
                A message entered by the user, it has a long story about something.
            """.trimIndent(),
            dateCreated = DateFormat.getDateTimeInstance().format(Date()),
            dateModified = DateFormat.getDateTimeInstance().format(Date()),
            onClickDelete = { },
            onClickRemnantDetails = {},
            defaultExpanded = true,
            photoUri = Uri.parse("android.resource://dev.piotrp.remnant/drawable/tramore"),
            remnantPhotoUri = Uri.parse("android.resource://dev.piotrp.remnant/drawable/tramore")
        )
    }
}