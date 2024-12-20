package com.example.jetpackcompose.presentation.ui.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.dataModel.Plan
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.GetPatchHistoryUseCase
import com.example.jetpackcompose.domain.usecase.GetPlanUseCase
import com.example.jetpackcompose.presentation.di.Routes
import com.example.jetpackcompose.presentation.ui.viewmodel.PlanViewModel
import com.example.jetpackcompose.util.getLastDate
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun PlanScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    workoutDatabase: WorkoutDatabase
) {
    val lastDateState = produceState<String?>(initialValue = null) {
        value = getLastDate(context) // This runs in a coroutine
    }

    // If the last date is not yet loaded, show a loading indicator
    if (lastDateState.value == null) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        return
    }

    // Initialize the ViewModel after lastDate is available
    val planViewModel = remember {
        val getPatchHistoryUseCase = GetPatchHistoryUseCase(
            WorkoutRepositoryImp(workoutDatabase, context)
        )
        val getPlanUseCase = GetPlanUseCase(
            WorkoutRepositoryImp(workoutDatabase, context)
        )

        PlanViewModel(getPatchHistoryUseCase, getPlanUseCase, lastDateState.value!!)
    }

    val uiState by planViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        Text(
            text = "This Month",
            color = Color.White,
            fontSize = 24.sp
        )
        // Top Section: Circular Indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (uiState.isLoading)
                CircularProgressIndicator(
                    color = Color.White,strokeWidth = 4.dp,modifier = Modifier.size(72.dp)
                )
            else
            {
                CircularIndicator(
                    label = "Missed",
                    value = uiState.missedCnt.toFloat(),
                    maxValue = uiState.plan?.minSession?.toFloat() ?: 1f
                )
                CircularIndicator(
                    label = "Hour",
                    value = uiState.totalHour,
                    maxValue = uiState.plan?.minHour ?: 24f
                )
                CircularIndicator(
                    label = "Session",
                    value = uiState.totalSession.toFloat(),
                    maxValue = uiState.plan?.minSession?.toFloat() ?: 1f
                )
            }
        }

        // Goal Section
        GoalSection(uiState.dayType,uiState.plan)

        // Library Buttons
        LibrarySection(navController)
    }
}

@Composable
fun CircularIndicator(label: String, value: Float, maxValue: Float=1f) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background( colorFromResource(R.color.grid_color) , shape = RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { value / maxValue },
                color = getColorFromProgress(value / maxValue),
                strokeWidth = 4.dp,
                modifier = Modifier.size(72.dp)
            )
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.Black, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${(value / maxValue * 100).toInt()}%",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        // text with the value + label
        Text(
            text = "$value $label",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

fun getColorFromProgress(progress: Float): Color {
    return when (progress) {
        in 0.0..0.25 -> Color(0xFF9AC0D6)
        in 0.25..0.5 -> Color(0xFFD5FFAF)
        in 0.5..0.99 -> Color(0xFFD5FF5F) // light green
        in 0.99..1.0 -> Color(0xFF4FAF4F) // green
        else -> Color(0xFF4FAF4F)
    }
}


@Composable
fun GoalSection(
    dayType: List<Int> = List(42) { 0 },
    plan: Plan?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),

        ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Left Column: Goal Information
            Column(
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
            ) {
                Text(
                        text = "Goal",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Max miss days: ${plan?.maxMissDay}",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Min session: ${plan?.minSession}",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Min hour: ${plan?.minHour}",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            // Vertical Divider
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .width(1.dp) // Width of the divider
                    .height(150.dp) // Height of the divider
                    .background(Color.Gray) // Divider color
                    .align(Alignment.CenterVertically) // Align the divider to the center
            )
            Spacer(modifier = Modifier.width(16.dp))

            CustomCalendar(
                dayType = dayType
            )
        }
    }
}

