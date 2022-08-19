package com.example.jetreddit.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetreddit.R

//
@Composable
fun JionButton(
    onClick: (Boolean) -> Unit = {}
) {
    var buttonState: JoinButtonState
            by remember { mutableStateOf(JoinButtonState.IDLE) }
    val shape = RoundedCornerShape(corner = CornerSize(12.dp))
    val buttonBackgroundColor: Color =
        if (buttonState == JoinButtonState.PRESSED) Color.White else Color.Blue
    var iconAsset: ImageVector =
        if (buttonState == JoinButtonState.PRESSED) Icons.Default.Check else Icons.Default.Add
    val iconTintColor: Color =
        if (buttonState == JoinButtonState.PRESSED) Color.Blue else Color.White
    Box(
        modifier = Modifier
            .clip(shape)
            .border(width = 1.dp, color = Color.Blue, shape = shape)
            .background(color = buttonBackgroundColor).animateContentSize()
            .size(width = 60.dp, height = 24.dp)
            .clickable(onClick = {
                buttonState =
                    if (buttonState == JoinButtonState.IDLE) {
                        onClick.invoke(true)
                        JoinButtonState.PRESSED
                    } else {
                        onClick.invoke(false)
                        JoinButtonState.IDLE
                    }
            }),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = iconAsset, contentDescription = stringResource(id = R.string.post_icon))
            Text(
                text = "Join",
                color = Color.White,
                fontSize = 14.sp,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

enum class JoinButtonState { IDLE, PRESSED }

@Preview
@Composable
fun JionButtonPreview() {
    JionButton(onClick = {})
}
