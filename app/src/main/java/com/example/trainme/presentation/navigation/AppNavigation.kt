// com/example/trainme/presentation/navigation/AppNavigation.kt
package com.example.trainme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trainme.ui.theme.Primary
import com.example.trainme.ui.theme.TextOnDark
import com.example.trainme.presentation.screens.HomeScreen
import com.example.trainme.presentation.screens.LoginScreen
import com.example.trainme.presentation.screens.RegistrationScreen
import com.example.trainme.presentation.screens.UpcomingLessonsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = { user ->
                    navController.navigate("home/${user.id}") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegistrationScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegistrationSuccess = { user ->
                    navController.navigate("home/${user.id}") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "home/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            HomeScreen(
                userId = userId,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home/{userId}") { inclusive = true }
                    }
                },
                onNavigateToUpcomingLessons = {
                    navController.navigate("upcoming_lessons/$userId")
                },
                onNavigateToProfile = {
                    navController.navigate("profile/$userId")
                },
                onNavigateToMyLessons = {
                    navController.navigate("my_lessons/$userId")
                }
            )
        }

        composable(
            route = "upcoming_lessons/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            UpcomingLessonsScreen(
                userId = userId
            )
        }

        // Placeholder for future screens
        composable(
            route = "profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            // ProfileScreen(userId = userId)
            // For now, we're just showing a placeholder
            PlaceholderScreen(
                title = "Profile",
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "my_lessons/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            // MyLessonsScreen(userId = userId)
            // For now, we're just showing a placeholder
            PlaceholderScreen(
                title = "My Lessons",
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun PlaceholderScreen(title: String, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Simple top bar without using experimental TopAppBar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Primary)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextOnDark
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = TextOnDark
            )
        }

        // Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "This feature is coming soon!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}