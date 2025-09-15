package com.optim.app4.ui.hisotry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.optim.app4.ui.elements.PremiumInfo


@Composable
fun HistoryScreen() {
    val iconMap = mapOf(
        "philosophy" to Icons.Default.AccountBox,
        "art" to Icons.Default.Favorite,
        "psychology" to Icons.Default.AccountCircle,
        "politics" to Icons.Default.Check,
    )

    val words = listOf(
        listOf("stoic", "philosophy","2025-08-24 19:51:07.60892+00"),
        listOf("eclectic", "art", "2025-08-23 19:51:07.60892+00"),
        listOf("pareidolia", "psychology", "2025-08-22 19:51:07.60892+00"),
        listOf("exteriorization", "politics", "2025-08-21 19:51:07.60892+00")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PremiumInfo("Subscribe to see the full history of wise words.")
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
        ) {
            items(words) { item ->
                val wordText = item[0]
                val category = item[1]
                val date = item[2]
                val icon: ImageVector = iconMap[category] ?: Icons.Default.Build
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    ListItem(
                        headlineContent = {
                            Text(
                                text = wordText,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = date
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "Meaning of the word",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Favorite",
                                tint = Color.Black
                            )
                        }
                    )
                }
            }
        }
    }
}