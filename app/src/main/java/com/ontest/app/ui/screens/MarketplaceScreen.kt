package com.ontest.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compost
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ontest.app.data.model.WasteListing
import com.ontest.app.ui.components.CoachMark
import com.ontest.app.ui.theme.CamelBrown
import com.ontest.app.ui.theme.CamelSurface
import com.ontest.app.ui.theme.GradeAGreen
import com.ontest.app.ui.theme.OffWhite
import com.ontest.app.ui.theme.TextPrimary
import com.ontest.app.ui.theme.TextSecondary
import com.ontest.app.ui.theme.TextTertiary
import com.ontest.app.ui.theme.VioletPrimary
import com.ontest.app.ui.theme.VioletSurface
import com.ontest.app.ui.theme.White
import com.ontest.app.viewmodel.MarketplaceViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(
    viewModel: MarketplaceViewModel = viewModel()
) {
    val allListings by viewModel.allListings.collectAsState()
    val userListings by viewModel.userListings.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val hasSeenCoach by viewModel.hasSeenMarketplaceCoach.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val filters = listOf("All", "Composting", "Biogas", "Fertilizer")
    val filteredListings = viewModel.getFilteredListings(allListings, selectedFilter)

    var selectedListing by remember { mutableStateOf<WasteListing?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Marketplace",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Connect with waste buyers nearby",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Filter chips with coach mark
            item {
                Box {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filters) { filter ->
                            FilterChip(
                                selected = selectedFilter == filter,
                                onClick = { viewModel.setFilter(filter) },
                                label = {
                                    Text(
                                        text = filter,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = VioletPrimary,
                                    selectedLabelColor = White,
                                    containerColor = White,
                                    labelColor = TextSecondary
                                )
                            )
                        }
                    }
                    CoachMark(
                        text = "Filter buyers by type — Composting, Biogas, or Fertilizer",
                        visible = !hasSeenCoach,
                        onDismiss = { viewModel.dismissMarketplaceCoach() },
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(top = 48.dp)
                    )
                }
            }

            // My Listings
            if (userListings.isNotEmpty()) {
                item {
                    Text(
                        text = "My Listings",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                items(userListings) { listing ->
                    ListingCard(
                        listing = listing,
                        onClick = { selectedListing = listing }
                    )
                }
            }

            // Buyer Listings
            item {
                Text(
                    text = "Buyer Listings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            items(filteredListings) { listing ->
                ListingCard(
                    listing = listing,
                    onClick = { selectedListing = listing }
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // Detail bottom sheet
        if (selectedListing != null) {
            ModalBottomSheet(
                onDismissRequest = { selectedListing = null },
                sheetState = rememberModalBottomSheetState(),
                containerColor = White
            ) {
                val listing = selectedListing!!
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(getBuyerColor(listing.buyerType)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = getBuyerIcon(listing.buyerType),
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = listing.buyerName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = listing.buyerType,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    DetailRow("Quantity", "${listing.quantityKg} kg")
                    DetailRow("Distance", "${listing.distanceKm} km away")
                    DetailRow("Status", listing.status.replaceFirstChar { it.uppercase() })
                    Spacer(modifier = Modifier.height(24.dp))

                    if (!listing.isUserListing) {
                        Button(
                            onClick = {
                                viewModel.requestContact(listing.id)
                                selectedListing = null
                                scope.launch {
                                    snackbarHostState.showSnackbar("Contact request sent (demo)")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = VioletPrimary,
                                contentColor = White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text("Request Contact", fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ListingCard(
    listing: WasteListing,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(getBuyerColor(listing.buyerType).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getBuyerIcon(listing.buyerType),
                    contentDescription = null,
                    tint = getBuyerColor(listing.buyerType),
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = listing.buyerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    text = "${listing.buyerType} · ${listing.quantityKg} kg",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
            if (listing.distanceKm > 0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = CamelBrown,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "${listing.distanceKm} km",
                        style = MaterialTheme.typography.labelSmall,
                        color = CamelBrown
                    )
                }
            }
            if (listing.isUserListing) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(GradeAGreen.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = listing.status.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelSmall,
                        color = GradeAGreen
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = TextTertiary)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = TextPrimary)
    }
}

private fun getBuyerIcon(type: String): ImageVector {
    return when (type) {
        "Composting" -> Icons.Filled.Compost
        "Biogas" -> Icons.Filled.ElectricBolt
        "Fertilizer" -> Icons.Filled.Eco
        else -> Icons.Filled.Storefront
    }
}

private fun getBuyerColor(type: String): androidx.compose.ui.graphics.Color {
    return when (type) {
        "Composting" -> GradeAGreen
        "Biogas" -> CamelBrown
        "Fertilizer" -> VioletPrimary
        else -> CamelBrown
    }
}
