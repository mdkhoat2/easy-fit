package com.example.jetpackcompose.presentation.ui.screen.Authen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.presentation.di.BottomBarScreen
import com.example.jetpackcompose.presentation.ui.screen.colorFromResource
import com.example.jetpackcompose.ui.theme.Typography

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotOTP(
    navController: NavController
) {
    var email by remember { mutableStateOf("") }

    // Get screen height
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Scaffold(
        contentColor = Color.Black,
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = colorFromResource(R.color.btn_back_color)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(screenHeight / 8))

            Image(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(92.dp)
            )

            Spacer(modifier = Modifier.height(screenHeight / 8))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("OTP") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            Text(
                text = "Enter the OTP and log in. You can also see your current password we sent to your mail.",
                color = Color.White,
                style = Typography.labelSmall,
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .padding(horizontal = 24.dp)
            )

            OutlinedButton(
                onClick = {
                    navController.navigateUp()
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
            ) {
                Text(
                    text = "LOG IN",
                    style = Typography.titleLarge,
                    color = Color(0xFF9AC0D6)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
@Preview
fun ForgotOTPPreview(){
    ForgotOTP(navController = NavController(LocalContext.current))
}