package com.mo.assistant.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mo.assistant.ui.screens.ChatScreen
import com.mo.assistant.ui.screens.HomeScreen
import com.mo.assistant.ui.screens.SettingsScreen

/**
 * Top-level navigation graph for MO app.
 */
@Composable
fun MoApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToChat = { navController.navigate("chat") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable("chat") {
            ChatScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}