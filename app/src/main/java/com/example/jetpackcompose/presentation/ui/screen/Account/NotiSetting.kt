package com.example.jetpackcompose.presentation.ui.screen.Account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.ui.theme.Colors
import com.example.jetpackcompose.ui.theme.AppTypo
import org.hamcrest.core.StringEndsWith

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun NotificationSetting(
    navController: NavController
) {
    val spaceBetweenElements = 16.dp
    var enabledNotifyPost by remember { mutableStateOf(true) }
    var enabledNotifyPlan by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar("Setting"){
            navController.navigateUp()
        }
        Spacer(modifier = Modifier.height(spaceBetweenElements))
        SwitchRow("Notify me about my post", enabledNotifyPost){
            enabledNotifyPost = it
        }
        Spacer(modifier = Modifier.height(spaceBetweenElements))
        SwitchRow("Notify me about my plan", enabledNotifyPlan) {
            enabledNotifyPlan = it
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun SwitchRow(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = label,
            color = Color.White,
            style = AppTypo.bodyLarge
        )
        Switch(
            checked = isChecked,
            onCheckedChange = {
                onCheckedChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Colors.Blue,
                uncheckedThumbColor = Colors.Blue,
                uncheckedTrackColor = Color.Transparent
            )
        )
    }
}

@Preview
@Composable
fun NotificationSettingPreview(){
    NotificationSetting(navController = NavController(LocalContext.current))
}