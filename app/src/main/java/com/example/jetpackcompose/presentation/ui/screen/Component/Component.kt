package com.example.jetpackcompose.presentation.ui.screen.Component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.R
import com.example.jetpackcompose.presentation.ui.screen.colorFromResource
import com.example.jetpackcompose.ui.theme.AppTypo

@Composable
fun BigButtonWithIcon(
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Search,
    text: String = "Search",
    align: ButtonAlign = ButtonAlign.CENTER
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = colorFromResource(R.color.primary_teal),
            containerColor = colorFromResource(R.color.primary_teal).copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = getButtonArrangement(align),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Render text and icon based on alignment type
            if (align != ButtonAlign.NO_ICON) {
                if (align == ButtonAlign.START || align == ButtonAlign.CENTER) {
                    Icon(
                        imageVector = icon,
                        contentDescription = text,
                        tint = colorFromResource(R.color.primary_teal)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Text(
                text = text,
                style = AppTypo.bodyLarge,
                color = colorFromResource(R.color.primary_teal)
            )

            if (align == ButtonAlign.SPREAD) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = colorFromResource(R.color.primary_teal)
                )
            }
        }
    }
}

// Utility function for button alignment
private fun getButtonArrangement(align: ButtonAlign): Arrangement.Horizontal {
    return when (align) {
        ButtonAlign.CENTER -> Arrangement.Center
        ButtonAlign.START -> Arrangement.Start
        ButtonAlign.SPREAD -> Arrangement.SpaceBetween
        ButtonAlign.NO_ICON -> Arrangement.Start
    }
}

enum class ButtonAlign {
    CENTER,
    START,
    SPREAD,
    NO_ICON
}

@Composable
fun LineDivider() {
    Spacer(modifier = Modifier.height(16.dp))
    // line divider
    Box(
        modifier = Modifier.run {
            fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
}

//@Preview
//@Composable
//fun CustomOutlinedTextFieldPreview() {
//}
