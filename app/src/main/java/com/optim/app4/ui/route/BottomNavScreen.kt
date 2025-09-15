package com.optim.app4.ui.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

sealed class BottomNavScreen(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
) {
    object Home : BottomNavScreen(
        route = "home",
        label = "Home",
        icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
    )

    object History : BottomNavScreen(
        route = "history",
        label = "History",
        icon = { Icon(Icons.Default.DateRange, contentDescription = "History") }
    )

    object Favorite : BottomNavScreen(
        route = "favorite",
        label = "Favorite",
        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorite") }
    )

    object Profile : BottomNavScreen(
        route = "profile",
        label = "Profile",
        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }
    )
}