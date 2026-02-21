package com.backpain.exercise.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.backpain.exercise.presentation.screens.home.HomeScreen
import com.backpain.exercise.presentation.screens.exercise.ExerciseSetDetailScreen

@Composable
fun BackPainExerciseNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable(Screen.ExerciseSetDetail.route) { backStackEntry ->
            val setId = backStackEntry.arguments?.getString("setId") ?: ""
            ExerciseSetDetailScreen(
                setId = setId,
                navController = navController
            )
        }
        
        // TODO: Add ExerciseDetail screen when implemented
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ExerciseSetDetail : Screen("exercise_set_detail/{setId}") {
        fun createRoute(setId: String) = "exercise_set_detail/$setId"
    }
    object ExerciseDetail : Screen("exercise_detail/{exerciseId}") {
        fun createRoute(exerciseId: String) = "exercise_detail/$exerciseId"
    }
}
