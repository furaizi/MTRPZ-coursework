package com.example.urlshortenerandroid.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.urlshortenerandroid.presentation.ui.DetailsScreen
import com.example.urlshortenerandroid.presentation.ui.ShortenScreen
import com.example.urlshortenerandroid.presentation.ui.StatsScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = Destinations.Shorten.name) {
        composable(Destinations.Shorten.name)   { ShortenScreen { id ->
            navController.navigate("${Destinations.Details.name}/$id") } }

        composable(
            route = "${Destinations.Details.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val linkId = backStackEntry.arguments!!.getString("id")!!
            DetailsScreen(linkId,
                onStatsClick = { navController.navigate("${Destinations.Stats.name}/$linkId") })
        }

        composable(
            route = "${Destinations.Stats.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { StatsScreen(it.arguments!!.getString("id")!!) }
    }
}