package com.kurir.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Courier(
    val id: String,
    val name: String,
    val phone: String,
    val vehiclePlate: String,
    val rating: Double,
    val activeOrderId: String? = null
)
