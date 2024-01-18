package com.example.feature.main.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feature.main.ui.MainDetailScreen
import com.example.feature.main.ui.MainScreen

@Composable
fun MainNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Route.Main
    ) {
        mainNavGraph {
            navController.navigate(Route.getMainDetailRoute(it))
        }
        mainDetailNavGraph {
            navController.popBackStack()
        }
    }
}

fun NavGraphBuilder.mainNavGraph(
    onItemClick: (String) -> Unit,
) {
    composable(route = Route.Main) {
        MainScreen(onItemClick = onItemClick)
    }
}

fun NavGraphBuilder.mainDetailNavGraph(
    onBackListener: () -> Unit,
) {
    composable(
        route = Route.MainDetail,
        arguments = listOf(navArgument(Route.id) { type = NavType.StringType })
    ) {
        MainDetailScreen(onBackListener = onBackListener)
    }
}

object Route {
    const val Main = "main"
    const val id = "id"
    const val MainDetail = "main/{$id}"
    fun getMainDetailRoute(id:String)  = "main/${id}"
}