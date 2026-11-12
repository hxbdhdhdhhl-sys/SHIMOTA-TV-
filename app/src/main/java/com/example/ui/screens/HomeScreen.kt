package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.model.ContentType
import com.example.data.model.MediaItem
import com.example.data.model.WatchProgress
import com.example.ui.components.ContinueWatchingCard
import com.example.ui.components.MediaCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.StarGold
import com.example.ui.theme.Strings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun HomeScreen(
  mediaList: List<MediaItem>,
  watchlistIds: Set<String>,
  watchProgressMap: Map<String, WatchProgress>,
  appLanguage: String,
  onMediaClick: (MediaItem) -> Unit,
  onPlayClick: (MediaItem) -> Unit,
  onToggleWatchlist: (String) -> Unit,
  onVipClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isAr = appLanguage == "ar"
  val featuredItem = mediaList.find { it.id == "m1" } ?: mediaList.firstOrNull()

  // Continue watching list: items that have a saved progress
  val continueWatchingItems = mediaList.mapNotNull { item ->
    val progress = watchProgressMap[item.id]
      ?: item.seasons.flatMap { it.episodes }.mapNotNull { ep -> watchProgressMap["${item.id}_${ep.id}"] }.firstOrNull()
    if (progress != null && progress.progressFraction < 0.95f) {
      Pair(item, progress)
    } else null
  }

  val trendingItems = mediaList.filter { it.isTrending && !it.isComingSoon }
  val seriesItems = mediaList.filter { it.contentType == ContentType.SERIES && !it.isComingSoon }
  val movieItems = mediaList.filter { it.contentType == ContentType.MOVIE && !it.isComingSoon }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground),
    contentPadding = PaddingValues(bottom = 90.dp)
  ) {
    // HERO FEATURED BANNER
    if (featuredItem != null) {
      item {
        val title = if (isAr) featuredItem.titleAr else featuredItem.title
        val overview = if (isAr) featuredItem.overviewAr else featuredItem.overview
        val inList = watchlistIds.contains(featuredItem.id)

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(430.dp)
            .clickable { onMediaClick(featuredItem) }
        ) {
          AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
              .data(featuredItem.backdropUrl.ifEmpty { featuredItem.posterUrl })
              .crossfade(true)
              .build(),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )

          // Dramatic Cinema Dark Gradients
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(
                Brush.verticalGradient(
                  colors = listOf(
                    Color.Black.copy(alpha = 0.5f),
                    Color.Transparent,
                    DarkBackground.copy(alpha = 0.85f),
                    DarkBackground
                  )
                )
              )
          )

          // Content Details over Hero
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .align(Alignment.BottomStart)
              .padding(16.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              // VIP Badge
              Box(
                modifier = Modifier
                  .background(
                    Brush.horizontalGradient(listOf(GoldPrimary, GoldDark)),
                    RoundedCornerShape(6.dp)
                  )
                  .padding(horizontal = 8.dp, vertical = 3.dp)
              ) {
                Text(
                  text = "SHIMOTA VIP",
                  color = DarkBackground,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Black
                )
              }

              // Rating
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Filled.Star,
                  contentDescription = null,
                  tint = StarGold,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = String.format("%.1f", featuredItem.rating),
                  color = TextWhite,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
              }

              Text(
                text = "• ${featuredItem.year} • ${if (isAr) featuredItem.genreAr else featuredItem.genre}",
                color = TextMuted,
                fontSize = 12.sp
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = title,
              color = TextWhite,
              fontSize = 26.sp,
              fontWeight = FontWeight.ExtraBold,
              lineHeight = 32.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = overview,
              color = TextWhite.copy(alpha = 0.8f),
              fontSize = 13.sp,
              maxLines = 2,
              lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Button(
                onClick = { onPlayClick(featuredItem) },
                colors = ButtonDefaults.buttonColors(
                  containerColor = GoldPrimary,
                  contentColor = DarkBackground
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                  .weight(1f)
                  .height(44.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.PlayArrow,
                  contentDescription = null,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = Strings.get("watch_now", appLanguage),
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                )
              }

              OutlinedButton(
                onClick = { onToggleWatchlist(featuredItem.id) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(GoldPrimary, GoldAccent))),
                modifier = Modifier
                  .weight(1f)
                  .height(44.dp)
              ) {
                Icon(
                  imageVector = if (inList) Icons.Default.Check else Icons.Default.Add,
                  contentDescription = null,
                  modifier = Modifier.size(18.dp),
                  tint = if (inList) GoldPrimary else TextWhite
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (inList) Strings.get("in_list", appLanguage) else Strings.get("add_to_list", appLanguage),
                  fontSize = 13.sp
                )
              }
            }
          }
        }
      }
    }

    // CONTINUE WATCHING ROW
    if (continueWatchingItems.isNotEmpty()) {
      item {
        Spacer(modifier = Modifier.height(10.dp))
        SectionHeader(
          title = Strings.get("continue_watching", appLanguage)
        )
        LazyRow(
          contentPadding = PaddingValues(horizontal = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(continueWatchingItems) { (item, progress) ->
            ContinueWatchingCard(
              item = item,
              progress = progress,
              isArabic = isAr,
              onClick = { onPlayClick(item) }
            )
          }
        }
      }
    }

    // VIP PROMO BANNER
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
          .clickable(onClick = onVipClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.horizontalGradient(
                listOf(
                  Color(0xFF2C2410),
                  DarkSurfaceElevated
                )
              )
            )
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(CircleShape)
              .background(Brush.radialGradient(listOf(GoldPrimary, GoldDark))),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.WorkspacePremium,
              contentDescription = null,
              tint = DarkBackground,
              modifier = Modifier.size(28.dp)
            )
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = Strings.get("vip_upgrade_title", appLanguage),
              color = GoldPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = Strings.get("vip_feature_1", appLanguage),
              color = TextMuted,
              fontSize = 12.sp
            )
          }

          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = GoldPrimary,
            modifier = Modifier.size(24.dp)
          )
        }
      }
    }

    // TRENDING NOW
    item {
      Spacer(modifier = Modifier.height(16.dp))
      SectionHeader(
        title = Strings.get("trending_now", appLanguage)
      )
      LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(trendingItems) { item ->
          MediaCard(
            item = item,
            isArabic = isAr,
            onClick = { onMediaClick(item) }
          )
        }
      }
    }

    // TOP TV SERIES (WITH SEASONS & EPISODES)
    item {
      Spacer(modifier = Modifier.height(16.dp))
      SectionHeader(
        title = Strings.get("series", appLanguage)
      )
      LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(seriesItems) { item ->
          MediaCard(
            item = item,
            isArabic = isAr,
            onClick = { onMediaClick(item) }
          )
        }
      }
    }

    // BLOCKBUSTER MOVIES
    item {
      Spacer(modifier = Modifier.height(16.dp))
      SectionHeader(
        title = Strings.get("movies", appLanguage)
      )
      LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(movieItems) { item ->
          MediaCard(
            item = item,
            isArabic = isAr,
            onClick = { onMediaClick(item) }
          )
        }
      }
    }
  }
}
