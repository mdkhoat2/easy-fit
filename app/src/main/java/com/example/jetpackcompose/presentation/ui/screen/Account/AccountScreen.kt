package com.example.jetpackcompose.presentation.ui.screen.Account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.presentation.di.Routes
import com.example.jetpackcompose.ui.theme.Colors
import com.example.jetpackcompose.ui.theme.AppTypo

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun AccountScreen(
    navController: NavController
) {
    // Get screen height
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(screenHeight / 7))
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.changePassword)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFF9AC0D6),
                containerColor = Color(0xFF9AC0D6).copy(alpha = 0.2f)
            )
        ){
            Text(
                text = "Change password",
                style = AppTypo.titleLarge,
                color = Color(0xFF9AC0D6)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_change_password),
                contentDescription = "change password"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.notificationSetting)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFF9AC0D6),
                containerColor = Color(0xFF9AC0D6).copy(alpha = 0.2f)
            )
        ){
            Text(
                text = "Notification setting",
                style = AppTypo.titleLarge,
                color = Color(0xFF9AC0D6)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_notification_setting),
                contentDescription = "change password"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Colors.Orange, // Text and icon color
                containerColor = Colors.Orange.copy(alpha = 0.2f), // Background color
            ),
            border = BorderStroke(1.dp, Colors.Orange)
        ){
            Text(
                text = "Log out",
                style = AppTypo.titleLarge,
                color = Colors.Orange
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_log_out),
                contentDescription = "change password"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun AccountScreenPreview() {
    AccountScreen(navController = NavController(LocalContext.current))
}