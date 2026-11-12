package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.model.ContentType
import com.example.data.model.MediaItem
import com.example.data.model.WatchProgress
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.StarGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun MediaCard(
  item: MediaItem,
  isArabic: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val title = if (isArabic) item.titleAr else item.title
  val genre = if (isArabic) item.genreAr else item.genre

  Card(
    modifier = modifier
      .width(150.dp)
      .clickable(onClick = onClick)
      .testTag("media_card_${item.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Column {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(210.dp)
          .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
      ) {
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data(item.posterUrl)
            .crossfade(true)
            .build(),
          contentDescription = title,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                startY = 300f
              )
            )
        )

        // VIP Badge
        if (item.isVip) {
          Box(
            modifier = Modifier
              .align(Alignment.TopStart)
              .padding(8.dp)
              .background(
                Brush.horizontalGradient(listOf(GoldPrimary, GoldDark)),
                RoundedCornerShape(6.dp)
              )
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "VIP",
              color = DarkBackground,
              fontSize = 11.sp,
              fontWeight = FontWeight.Black
            )
          }
        }

        // Content Type Badge (Series / Movie)
        Box(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(8.dp)
            .background(Color.Black.copy(alpha = 0.65f), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = if (item.contentType == ContentType.SERIES) (if (isArabic) "مسلسل" else "TV") else (if (isArabic) "فيلم" else "MOVIE"),
            color = if (item.contentType == ContentType.SERIES) GoldLight else TextWhite,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }

        // Rating
        Row(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = StarGold,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = String.format("%.1f", item.rating),
            color = TextWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp)
      ) {
        Text(
          text = title,
          color = TextWhite,
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = genre,
            color = TextMuted,
            fontSize = 11.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
          )
          Text(
            text = item.year.toString(),
            color = GoldPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }
  }
}

@Composable
fun ContinueWatchingCard(
  item: MediaItem,
  progress: WatchProgress,
  isArabic: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val title = if (isArabic) item.titleAr else item.title
  val episodeText = progress.episodeId?.let { epId ->
    val ep = item.seasons.flatMap { it.episodes }.find { it.id == epId }
    if (ep != null) (if (isArabic) "حلقة ${ep.episodeNumber}" else "Ep. ${ep.episodeNumber}") else null
  }

  Card(
    modifier = modifier
      .width(220.dp)
      .clickable(onClick = onClick)
      .testTag("continue_watching_${item.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
  ) {
    Column {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(125.dp)
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

        // Overlay with play button
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)),
          contentAlignment = Alignment.Center
        ) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .background(GoldPrimary.copy(alpha = 0.9f), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = "Resume",
              tint = DarkBackground,
              modifier = Modifier.size(24.dp)
            )
          }
        }

        // Progress indicator at the bottom edge of thumbnail
        LinearProgressIndicator(
          progress = { progress.progressFraction },
          modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .align(Alignment.BottomCenter),
          color = GoldPrimary,
          trackColor = Color.White.copy(alpha = 0.2f),
        )
      }

      Column(modifier = Modifier.padding(10.dp)) {
        Text(
          text = title,
          color = TextWhite,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        if (episodeText != null) {
          Text(
            text = episodeText,
            color = GoldPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }
  }
}

@Composable
fun SectionHeader(
  title: String,
  actionText: String? = null,
  onActionClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 10.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier = Modifier
          .width(4.dp)
          .height(18.dp)
          .background(
            Brush.verticalGradient(listOf(GoldPrimary, GoldAccent)),
            RoundedCornerShape(2.dp)
          )
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = title,
        color = TextWhite,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
      )
    }

    if (actionText != null && onActionClick != null) {
      Text(
        text = actionText,
        color = GoldPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.clickable(onClick = onActionClick)
      )
    }
  }
}

@Composable
fun GoldButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  icon: ImageVector? = null,
  enabled: Boolean = true
) {
  Button(
    onClick = onClick,
    enabled = enabled,
    modifier = modifier
      .height(48.dp)
      .testTag("gold_button_${text.replace(" ", "_")}"),
    shape = RoundedCornerShape(10.dp),
    colors = ButtonDefaults.buttonColors(
      containerColor = GoldPrimary,
      contentColor = DarkBackground,
      disabledContainerColor = DarkSurfaceElevated,
      disabledContentColor = TextMuted
    )
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      if (icon != null) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
      }
      Text(
        text = text,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
      )
    }
  }
}

@Composable
fun CategoryChip(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(20.dp))
      .background(if (isSelected) GoldPrimary else DarkSurfaceElevated)
      .border(
        width = 1.dp,
        color = if (isSelected) GoldPrimary else DarkBorderSubtle,
        shape = RoundedCornerShape(20.dp)
      )
      .clickable(onClick = onClick)
      .padding(horizontal = 14.dp, vertical = 8.dp)
  ) {
    Text(
      text = label,
      color = if (isSelected) DarkBackground else TextWhite,
      fontSize = 13.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
    )
  }
}

@Composable
fun InteractiveRatingBar(
  currentRating: Float,
  onRatingChanged: (Float) -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    for (i in 1..5) {
      val isFilled = i <= currentRating
      Icon(
        imageVector = if (isFilled) Icons.Filled.Star else Icons.Outlined.Star,
        contentDescription = "Rating $i",
        tint = if (isFilled) StarGold else TextMuted,
        modifier = Modifier
          .size(32.dp)
          .clickable { onRatingChanged(i.toFloat()) }
      )
    }
  }
}
