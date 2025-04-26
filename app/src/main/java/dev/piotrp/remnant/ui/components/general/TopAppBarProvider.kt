package dev.piotrp.remnant.ui.components.general

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.piotrp.remnant.navigation.AppDestination
import dev.piotrp.remnant.navigation.Reminisce
import dev.piotrp.remnant.ui.theme.RemnantTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarProvider(
    navController: NavController,
    currentScreen: AppDestination,
    canNavigateBack: Boolean,
    email: String,
    name: String,
    switchChecked: Boolean = true,
    onCheckChange: (Boolean) -> Unit = {},
    navigateUp: () -> Unit = {},
)
{
    TopAppBar(
        title = {
            Column {
                Text(
                    text = currentScreen.label,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Row {
                    if (name.isNotEmpty())
                        Text(
                            text = name,
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    if (email.isNotEmpty())
                        Text(
                            text = " ($email)",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            else
                IconButton(onClick = {},
                    content = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                })

        },
        actions = {
            Row {
                Switch(
                    checked = switchChecked,
                    onCheckedChange = {
                        onCheckChange(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        checkedBorderColor = MaterialTheme.colorScheme.onSecondary,
                    )
                )
                DropDownMenu(navController = navController)
            }
        }
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopAppBarPreview() {
    RemnantTheme {
        TopAppBarProvider(
            navController = rememberNavController(),
            Reminisce,
            true,
            email = "dave@gmail.com",
            name = "userName!!"
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopAppBarOffPreview() {
    RemnantTheme {
        TopAppBarProvider(
            navController = rememberNavController(),
            Reminisce,
            true,
            email = "dave@gmail.com",
            name = "userName!!"
        )
    }
}