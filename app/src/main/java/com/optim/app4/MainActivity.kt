package com.optim.app4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.optim.app4.ui.favorite.FavoriteScreen
import com.optim.app4.ui.hisotry.HistoryScreen
import com.optim.app4.ui.home.HomeScreen
import com.optim.app4.ui.profile.ProfileScreen
import com.optim.app4.ui.route.BottomNavScreen
import com.optim.app4.ui.theme.App4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App4Theme {
                MainView()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainView() {

    val bottomNavItems = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.History,
        BottomNavScreen.Favorite,
        BottomNavScreen.Profile
    )
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(screen.label) },
                        icon = { screen.icon() }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavScreen.Favorite.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.Home.route) { HomeScreen() }
            composable(BottomNavScreen.History.route) { HistoryScreen() }
            composable(BottomNavScreen.Favorite.route) { FavoriteScreen() }
            composable(BottomNavScreen.Profile.route) { ProfileScreen() }
        }
    }
}






