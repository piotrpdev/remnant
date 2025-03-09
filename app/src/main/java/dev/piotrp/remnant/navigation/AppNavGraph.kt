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
import dev.piotrp.remnant.ui.screens.donate.DonateScreen
import dev.piotrp.remnant.ui.screens.report.ReportScreen

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Report.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = Donate.route) {
            //call our 'Donate' Screen Here
            DonateScreen(modifier = modifier)
        }
        composable(route = Report.route) {
            //call our 'Report' Screen Here
            ReportScreen(modifier = modifier,
                onClickDonationDetails = {
                    donationId : Int ->
                    navController.navigateToDonationDetails(donationId)
                },
            )
        }
        composable(route = About.route) {
            //call our 'About' Screen Here
            AboutScreen(modifier = modifier)
        }

        composable(
            route = Details.route,
            arguments = Details.arguments
        )
        { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Details.idArg)
            if (id != null) {
                DetailsScreen()
            }
        }
    }
}

private fun NavHostController.navigateToDonationDetails(donationId: Int) {
    this.navigate("details/$donationId")
}

