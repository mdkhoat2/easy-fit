import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.R
import com.example.jetpackcompose.ui.theme.AppTypo
import com.example.jetpackcompose.ui.theme.Colors

@Composable
fun GuidanceScreen(onBackClick: () -> Unit = {}) {

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
                text = "Guidance",
                style = AppTypo.titleLarge,
                color = Colors.White,
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            listOf(
                GuidancePost(R.drawable.push_up_1, "Push Up", "You can start by performing push-ups against a wall, which is the easiest variation. Follow these steps:\n1. Start with Wall Push-Ups: Stand about arm’s length away from a wall, place your hands shoulder-width apart on the wall, and perform push-ups by leaning in and pushing back.\n2. Move to an Inclined Surface: Once comfortable, transition to an inclined surface like a table.\n3. Lower the Incline Gradually: As you get stronger, you reduce the angle over time.\n4. Standard Push-Ups: Finally, progress to standard push-ups on the floor."),
                GuidancePost(R.drawable.push_up_2, "Push Up", "Use correct form as shown to maximize effectiveness and avoid injury."),
                GuidancePost(R.drawable.crunges, "Crunges", "Crunges are great for core strength. Start with a small set and gradually increase repetitions."),
                GuidancePost(R.drawable.rope_jump, "Rope Jump", "Rope jumping improves cardiovascular health. Start with short durations and increase as you get comfortable.")
            ).forEach { post ->
                PostItem(post)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }

}

@Composable
fun PostItem(post: GuidancePost) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Colors.DarkGrey),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, Colors.LightGrey)
    ) {
        Image(
            painter = painterResource(id = post.imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = post.title,
                style = AppTypo.titleLarge,
                color = Colors.Green,
                textAlign = TextAlign.Start,
                modifier = Modifier.align(Alignment.Start).padding(start = 16.dp)
            )
            Text(
                text = post.description,
                style = AppTypo.bodyMedium,
                color = Colors.White,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewGuidanceScreen() {
    GuidanceScreen(onBackClick = {/*finish activity*/})
}

data class GuidancePost(
    val imageRes: Int,
    val title: String,
    val description: String
)
