package com.example.jetpackcompose.presentation.ui.screen.Authen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.presentation.di.BottomBarScreen
import com.example.jetpackcompose.presentation.di.Routes
import com.example.jetpackcompose.ui.theme.AppTypo

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun LoginScreen(
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Get screen height
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

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
            label = { Text("Email") },
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

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        TextButton(
            onClick = { navController.navigate(Routes.forgotEmail) },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 8.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Forgot Password?",
                color = colorResource(R.color.btn_back_color),
                style = AppTypo.titleMedium
            )
        }

        OutlinedButton(
            onClick = {
                navController.navigate(BottomBarScreen.Home.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFF9AC0D6),
                containerColor = Color(0xFF9AC0D6).copy(alpha = 0.2f)
            )
        ){
            Text(
                text = "LOG IN",
                style = AppTypo.titleLarge,
                color = Color(0xFF9AC0D6)
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                color = colorResource(R.color.btn_back_color),
                style = AppTypo.titleSmall
            )
            TextButton(onClick = {
                navController.navigate(Routes.register)
            }) {
                Text(
                    "Register now",
                    color = colorResource(R.color.btn_back_color),
                    style = AppTypo.titleSmall
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview
fun LoginScreenPreview(){
    LoginScreen(navController = NavController(LocalContext.current))
}