@Composable
fun LibrarySection( navController: NavController
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LibraryButton("Guides & Notes", Modifier.weight(1f),
                onClick = { Log.d("PlanScreen", "Guides & Notes clicked") })
            LibraryButton("Workout library", Modifier.weight(1f),
                onClick = { /* Handle click here */ })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LibraryButton("Edit Plan", Modifier.weight(1f),
                onClick = { /* Handle click here */ },iconIm = Icons.Default.DateRange)
            LibraryButton("Create Workout ", Modifier.weight(1f),
                onClick = { navController.navigate(Routes.newWorkout) }, iconIm = Icons.Default.Add)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LibraryButton("Edit Goal", Modifier.weight(1f),
                onClick = { }, iconIm = Icons.Default.DateRange)
            Box(modifier = Modifier.weight(1f)) // Empty Box
        }
    }
}

@Composable
fun LibraryButton(title: String,modifier: Modifier = Modifier,
                  onClick: () -> Unit, // Added onClick parameter,
                  iconIm: ImageVector=Icons.AutoMirrored.Filled.ArrowForward
)
{
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = iconIm,
                contentDescription = null,
                tint = colorFromResource(R.color.primary_teal),
                modifier = Modifier.align(Alignment.End).clickable {  onClick() } // Icon aligned to the right side
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun CustomCalendar(
    dayType: List<Int> = List(42) { 0 }
) {
    val today = LocalDate.now()
    val currentMonth = today.plusMonths(0)
    val firstDayOfMonth = currentMonth.withDayOfMonth(1)
    val daysInMonth = YearMonth.from(firstDayOfMonth).lengthOfMonth()
    val dayOfWeekOffset = firstDayOfMonth.dayOfWeek.value - 1 // 1 = Monday, 7 = Sunday

    // Generate a list of days (including blanks for leading days)
    val daysList = buildList {
        repeat(dayOfWeekOffset) { add("") } // Blank days
        for (day in 1..daysInMonth) { add(day.toString()) } // Actual days
        val remainingBlanks = 7 - (size % 7) // Trailing blank spaces to fill the row
        if (remainingBlanks < 7) repeat(remainingBlanks) { add("") }
    }
    Box(
        modifier = Modifier.width(200.dp),
        contentAlignment = Alignment.Center // Centers the calendar horizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Month and Year Title
            Text(
                text = "${today.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${today.year}",
                fontSize = 12.sp,fontWeight = FontWeight.Bold,color = Color.White,
                modifier = Modifier.align(Alignment.End)
            )

            // Weekday Headers
            Row(modifier = Modifier) {
                listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Calendar Grid
            val rows = daysList.chunked(7)
            rows.forEach { week ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    week.forEach { day ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1.5f), // Reduced padding
                            contentAlignment = Alignment.Center
                        ) {
                            if (day.isNotBlank()) {
                                if (dayType[day.toInt() - 1] == 5 || dayType[day.toInt() - 1] == 6) {
                                    Box(modifier = Modifier.size(17.dp).background(Color(0xEEFFFFFF),
                                        shape = CircleShape),
                                        contentAlignment = Alignment.Center){
                                        Box(modifier = Modifier
                                            .size(15.dp).background(
                                                colorFromResource(R.color.bottom_bar_background)
                                                ,shape = CircleShape)
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .size(14.dp) // Consistent circle size
                                        .background(
                                            when (dayType[day.toInt() - 1]) {
                                                1,6 -> colorFromResource(R.color.grid_color)
                                                2 -> colorFromResource(R.color.primary_green)
                                                3 -> colorFromResource(R.color.primary_orange)
                                                4 -> colorFromResource(R.color.primary_teal)
                                                5 -> colorFromResource(R.color.success_green)
                                                else -> Color.Transparent
                                            },
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "",
                                        color = when (dayType[day.toInt() - 1]) {
                                            1 -> Color.White
                                            2, 3, 4, 5 -> Color.Black
                                            else -> Color.White
                                        },
                                        fontSize = 10.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }

                            }
                        }
                    }
                }
            }
    }
    }
}

