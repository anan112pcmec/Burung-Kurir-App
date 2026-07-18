package com.kurir.app.ui.screen

sealed class Screen {
    data object Login : Screen()
    data object OrderList : Screen()
    data class OrderDetail(val orderId: String) : Screen()
    data class Tracking(val orderId: String) : Screen()
}
