package com.example.jetpackcompose.presentation.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.Stroke
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

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "History",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Arrow",
                tint = Color.LightGray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(colorFromResource(R.color.bottom_bar_background)),
            contentAlignment = Alignment.TopCenter
        ) {
            val pointsData = listOf(
                Point(0f, 40f),
                Point(1f, 90f),
                Point(2f, 0f),
                Point(3f, 60f),
                Point(4f, 10f)
            )

            LineChartScreen(pointsData)
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Scrollable Stats Row
    }
}

@Composable
fun StatCard(title: String, value: String)
{
    Column(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorFromResource(R.color.home_btn))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = title,
            color = colorFromResource(R.color.white),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = colorFromResource(R.color.line_color),
            style = MaterialTheme.typography.labelLarge
        )

    }
}

@Composable
fun LineChartScreen(pointsData: List<Point>){
    val steps = 5

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
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
                        color = colorFromResource(R.color.line_color),
                        lineType = LineType.Straight(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = colorFromResource(R.color.line_color)
                    ),
                    SelectionHighlightPoint(color = Color.Transparent),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorFromResource(R.color.line_color),
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


@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen()
}