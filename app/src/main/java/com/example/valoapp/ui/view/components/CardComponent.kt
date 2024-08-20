package com.example.valoapp.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.valoapp.data.models.CardData
import com.example.valoapp.utils.hexToColorInt

@Composable
fun CardComponent(data: CardData) {
    val (agent, modifier, onClick) = data
    val painter = rememberAsyncImagePainter(agent?.bustPortrait)
    val colors = agent?.backgroundGradientColors

    val colorList = mutableListOf<Color>()

    if (colors != null) {
        for (color in colors) {
            val parsedColor = hexToColorInt(color)
            colorList.add(Color(parsedColor))
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(colors = colorList),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(
                    onClick = onClick,
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() },
                )
                .clip(RoundedCornerShape(16.dp))
                .fillMaxSize()
        ) {
            Image(
                painter = painter,
                contentDescription = "Character",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = rememberAsyncImagePainter(agent?.role?.displayIcon),
                contentDescription = "${agent?.role} role icon",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = agent?.displayName ?: "Unknown",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .background(
                        color = Color.DarkGray.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(10.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}