package com.kurir.app.model

import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus(val label: String) {
    PENDING("Menunggu Kurir"),
    PICKED_UP("Barang Diambil"),
    ON_DELIVERY("Sedang Diantar"),
    DELIVERED("Terkirim"),
    CANCELLED("Dibatalkan")
}

@Serializable
data class Order(
    val id: String,
    val pickupAddress: String,
    val dropAddress: String,
    val receiverName: String,
    val receiverPhone: String,
    val itemDescription: String,
    val distanceKm: Double,
    val price: Long,
    val status: OrderStatus,
    val createdAtEpochMillis: Long
)
