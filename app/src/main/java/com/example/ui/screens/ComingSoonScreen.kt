package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.model.MediaItem
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.Strings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun ComingSoonScreen(
  items: List<MediaItem>,
  appLanguage: String,
  onPlayTrailer: (MediaItem) -> Unit,
  modifier: Modifier = Modifier
) {
  val isAr = appLanguage == "ar"
  val comingSoonList = items.filter { it.isComingSoon }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground),
    contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Column {
        Text(
          text = Strings.get("coming_soon", appLanguage),
          color = GoldPrimary,
          fontSize = 22.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = if (isAr) "ترقب أحدث الإصدارات السينمائية الحصرية على SHIMOTA TV" else "Upcoming exclusive cinematic premieres on SHIMOTA TV",
          color = TextMuted,
          fontSize = 13.sp
        )
      }
    }

    items(comingSoonList) { item ->
      ComingSoonCard(
        item = item,
        isArabic = isAr,
        appLanguage = appLanguage,
        onPlayTrailer = { onPlayTrailer(item) }
      )
    }
  }
}

@Composable
fun ComingSoonCard(
  item: MediaItem,
  isArabic: Boolean,
  appLanguage: String,
  onPlayTrailer: () -> Unit
) {
  var isReminded by remember { mutableStateOf(false) }
  val title = if (isArabic) item.titleAr else item.title
  val overview = if (isArabic) item.overviewAr else item.overview

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp)
      ) {
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data(item.backdropUrl.ifEmpty { item.posterUrl })
            .crossfade(true)
            .build(),
          contentDescription = title,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
              )
            )
        )

        // Release Date Badge
        Row(
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(12.dp)
            .background(
              Brush.horizontalGradient(listOf(GoldPrimary, GoldDark)),
              RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = null,
            tint = DarkBackground,
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = item.releaseDate,
            color = DarkBackground,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Column(modifier = Modifier.padding(14.dp)) {
        Text(
          text = title,
          color = TextWhite,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = overview,
          color = TextMuted,
          fontSize = 13.sp,
          lineHeight = 18.sp,
          maxLines = 3
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = onPlayTrailer,
            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DarkBackground),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
          ) {
            Icon(imageVector = Icons.Default.PlayCircleOutline, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = if (isArabic) "الإعلان التشويقي" else "Trailer", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }

          OutlinedButton(
            onClick = { isReminded = !isReminded },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = if (isReminded) GoldPrimary else TextWhite),
            modifier = Modifier.weight(1f)
          ) {
            Icon(
              imageVector = if (isReminded) Icons.Default.NotificationsActive else Icons.Default.Notifications,
              contentDescription = null,
              tint = if (isReminded) GoldPrimary else TextWhite,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (isReminded) (if (isArabic) "تم التفعيل" else "Reminded") else (if (isArabic) "ذكّرني" else "Remind Me"),
              fontSize = 13.sp
            )
          }
        }
      }
    }
  }
}
