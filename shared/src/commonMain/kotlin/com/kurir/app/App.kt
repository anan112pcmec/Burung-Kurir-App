package com.kurir.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kurir.app.data.FakeOrderRepository
import com.kurir.app.data.OrderRepository
import com.kurir.app.model.Order
import com.kurir.app.ui.screen.LoginScreen
import com.kurir.app.ui.screen.OrderDetailScreen
import com.kurir.app.ui.screen.OrderListScreen
import com.kurir.app.ui.screen.Screen
import com.kurir.app.ui.screen.TrackingScreen
import com.kurir.app.ui.theme.CourierAppTheme

/**
 * Entry point Compose Multiplatform. Dipanggil dari MainActivity (Android)
 * dan dari MainViewController (iOS).
 *
 * Repository di-instantiate di sini pakai FakeOrderRepository (data dummy
 * in-memory) supaya starter template ini langsung bisa dijalankan tanpa
 * backend. Ganti dengan implementasi asli yang manggil API begitu backend
 * lo siap.
 */
@Composable
fun App(repository: OrderRepository = remember { FakeOrderRepository() }) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }
    var lastKnownOrder by remember { mutableStateOf<Order?>(null) }

    CourierAppTheme {
        when (val screen = currentScreen) {
            is Screen.Login -> LoginScreen(
                onLoginSuccess = { currentScreen = Screen.OrderList }
            )

            is Screen.OrderList -> OrderListScreen(
                repository = repository,
                onOrderClick = { order ->
                    lastKnownOrder = order
                    currentScreen = Screen.OrderDetail(order.id)
                }
            )

            is Screen.OrderDetail -> OrderDetailScreen(
                orderId = screen.orderId,
                repository = repository,
                onBack = { currentScreen = Screen.OrderList },
                onTrackClick = { order ->
                    lastKnownOrder = order
                    currentScreen = Screen.Tracking(order.id)
                }
            )

            is Screen.Tracking -> TrackingScreen(
                orderId = screen.orderId,
                currentStatus = lastKnownOrder?.status
                    ?: com.kurir.app.model.OrderStatus.ON_DELIVERY,
                onBack = { currentScreen = Screen.OrderDetail(screen.orderId) }
            )
        }
    }
}
