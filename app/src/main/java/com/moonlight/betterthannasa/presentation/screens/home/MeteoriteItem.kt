package com.moonlight.betterthannasa.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moonlight.betterthannasa.domain.model.Meteorite
import com.moonlight.betterthannasa.R
import com.moonlight.betterthannasa.ui.theme.*
import java.time.format.DateTimeFormatter

@Composable
fun MeteoriteItem(
    meteorite: Meteorite
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = LARGE_PADDING)
            .border(BorderStroke(2.dp, Color.DarkGray)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(INFO_ICON_SIZE),
            painter = painterResource(id = R.drawable.ic_reclass),
            contentDescription = "Name of the meteorite",
            tint = DarkGray
        )
        Text(
            modifier = Modifier.padding(start = MEDIUM_PADDING, end = MEDIUM_PADDING),
            text = meteorite.name,
            color = DarkGray,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = meteorite.recclass,
            color = DarkGray.copy(0.7f),
            fontSize = MaterialTheme.typography.subtitle2.fontSize
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InfoBox(
            icon = painterResource(id = R.drawable.ic__calendar),
            iconColor = MaterialTheme.colors.primary,
            bigText = meteorite.year.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            smallText = "Year",
            textColor = DarkGray
        )
        InfoBox(
            icon = painterResource(id = R.drawable.ic_mass),
            iconColor = MaterialTheme.colors.primary,
            bigText = "${meteorite.mass}",
            smallText = "Mass",
            textColor = DarkGray
        )
        InfoBox(
            icon = painterResource(id = R.drawable.ic_location),
            iconColor = MaterialTheme.colors.primary,
            bigText = "${meteorite.latitude}/${meteorite.longitude}",
            smallText = "Location",
            textColor = DarkGray
        )
    }
}

