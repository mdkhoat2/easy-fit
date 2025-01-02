package com.example.jetpackcompose.presentation.ui.screen.Home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.GetPatchHistoryUseCase
import com.example.jetpackcompose.presentation.di.Routes
import com.example.jetpackcompose.presentation.di.StatData
import com.example.jetpackcompose.presentation.ui.screen.colorFromResource
import com.example.jetpackcompose.presentation.ui.viewmodel.HomeViewModel
import com.example.jetpackcompose.ui.theme.AppTypo

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun HomeScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    workoutDatabase: WorkoutDatabase
) {
    val homeViewModel = remember {
        val getPatchHistoryUseCase = GetPatchHistoryUseCase(
            WorkoutRepositoryImp(workoutDatabase, context)
        )
        HomeViewModel(getPatchHistoryUseCase)
    }

    val uiState by homeViewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        HistoryBar()

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        else{
            ProgressChart(uiState.points)
        }


        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally).size(50.dp)
            )
        }
        else{
            WeeklyStat(uiState.listData)
        }

        Spacer(modifier = Modifier.height(32.dp))

        StartSessionButton(navController)
    }
}

@Composable
fun HistoryBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "History",
            color = Color.White,
            style = AppTypo.titleLarge
        )
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Arrow",
            tint = Color.LightGray
        )
    }
}

@Composable
fun WeeklyStat(listData: List<StatData>){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = "This week",
        color = Color.White,
        style = AppTypo.titleLarge
    )
    // Scrollable Stats Row
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(listData) { stat ->
            StatCard(title = stat.title, value = stat.value)
        }
    }
}

@Composable
fun StartSessionButton(navController: NavController){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.selectWorkout)
            },
            modifier = Modifier
                .width(162.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFF9AC0D6),
                containerColor = Color(0xFF9AC0D6).copy(alpha = 0.2f)
            )

        )
        {
            Text(
                text = "Start session",
                style = AppTypo.titleSmall,
                color = Color(0xFF9AC0D6)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Arrow",
                tint = Color(0xFF9AC0D6)
            )
        }
    }
}

@Composable
fun ProgressChart(pointsData: List<Point>){
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(colorFromResource(R.color.bottom_bar_background)),
        contentAlignment = Alignment.TopCenter
    ) {
        LineChartScreen(pointsData)
    }
}

@Composable
fun StatCard(title: String, value: String)
{
    Box(
        modifier = Modifier
            .width(114.dp)
            .height(122.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFF1E1E1E))
            .border(width = 2.dp, color = Color(0xFF595959), shape = RoundedCornerShape(8.dp))
    ){
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp),
            text = title,
            color = colorFromResource(R.color.white),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = value,
            color = colorFromResource(R.color.primary_green),
            style = AppTypo.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun LineChartScreen(pointsData: List<Point>){
    val steps = 5

    val xAxisData = AxisData.Builder()
        .axisStepSize(50.dp)
        .backgroundColor(colorFromResource(R.color.bottom_bar_background))
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .axisLineColor(colorFromResource(R.color.grid_color))
        .axisLabelColor(Color.White)
        .shouldDrawAxisLineTillEnd(true)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(colorFromResource(R.color.bottom_bar_background))
        .labelData { i ->
            val yScale = 100 / steps
            (i * yScale).toString()
        }
        .axisLineColor(colorFromResource(R.color.grid_color))
        .axisLabelColor(Color.White)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = colorFromResource(R.color.primary_green),
                        lineType = LineType.Straight(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = colorFromResource(R.color.primary_green)
                    ),
                    SelectionHighlightPoint(color = Color.Transparent),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorFromResource(R.color.primary_green),
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp(
                        backgroundColor = Color.Black,
                        backgroundStyle = Stroke(2f),
                        labelColor = Color.White,
                        popUpLabel = { x, y ->
                            val xLabel = "Week : ${x.toInt()} "
                            val yLabel = "Hour : ${String.format("%.2f", y)}"
                            "$xLabel - $yLabel"
                        }
                    )
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(
            color = colorFromResource(R.color.grid_color),
            enableVerticalLines = false),
        backgroundColor = colorFromResource(R.color.bottom_bar_background),
        paddingRight = 0.dp,
        containerPaddingEnd = 0.dp,

    )

    LineChart(
        modifier = Modifier
            .fillMaxSize(),
        lineChartData = lineChartData
    )
}


//@Composable
//@Preview
//fun HomeScreenPreview(){
//    HomeScreen(navController = NavController(LocalContext.current))
//}