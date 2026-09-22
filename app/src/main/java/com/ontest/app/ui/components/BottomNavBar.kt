package com.ontest.app.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ontest.app.ui.theme.CamelBrown
import com.ontest.app.ui.theme.TextTertiary
import com.ontest.app.ui.theme.VioletPrimary
import com.ontest.app.ui.theme.White

data class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem("history", "History", Icons.Filled.History, Icons.Outlined.History),
    BottomNavItem("marketplace", "Marketplace", Icons.Filled.Storefront, Icons.Outlined.Storefront),
    BottomNavItem("reputation", "Reputation", Icons.Filled.VerifiedUser, Icons.Outlined.VerifiedUser)
)

@Composable
fun OnTestBottomBar(
    currentRoute: String?,
    onNavItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = White,
        tonalElevation = 8.dp,
        modifier = modifier.height(80.dp)
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavItemClick(item.route) },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = androidx.compose.material3.MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VioletPrimary,
                    selectedTextColor = VioletPrimary,
                    unselectedIconColor = CamelBrown,
                    unselectedTextColor = TextTertiary,
                    indicatorColor = VioletPrimary.copy(alpha = 0.12f)
                )
            )
        }
    }
}

@Composable
fun GradingFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = VioletPrimary,
        contentColor = White,
        elevation = FloatingActionButtonDefaults.elevation(6.dp),
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.CameraAlt,
            contentDescription = "Start New Grading",
            modifier = Modifier.size(28.dp)
        )
    }
}
