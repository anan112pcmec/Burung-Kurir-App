package com.kurir.app.data

import com.kurir.app.model.Order
import com.kurir.app.model.OrderStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface OrderRepository {
    val orders: Flow<List<Order>>
    suspend fun refresh()
    suspend fun getOrderById(id: String): Order?
    suspend fun updateStatus(id: String, status: OrderStatus)
}

/**
 * Implementasi dummy/in-memory. Ganti nanti dengan implementasi yang
 * manggil REST API / Ktor client ke backend lo (misal ke Backend-1).
 */
class FakeOrderRepository : OrderRepository {

    private val state = MutableStateFlow(sampleOrders())

    override val orders = state.asStateFlow()

    override suspend fun refresh() {
        delay(400) // simulasi network call
        // no-op untuk fake repo, di implementasi asli ini fetch dari API
    }

    override suspend fun getOrderById(id: String): Order? {
        return state.value.firstOrNull { it.id == id }
    }

    override suspend fun updateStatus(id: String, status: OrderStatus) {
        delay(200)
        state.value = state.value.map {
            if (it.id == id) it.copy(status = status) else it
        }
    }

    private fun sampleOrders(): List<Order> = listOf(
        Order(
            id = "ORD-001",
            pickupAddress = "Jl. Sudirman No. 12, Jakarta Selatan",
            dropAddress = "Jl. Kemang Raya No. 5, Jakarta Selatan",
            receiverName = "Budi Santoso",
            receiverPhone = "0812-3456-7890",
            itemDescription = "Dokumen kontrak (amplop)",
            distanceKm = 4.2,
            price = 18000,
            status = OrderStatus.PENDING,
            createdAtEpochMillis = 1_726_000_000_000
        ),
        Order(
            id = "ORD-002",
            pickupAddress = "Ruko Cikarang Baru Blok C2",
            dropAddress = "Perumahan Delta Silicon 3",
            receiverName = "Siti Aminah",
            receiverPhone = "0813-2233-4455",
            itemDescription = "Paket elektronik kecil",
            distanceKm = 7.8,
            price = 25000,
            status = OrderStatus.ON_DELIVERY,
            createdAtEpochMillis = 1_726_010_000_000
        ),
        Order(
            id = "ORD-003",
            pickupAddress = "Grand Wisata Bekasi Cluster Melati",
            dropAddress = "Summarecon Bekasi Mall",
            receiverName = "Andi Wijaya",
            receiverPhone = "0821-9988-7766",
            itemDescription = "Makanan (perlu cepat)",
            distanceKm = 2.1,
            price = 12000,
            status = OrderStatus.DELIVERED,
            createdAtEpochMillis = 1_725_990_000_000
        )
    )
}
