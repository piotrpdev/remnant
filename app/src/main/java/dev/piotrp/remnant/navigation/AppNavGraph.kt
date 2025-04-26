package dev.piotrp.remnant.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.piotrp.remnant.ui.screens.about.AboutScreen
import dev.piotrp.remnant.ui.screens.details.DetailsScreen
import dev.piotrp.remnant.ui.screens.reminisce.ReminisceScreen
import dev.piotrp.remnant.ui.screens.home.HomeScreen
import dev.piotrp.remnant.ui.screens.login.LoginScreen
import dev.piotrp.remnant.ui.screens.map.MapScreen
import dev.piotrp.remnant.ui.screens.profile.ProfileScreen
import dev.piotrp.remnant.ui.screens.register.RegisterScreen
import dev.piotrp.remnant.ui.screens.report.ReportScreen

@Composable
fun NavHostProvider(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: AppDestination,
    paddingValues: PaddingValues,
    permissions: Boolean,
    switchChecked: Boolean = true
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = Reminisce.route) {
            //call our 'Reminisce' Screen Here
            ReminisceScreen(modifier = modifier)
        }

        composable(route = Home.route) {
            //call our 'Home' Screen Here
            HomeScreen(modifier = modifier)
        }
        composable(route = Report.route) {
            //call our 'Report' Screen Here
            ReportScreen(modifier = modifier,
                onClickRemnantDetails = {
                    remnantId : String ->
                    navController.navigateToRemnantDetails(remnantId)
                },
                switchChecked = switchChecked
            )
        }
        composable(route = About.route) {
            //call our 'About' Screen Here
            AboutScreen(modifier = modifier)
        }

        composable(route = Login.route) {
            //call our 'Login' Screen Here
            LoginScreen(
                navController = navController,
                onLogin = { navController.popBackStack() }
            )
        }

        composable(route = Register.route) {
            //call our 'Register' Screen Here
            RegisterScreen(
                navController = navController,
                onRegister = { navController.popBackStack() }
            )
        }

        composable(
            route = Details.route,
            arguments = Details.arguments
        )
        { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString(Details.idArg)
            if (id != null) {
                DetailsScreen()
            }
        }

        composable(route = Profile.route) {
            ProfileScreen(
                onSignOut = {
                    navController.popBackStack()
                    navController.navigate(Login.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                },
            )
        }

        composable(route = Map.route) {
            //call our 'Map' Screen Here
            MapScreen(permissions = permissions, switchChecked = switchChecked)
        }
    }
}

private fun NavHostController.navigateToRemnantDetails(remnantId: String) {
    this.navigate("details/$remnantId")
}

