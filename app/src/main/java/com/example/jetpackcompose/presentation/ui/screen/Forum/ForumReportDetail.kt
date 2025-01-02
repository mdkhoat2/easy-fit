import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.ui.theme.AppTypo
import com.example.jetpackcompose.ui.theme.Colors

@Composable
fun ReportDetailScreen(
    postContent: String,
    timestamp: String,
    onReassessRequest: () -> Unit,
    onBackClick: () -> Unit
) {
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
                text = "Report Detail",
                style = AppTypo.titleLarge,
                color = Colors.White,
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

        }
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Hi, we want to tell you that one of our mods took down a post of yours. See your post:",
                style = AppTypo.bodyMedium,
                color = Colors.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.DarkGrey, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = postContent,
                    style = AppTypo.bodyMedium,
                    color = Colors.Green
                )
            }
            Text(
                text = timestamp,
                style = AppTypo.bodySmall,
                color = Colors.LightGrey,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp, start = 8.dp)
            )
            Text(
                text = "Posts that should be removed include those containing harassment, hate speech, spam, explicit content, illegal activities, or harmful misinformation.",
                style = AppTypo.bodyMedium,
                color = Colors.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "If you believe your post doesn’t fall under these categories, you can request a re-assessment, and another mod will review it.",
                style = AppTypo.bodyMedium,
                color = Colors.White
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onReassessRequest,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.Blue.copy(alpha = 0.2f),
                contentColor = Colors.Blue,
            )
        ) {
            Text(
                text = "I BELIEVE THIS IS A MISTAKE",
                style = AppTypo.bodyMedium,
                color = Colors.Blue,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReportDetailScreen() {
    ReportDetailScreen(
        postContent = "You're all so dumb for thinking this app works! Only idiots would use something so broken. Go use a real app, losers! 😂",
        timestamp = "20:03:02 Dec 18th",
        onReassessRequest = {},
        onBackClick = {} /* finish activity */
    )
}