package com.kurir.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kurir.app.data.OrderRepository
import com.kurir.app.model.Order
import com.kurir.app.model.OrderStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    orderId: String,
    repository: OrderRepository,
    onBack: () -> Unit,
    onTrackClick: (Order) -> Unit
) {
    var order by remember { mutableStateOf<Order?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(orderId) {
        order = repository.getOrderById(orderId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detail Pesanan") })
        }
    ) { padding ->
        val current = order
        if (current == null) {
            Text("Memuat...", modifier = Modifier.padding(padding).padding(16.dp))
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(current.id, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(4.dp))
            StatusBadge(status = current.status)
            Spacer(Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    LabeledText("Ambil di", current.pickupAddress)
                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(8.dp))
                    LabeledText("Antar ke", current.dropAddress)
                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(8.dp))
                    LabeledText("Penerima", "${current.receiverName} (${current.receiverPhone})")
                    Spacer(Modifier.height(8.dp))
                    LabeledText("Barang", current.itemDescription)
                    Spacer(Modifier.height(8.dp))
                    LabeledText("Jarak", "${current.distanceKm} km · Rp${current.price}")
                }
            }

            Spacer(Modifier.height(24.dp))

            when (current.status) {
                OrderStatus.PENDING -> Button(
                    onClick = {
                        scope.launch {
                            repository.updateStatus(current.id, OrderStatus.PICKED_UP)
                            order = repository.getOrderById(current.id)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Ambil Barang") }

                OrderStatus.PICKED_UP -> Button(
                    onClick = {
                        scope.launch {
                            repository.updateStatus(current.id, OrderStatus.ON_DELIVERY)
                            order = repository.getOrderById(current.id)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Mulai Antar") }

                OrderStatus.ON_DELIVERY -> Column {
                    Button(
                        onClick = { onTrackClick(current) },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Lihat Tracking") }
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                repository.updateStatus(current.id, OrderStatus.DELIVERED)
                                order = repository.getOrderById(current.id)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Selesai / Terkirim") }
                }

                OrderStatus.DELIVERED -> Text(
                    "Pesanan sudah terkirim ✅",
                    style = MaterialTheme.typography.bodyLarge
                )

                OrderStatus.CANCELLED -> Text(
                    "Pesanan dibatalkan",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun LabeledText(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelLarge)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}
