package com.example.valoapp.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.valoapp.data.models.maps.Map

@Composable
fun MapCardComponent(map: Map, modifier: Modifier, onClick: ()-> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    onClick = onClick,
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() }
                )
                .clip(RoundedCornerShape(16.dp))
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(map.splash),
                contentDescription = "${map.displayName} map",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f),
                contentScale = ContentScale.Crop
            )

            Text(
                text = map.displayName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                color = androidx.compose.ui.graphics.Color.White
            )
        }
    }
}