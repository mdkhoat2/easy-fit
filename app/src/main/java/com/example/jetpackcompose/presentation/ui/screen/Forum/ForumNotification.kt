package com.example.jetpackcompose.presentation.ui.screen.Forum

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.ui.theme.AppTypo
import com.example.jetpackcompose.ui.theme.Colors
import com.example.jetpackcompose.data.dataModel.ForumNotification

@Composable
fun ForumNotifications(onBackClick: () -> Unit) {
    val notifications = listOf(
        Notification(
            type = 1, // Reply to post
            subject = "FitCoachMikePenson",
            action = "replied to your post.",
            time = "20m"
        ),
        Notification(
            type = 1, // Reply to post
            subject = "NewUser3628",
            action = "replied to your post.",
            time = "11h"
        ),
        Notification(
            type = 2, // Admin action
            subject = "ModThienDia⭐",
            action = "took down your post.",
            time = "20h"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Black)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            // Start Component
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Return",
                tint = Colors.Blue,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() }
                    .align(Alignment.CenterStart)

            )

            // Center Component
            Text(
                text = "Notifications",
                style = AppTypo.titleLarge,
                color = Colors.White,
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(notifications) { notification ->
                NotificationItem(notification)
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    HorizontalDivider(thickness = 1.dp, color = Colors.LightGrey)
    Spacer(modifier = Modifier.height(4.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Text(
                    text = notification.subject,
                    style = AppTypo.titleSmall,
                    color = if (notification.type == 1) Colors.Green else Colors.Orange
                )
                Text(
                    text = notification.time,
                    style = AppTypo.bodyMedium,
                    color = Colors.LightGrey
                )
            }

            Text(
                text = notification.action,
                style = AppTypo.bodyMedium,
                color = Colors.White
            )
        }

        Row(
            modifier = Modifier
                //.fillMaxHeight() // Ensures the Row fills the height of its parent
                .clickable { }
                .border(
                    width = 0.5.dp,
                    color = Colors.LightGrey,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(end = 8.dp, start = 4.dp, top = 4.dp, bottom = 4.dp),
        ) {
            Text(
                text = "View",
                style = AppTypo.titleSmall,
                color = Colors.Blue,
                modifier = Modifier
                    .padding(start = 6.dp, top = 2.dp, end = 2.dp, bottom = 2.dp)
                    .wrapContentHeight(Alignment.CenterVertically) // Ensures the text is centered vertically within itself
            )
        }

    }

    Spacer(modifier = Modifier.height(4.dp))
}

data class Notification(
    val type: Int,
    val subject: String,
    val action: String,
    val time: String
)

@Composable
@Preview
fun ForumNotificationsPreview() {
    ForumNotifications({/*finish activity*/})
}
