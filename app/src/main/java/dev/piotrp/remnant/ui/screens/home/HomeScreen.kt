package dev.piotrp.remnant.ui.screens.home

import android.content.res.Configuration
import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dev.piotrp.remnant.navigation.Login
import dev.piotrp.remnant.navigation.NavHostProvider
import dev.piotrp.remnant.navigation.Report
import dev.piotrp.remnant.navigation.allDestinations
import dev.piotrp.remnant.navigation.bottomAppBarDestinations
import dev.piotrp.remnant.navigation.userSignedOutDestinations
import dev.piotrp.remnant.ui.components.general.BottomAppBarProvider
import dev.piotrp.remnant.ui.components.general.TopAppBarProvider
import dev.piotrp.remnant.ui.screens.map.MapViewModel
import dev.piotrp.remnant.ui.theme.RemnantTheme
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               homeViewModel: HomeViewModel = hiltViewModel(),
               mapViewModel: MapViewModel = hiltViewModel(),
               navController: NavHostController = rememberNavController(),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen =
        allDestinations.find { it.route == currentDestination?.route } ?: Login

    var startScreen = currentBottomScreen
    val currentUser = homeViewModel.currentUser
    val isActiveSession = homeViewModel.isAuthenticated()
    val userEmail = if (isActiveSession) currentUser?.email else ""
    val userName = if (isActiveSession) currentUser?.displayName else ""
    val userDestinations = if (!isActiveSession) userSignedOutDestinations else bottomAppBarDestinations

    val locationPermissions = rememberMultiplePermissionsState(
            permissions = listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                        )
                )

    if (isActiveSession) {
            startScreen = Report
            LaunchedEffect(locationPermissions.allPermissionsGranted) {
                    locationPermissions.launchMultiplePermissionRequest()
                    if (locationPermissions.allPermissionsGranted) {
                            mapViewModel.setPermissions(true)
                            mapViewModel.getLocationUpdates()
                        }
                }
        }

    Timber.i("HOME LAT/LNG PERMISSIONS ${mapViewModel.hasPermissions.collectAsState().value} ")

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBarProvider(
            navController = navController,
            currentScreen = currentBottomScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            email = userEmail!!,
            name = userName!!,
        ) { navController.navigateUp() }
        },
        content = { paddingValues ->
            NavHostProvider(
                modifier = modifier,
                navController = navController,
                startDestination = startScreen,
                paddingValues = paddingValues,
                permissions = mapViewModel
                    .hasPermissions
                    .collectAsState().value
            )
        },
        bottomBar = {
            BottomAppBarProvider(
                navController,
                currentScreen = currentBottomScreen,
                userDestinations
            )
        }
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyAppPreview() {
    RemnantTheme {
        HomeScreen(modifier = Modifier)
    }
}