package com.kurir.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.kurir.app.model.OrderStatus
import androidx.compose.ui.unit.dp

private val trackingSteps = listOf(
    OrderStatus.PENDING,
    OrderStatus.PICKED_UP,
    OrderStatus.ON_DELIVERY,
    OrderStatus.DELIVERED
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingScreen(
    orderId: String,
    currentStatus: OrderStatus,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Tracking $orderId") })
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {

            // Placeholder area peta — gantikan dengan integrasi Google Maps /
            // MapLibre / OpenStreetMap sesuai kebutuhan lo nanti.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.4f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Text("📍 Peta / lokasi kurir\n(integrasikan Maps SDK di sini)")
            }

            Spacer(Modifier.height(24.dp))
            Text("Status Pengiriman", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            trackingSteps.forEachIndexed { index, step ->
                val isDone = trackingSteps.indexOf(currentStatus) >= index
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(
                                if (isDone) MaterialTheme.colorScheme.primary
                                else Color(0xFFCCCCCC)
                            )
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = step.label,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isDone) MaterialTheme.colorScheme.onSurface
                                else Color(0xFF9E9E9E)
                    )
                }
                if (index != trackingSteps.lastIndex) {
                    Box(
                        modifier = Modifier
                            .padding(start = 7.dp)
                            .width(2.dp)
                            .height(20.dp)
                            .background(
                                if (isDone) MaterialTheme.colorScheme.primary
                                else Color(0xFFCCCCCC)
                            )
                    )
                }
            }
        }
    }
}
