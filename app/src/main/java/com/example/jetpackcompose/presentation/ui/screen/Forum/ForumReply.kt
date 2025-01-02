package com.example.jetpackcompose.presentation.ui.screen.Forum

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldColors
import com.example.jetpackcompose.ui.theme.JetPackComposeTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.ui.theme.AppTypo
import com.example.jetpackcompose.ui.theme.Colors

@Composable
fun ForumReplyScreen(onBackClick: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

        androidx.compose.foundation.layout.Column(
            modifier = androidx.compose.ui.Modifier
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
                    text = "Post Detail",
                    style = AppTypo.titleLarge,
                    color = Colors.White,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )

                // End Component
                Row(
                    modifier = Modifier
                        .clickable { showDialog = true }
                        .border(
                            width = 0.5.dp,
                            color = Colors.LightGrey,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .padding(end = 8.dp, start = 4.dp, top = 4.dp, bottom = 4.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Report",
                        tint = Colors.Orange,
                        modifier = Modifier.padding(start = 4.dp).size(12.dp,12.dp).align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "Report",
                        style = AppTypo.bodySmall,
                        color = Colors.Orange,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }


            if (showDialog) {
                UnsupportedFeatureDialog(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false }
                )
            }


            Spacer(modifier = Modifier.height(16.dp))
            // Post Section
            androidx.compose.foundation.layout.Column(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "Akshan Nethun",
                    style = AppTypo.titleSmall,
                    color = Colors.Green
                )
                androidx.compose.material3.Text(
                    text = "Is it just me or are push-ups really hard to get in the correct form?? Like I’ve seen instruction on YouTube but I can never get in that form effortlessly...",
                    style = AppTypo.bodyMedium,
                    color = Colors.White,
                    modifier = androidx.compose.ui.Modifier.padding(top = 4.dp)
                )
            }

            Text(text = "Reply",
                style = AppTypo.titleSmall,
                color = Colors.LightGrey)

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(thickness = 1.dp, color = Colors.LightGrey)

            // Replies Section
            androidx.compose.foundation.layout.Column(
                modifier = androidx.compose.ui.Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                ReplyItem(
                    username = "NewUser3628",
                    message = "Push-ups can definitely be tricky! Try breaking them down into smaller steps, like starting with knee push-ups to build strength and focusing on keeping your body in a straight line. Practice makes perfect—don’t give up! 💪"
                )
                ReplyItem(
                    username = "FitCoachMikePenson",
                    message = "What helped me was doing incline push-ups on a sturdy surface like a table or bench. It’s all about gradual progression!"
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            ReplyInputField { replyText ->
                // Handle the reply submission
                println("User replied: $replyText")
            }
        }

}

@Composable
fun ReplyInputField(onSendClick: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp,24.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = {
                Text(
                    text = "Write a reply...",
                    style = AppTypo.bodyMedium,
                    color = Colors.LightGrey
                )
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            textStyle = AppTypo.bodyMedium,
            shape = RoundedCornerShape(32.dp),
            maxLines = 4,
            singleLine = false,
            colors = OutlinedTextFieldDefaults.colors(
                //setting the text field background when it is focused
                focusedTextColor = Colors.Blue,
                unfocusedTextColor = Colors.LightGrey,
                focusedBorderColor = Colors.Blue,
                unfocusedBorderColor = Colors.LightGrey,
                cursorColor = Colors.Blue
            )
        )

        IconButton(
            onClick = {
                onSendClick(text)
                text = "" // Clear the text field after sending
            },
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = Colors.DarkGrey,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = Colors.Blue
            )
        }
    }
}


@Composable
fun ReplyItem(username: String, message: String) {
    androidx.compose.foundation.layout.Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        androidx.compose.material3.Text(
            text = username,
            style = AppTypo.titleSmall,
            color = Colors.Green
        )
        androidx.compose.material3.Text(
            text = message,
            style = AppTypo.bodyMedium,
            color = Colors.White,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
@Preview
fun ForumReplyScreenPreview(){
    ForumReplyScreen(onBackClick = { /*TODO*/ })
}