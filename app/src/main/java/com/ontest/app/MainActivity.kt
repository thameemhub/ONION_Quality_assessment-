package com.ontest.app

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ontest.app.data.DataStoreManager
import com.ontest.app.navigation.NavGraph
import com.ontest.app.navigation.Routes
import com.ontest.app.ui.components.GradingFab
import com.ontest.app.ui.components.OnTestBottomBar
import com.ontest.app.ui.components.bottomNavItems
import com.ontest.app.ui.theme.OnTestTheme
import com.ontest.app.viewmodel.GradingViewModel
import com.ontest.app.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnTestTheme {
                OnTestApp()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnTestApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current
    val onboardingViewModel: OnboardingViewModel = viewModel()
    val gradingViewModel: GradingViewModel = viewModel()
    val dataStore = remember { DataStoreManager(context) }
    val scope = rememberCoroutineScope()

    val hasSeenCalibrationCoach by dataStore.hasSeenCalibrationCoach.collectAsState(initial = true)

    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)

    val bottomNavRoutes = bottomNavItems.map { it.route }
    val showBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                OnTestBottomBar(
                    currentRoute = currentRoute,
                    onNavItemClick = { route ->
                        navController.navigate(route) {
                            popUpTo(Routes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (showBottomBar) {
                GradingFab(
                    onClick = {
                        gradingViewModel.resetState()
                        if (cameraPermission.status.isGranted) {
                            navController.navigate(Routes.CALIBRATION)
                        } else {
                            navController.navigate(Routes.PERMISSION)
                        }
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavGraph(
                navController = navController,
                onboardingViewModel = onboardingViewModel,
                gradingViewModel = gradingViewModel,
                hasSeenCalibrationCoach = hasSeenCalibrationCoach,
                onDismissCalibrationCoach = {
                    scope.launch {
                        dataStore.setCalibrationCoachSeen()
                    }
                }
            )
        }
    }
}
