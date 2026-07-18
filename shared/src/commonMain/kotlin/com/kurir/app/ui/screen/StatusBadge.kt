package com.kurir.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kurir.app.model.OrderStatus
import com.kurir.app.ui.theme.StatusCancelledBg
import com.kurir.app.ui.theme.StatusCancelledText
import com.kurir.app.ui.theme.StatusDeliveredBg
import com.kurir.app.ui.theme.StatusDeliveredText
import com.kurir.app.ui.theme.StatusOnDeliveryBg
import com.kurir.app.ui.theme.StatusOnDeliveryText
import com.kurir.app.ui.theme.StatusPendingBg
import com.kurir.app.ui.theme.StatusPendingText

@Composable
fun StatusBadge(status: OrderStatus, modifier: Modifier = Modifier) {
    val (bg, fg) = colorsFor(status)
    Text(
        text = status.label,
        color = fg,
        style = MaterialTheme.typography.labelLarge,
        modifier = modifier
            .background(bg, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

private fun colorsFor(status: OrderStatus): Pair<Color, Color> = when (status) {
    OrderStatus.PENDING -> StatusPendingBg to StatusPendingText
    OrderStatus.PICKED_UP -> StatusOnDeliveryBg to StatusOnDeliveryText
    OrderStatus.ON_DELIVERY -> StatusOnDeliveryBg to StatusOnDeliveryText
    OrderStatus.DELIVERED -> StatusDeliveredBg to StatusDeliveredText
    OrderStatus.CANCELLED -> StatusCancelledBg to StatusCancelledText
}
