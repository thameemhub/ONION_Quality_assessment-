package com.ontest.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ontest.app.data.model.GradingReport
import com.ontest.app.ui.screens.CalibrationScreen
import com.ontest.app.ui.screens.CaptureScreen
import com.ontest.app.ui.screens.HistoryScreen
import com.ontest.app.ui.screens.HomeScreen
import com.ontest.app.ui.screens.MarketplaceScreen
import com.ontest.app.ui.screens.OnboardingScreen
import com.ontest.app.ui.screens.PermissionScreen
import com.ontest.app.ui.screens.ReputationScreen
import com.ontest.app.ui.screens.ResultScreen
import com.ontest.app.ui.screens.SplashScreen
import com.ontest.app.viewmodel.GradingViewModel
import com.ontest.app.viewmodel.OnboardingViewModel

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val HISTORY = "history"
    const val MARKETPLACE = "marketplace"
    const val REPUTATION = "reputation"
    const val PERMISSION = "permission"
    const val CALIBRATION = "calibration"
    const val CAPTURE = "capture"
    const val RESULT = "result/{reportId}"
    const val RESULT_LIVE = "result_live"

    fun result(reportId: Long) = "result/$reportId"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel = viewModel(),
    gradingViewModel: GradingViewModel = viewModel(),
    hasSeenCalibrationCoach: Boolean,
    onDismissCalibrationCoach: () -> Unit
) {
    val hasCompleted by onboardingViewModel.hasCompletedOnboarding.collectAsState()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigate = {
                    val dest = if (hasCompleted) Routes.HOME else Routes.ONBOARDING
                    navController.navigate(dest) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onComplete = {
                    onboardingViewModel.completeOnboarding()
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onStartGrading = {
                    navController.navigate(Routes.CALIBRATION)
                },
                onReportClick = { reportId ->
                    navController.navigate(Routes.result(reportId))
                }
            )
        }

        composable(Routes.HISTORY) {
            HistoryScreen(
                onReportClick = { reportId ->
                    navController.navigate(Routes.result(reportId))
                }
            )
        }

        composable(Routes.MARKETPLACE) {
            MarketplaceScreen()
        }

        composable(Routes.REPUTATION) {
            ReputationScreen()
        }

        composable(Routes.PERMISSION) {
            PermissionScreen(
                onPermissionGranted = {
                    navController.navigate(Routes.CALIBRATION) {
                        popUpTo(Routes.PERMISSION) { inclusive = true }
                    }
                },
                onCancel = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.PERMISSION) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.CALIBRATION) {
            CalibrationScreen(
                onContinue = {
                    navController.navigate(Routes.CAPTURE) {
                        popUpTo(Routes.CALIBRATION) { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate(Routes.CAPTURE) {
                        popUpTo(Routes.CALIBRATION) { inclusive = true }
                    }
                },
                hasSeenCoach = hasSeenCalibrationCoach,
                onDismissCoach = onDismissCalibrationCoach
            )
        }

        composable(Routes.CAPTURE) {
            CaptureScreen(
                onResultReady = { reportId ->
                    navController.navigate(Routes.result(reportId)) {
                        popUpTo(Routes.HOME)
                    }
                },
                viewModel = gradingViewModel
            )
        }

        composable(Routes.RESULT_LIVE) {
            val uiState by gradingViewModel.uiState.collectAsState()
            ResultScreen(
                report = uiState.result,
                readOnly = false,
                showUrsPrompt = uiState.showUrsPrompt,
                onSave = {
                    gradingViewModel.saveReport()
                },
                onDone = {
                    gradingViewModel.resetState()
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onListNow = {
                    uiState.result?.let { report ->
                        gradingViewModel.createUserListing(report.id, report.ursPercent * 5)
                    }
                    gradingViewModel.dismissUrsPrompt()
                    navController.navigate(Routes.MARKETPLACE) {
                        popUpTo(Routes.HOME)
                    }
                },
                onDismissUrsPrompt = { gradingViewModel.dismissUrsPrompt() }
            )
        }

        composable(
            route = Routes.RESULT,
            arguments = listOf(navArgument("reportId") { type = NavType.LongType })
        ) { backStackEntry ->
            val reportId = backStackEntry.arguments?.getLong("reportId") ?: 0L
            var report by remember { mutableStateOf<GradingReport?>(null) }
            val uiState by gradingViewModel.uiState.collectAsState()

            LaunchedEffect(reportId) {
                // If we have a live result with this ID, use it
                val liveResult = uiState.result
                if (liveResult != null && (liveResult.id == reportId || reportId == 0L)) {
                    report = liveResult
                } else {
                    report = gradingViewModel.getReportById(reportId)
                }
            }

            val isLive = uiState.result != null && (uiState.result?.id == reportId || reportId == 0L)

            ResultScreen(
                report = report ?: uiState.result,
                readOnly = !isLive,
                showUrsPrompt = if (isLive) uiState.showUrsPrompt else false,
                onSave = {
                    if (isLive) {
                        gradingViewModel.saveReport { savedId ->
                            report = report?.copy(id = savedId)
                        }
                    }
                },
                onDone = {
                    gradingViewModel.resetState()
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onListNow = {
                    val r = report ?: uiState.result
                    r?.let {
                        gradingViewModel.createUserListing(it.id, it.ursPercent * 5)
                    }
                    gradingViewModel.dismissUrsPrompt()
                    navController.navigate(Routes.MARKETPLACE) {
                        popUpTo(Routes.HOME)
                    }
                },
                onDismissUrsPrompt = { gradingViewModel.dismissUrsPrompt() }
            )
        }
    }
}
