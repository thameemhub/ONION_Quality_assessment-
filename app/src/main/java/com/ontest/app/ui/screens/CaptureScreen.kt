package com.ontest.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.camera.core.ImageCapture
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ontest.app.camera.CameraPreview
import com.ontest.app.ui.theme.VioletDark
import com.ontest.app.ui.theme.VioletPrimary
import com.ontest.app.ui.theme.White
import com.ontest.app.viewmodel.GradingViewModel

@Composable
fun CaptureScreen(
    onResultReady: (Long) -> Unit,
    viewModel: GradingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val imageCapture = remember { ImageCapture.Builder().build() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Camera preview (hidden during analysis)
        if (!uiState.isAnalyzing && uiState.result == null) {
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                imageCapture = imageCapture
            )

            // Dark overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.15f))
            )

            // Guide overlay
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Capture",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = White,
                    modifier = Modifier.padding(top = 48.dp)
                )

                // Guide frame
                Box(
                    modifier = Modifier
                        .size(260.dp)
                        .border(2.dp, VioletPrimary.copy(alpha = 0.7f), RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Center the onion basket",
                        style = MaterialTheme.typography.bodyMedium,
                        color = White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                }

                // Capture button
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 48.dp)
                ) {
                    val pulseTransition = rememberInfiniteTransition(label = "pulse")
                    val pulseScale by pulseTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.08f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(800),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "pulse_scale"
                    )

                    IconButton(
                        onClick = {
                            viewModel.startAnalysis()
                        },
                        modifier = Modifier
                            .size(72.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .background(VioletPrimary, CircleShape)
                            .border(4.dp, White.copy(alpha = 0.3f), CircleShape),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CameraAlt,
                            contentDescription = "Capture",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }

        // Analyzing state
        AnimatedVisibility(
            visible = uiState.isAnalyzing,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = VioletPrimary,
                        strokeWidth = 5.dp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Analyzing...",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = uiState.analyzeStep,
                        style = MaterialTheme.typography.bodyLarge,
                        color = VioletPrimary
                    )
                }
            }
        }

        // When result is ready, save and navigate
        if (uiState.result != null && !uiState.isAnalyzing) {
            viewModel.saveReport { reportId ->
                onResultReady(reportId)
            }
        }
    }
}
