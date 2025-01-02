package com.example.jetpackcompose.presentation.ui.screen.Forum

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.R
import com.example.jetpackcompose.ui.theme.AppTypo
import com.example.jetpackcompose.ui.theme.Colors

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Khoa
 */

@Composable
fun ForumScreen() {
    // State for managing selected tab
    val tabs = listOf("All", "PushUp", "WeightLift", "Crunges", "RopeJump", "HandGrip", "Hoop")
    var selectedTab by remember { mutableStateOf("All") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Row for Notification and Search Icons
        Spacer(modifier = Modifier.height(48.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Colors.Blue,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showDialog = true }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Colors.Blue,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showDialog = true }
            )
        }

        if (showDialog) {
            UnsupportedFeatureDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = false }
            )
        }

        // Dynamic Filter Tab Bar
        FilterTabBar(
            tabs = tabs,
            selectedTab = selectedTab,
            onTabSelected = { tab -> selectedTab = tab }
        )

        // List of Forum Posts
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)) {
            val filteredPosts = getFilteredPosts(selectedTab) // Mock function to filter posts
            items(filteredPosts) { post ->
                HorizontalDivider(thickness = 1.dp, color = Colors.LightGrey)
                Spacer(modifier = Modifier.height(8.dp))

                ForumPostCard(
                    userName = post.userName,
                    content = post.content,
                    tag = post.tag,
                    initialUpvotes = post.upvotes,
                    comments = post.comments
                )

            }
        }
    }
}

@Composable
fun RoundedRectangleWithBorder() {
    Box(
        modifier = Modifier
            .size(200.dp) // Example size; it will wrap this space
            .border(32.dp, Color.Blue, RoundedCornerShape(16.dp)) // 32dp border
    )
}

val CustomOvalShape: Shape = GenericShape { size, _ ->
    addOval(
        Rect(
            left = 0f,
            top = 0f,
            right = size.width, // Use size.width for the right boundary
            bottom = size.height / 2 // Use size.height / 2 for the bottom boundary
        )
    )
}

// Mock function to filter posts
fun getFilteredPosts(selectedTab: String): List<Post> {
    // Replace this logic with actual filtering logic based on selectedTab
    return if (selectedTab == "All") {
        mockPosts // All posts
    } else {
        mockPosts.filter { post -> post.tag.contains(selectedTab, ignoreCase = true) }
    }
}

@Composable
fun UnsupportedFeatureDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "Feature Unavailable")
            },
            text = {
                Text(
                    text = "This feature is not supported yet. Please stay tuned for future updates!"
                )
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "OK")
                }
            }
        )
    }
}


@Composable
fun FilterTabBar(
    tabs: List<String>,
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(tabs) { tab ->
            FilterChip(
                selected = tab == selectedTab,
                onClick = { onTabSelected(tab) },
                label = {
                    Text(
                        text = tab,
                        style = AppTypo.displaySmall,
                        color = if (tab == selectedTab) Color.Black else Color.White
                    )
                },
                colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color.White,
                    containerColor = Colors.DarkGrey
                )
            )
        }
    }
}

data class Post(
    val userName: String,
    val content: String,
    val tag: String,
    val upvotes: Int,
    val comments: Int
)

val mockPosts = listOf(
    Post("Akshan Nethun", "Is it just me or are push-ups really hard to get in the correct form?? Like I’ve seen instruction on YouTube but I can never get in that form effortlessly...", "#pushup", 45, 186),
    Post("Jane Doe", "Crunches hurt my back!", "#crunges", -5, 92),
    Post("Emily Rose", "Weightlift make me feels so good!", "#weightlift", 20, 45)
)


@Composable
fun ForumPostCard(
    userName: String,
    content: String,
    tag: String,
    initialUpvotes: Int,
    comments: Int
) {
    // Local state to manage upvotes and user choice
    var upvotes by remember { mutableStateOf(initialUpvotes) }
    var userChoice by remember { mutableStateOf<String?>(null) } // "upvote", "downvote", or null

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color.Black
        )
    ) {
        Column(modifier = Modifier.padding(2.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // User Name
                Text(
                    text = userName,
                    style = AppTypo.titleSmall,
                    color = Colors.Green
                )

                // Spacer to push Tag to the end
                Spacer(modifier = Modifier.weight(1f))

                // Tag
                Text(
                    text = tag,
                    style = AppTypo.bodySmall,
                    color = Colors.Green,
                    modifier = Modifier.wrapContentSize(Alignment.Center) // Center vertically
                )
            }


            Spacer(modifier = Modifier.height(8.dp))



            // Post Content
            Text(
                text = content,
                style = AppTypo.bodyMedium,
                color = Color.White
            )


            Spacer(modifier = Modifier.height(8.dp))

            // Upvote, Downvote, and Comments Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(paddingValues = PaddingValues(horizontal = 0.dp, vertical = 8.dp)),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Control spacing between items
                ) {
                    // Upvote Button
//                    Row(
//                        modifier = Modifier
//                            .border(
//                                width = 0.5.dp,
//                                color = if (userChoice == "upvote") Colors.Blue else Colors.LightGrey,
//                                shape = RoundedCornerShape(32.dp)
//                            )
//                            .clickable {
//                                if (userChoice != "upvote") {
//                                    upvotes = initialUpvotes + 1
//                                    userChoice = "upvote"
//                                } else {
//                                    upvotes = initialUpvotes
//                                    userChoice = null
//                                }
//                            }
//                            .padding(end = 8.dp, start = 8.dp, top = 4.dp, bottom = 4.dp)
//                            .fillMaxHeight()
//                            .align(Alignment.CenterVertically)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.ThumbUp,
//                            contentDescription = "Upvote",
//                            tint = if (userChoice == "upvote") Colors.Blue else Colors.LightGrey
//                        )
//                    }
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = "Upvote",
                        tint = if (userChoice == "upvote") Colors.Blue else Colors.LightGrey,
                        modifier = Modifier.clickable {
                            if (userChoice != "upvote") {
                                upvotes = initialUpvotes + 1
                                userChoice = "upvote"
                            } else {
                                upvotes = initialUpvotes
                                userChoice = null
                            }
                        }
                    )

                    Text(
                        text = if (upvotes >= 0) "+$upvotes" else "$upvotes",
                        style = AppTypo.bodySmall,
                        color = if (userChoice == "upvote") Colors.Blue
                                else if (userChoice == "downvote") Colors.Orange
                                else Colors.LightGrey,
                        modifier = Modifier.align(Alignment.CenterVertically).wrapContentHeight().width(40.dp)
                    )

                    // Downvote Button
                    Icon(
                        painter = painterResource(id = R.drawable.dislike_24),
                        contentDescription = "Downvote",
                        tint = if (userChoice == "downvote") Colors.Orange else Colors.LightGrey,
                        modifier = Modifier.clickable {
                            if (userChoice != "downvote") {
                                upvotes = initialUpvotes - 1
                                userChoice = "downvote"
                            } else {
                                upvotes = initialUpvotes
                                userChoice = null
                            }
                        }
                    )
                }

                // Comments Section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable { /* Handle comments click */ }
                        .border(
                            width = 0.5.dp,
                            color = Colors.LightGrey,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .padding(end = 8.dp, start = 4.dp, top = 4.dp, bottom = 4.dp)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "🗨️",
                        style = AppTypo.bodySmall,
                        color = Colors.Blue,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Text(
                        text = "$comments",
                        style = AppTypo.bodySmall,
                        color = Colors.Blue,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}




@Composable
@Preview
fun ForumScreenPreview(){
    ForumScreen()
}