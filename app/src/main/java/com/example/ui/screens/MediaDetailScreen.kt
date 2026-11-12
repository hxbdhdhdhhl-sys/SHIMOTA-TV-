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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.ContentType
import com.example.data.model.Episode
import com.example.data.model.MediaItem
import com.example.data.model.UserReview
import com.example.data.model.WatchProgress
import com.example.ui.components.CategoryChip
import com.example.ui.components.InteractiveRatingBar
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AccentRed
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.StarGold
import com.example.ui.theme.Strings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun MediaDetailScreen(
  item: MediaItem,
  progress: WatchProgress?,
  isWatchlisted: Boolean,
  isFavorite: Boolean,
  reviews: List<UserReview>,
  isDownloaded: Boolean,
  appLanguage: String,
  onBack: () -> Unit,
  onPlay: (Episode?) -> Unit,
  onToggleWatchlist: () -> Unit,
  onToggleFavorite: () -> Unit,
  onDownload: (Episode?) -> Unit,
  onAddReview: (Float, String) -> Unit,
  modifier: Modifier = Modifier
) {
  val isAr = appLanguage == "ar"
  val title = if (isAr) item.titleAr else item.title
  val overview = if (isAr) item.overviewAr else item.overview
  val genre = if (isAr) item.genreAr else item.genre

  var selectedSeasonIndex by remember { mutableIntStateOf(0) }
  var userRatingInput by remember { mutableFloatStateOf(5.0f) }
  var reviewCommentInput by remember { mutableStateOf("") }
  var showReviewSuccess by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground),
    contentPadding = PaddingValues(bottom = 80.dp)
  ) {
    // BACKDROP HEADER WITH BACK BUTTON
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(300.dp)
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

        // Gradient fading downwards into DarkBackground
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(
                  Color.Black.copy(alpha = 0.6f),
                  Color.Transparent,
                  DarkBackground
                )
              )
            )
        )

        // Back button
        IconButton(
          onClick = onBack,
          modifier = Modifier
            .padding(top = 40.dp, start = 16.dp)
            .size(42.dp)
            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
        ) {
          Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = TextWhite
          )
        }

        // VIP badge
        if (item.isVip) {
          Box(
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(top = 45.dp, end = 16.dp)
              .background(
                Brush.horizontalGradient(listOf(GoldPrimary, GoldDark)),
                RoundedCornerShape(6.dp)
              )
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Text(
              text = "VIP EXCLUSIVE",
              color = DarkBackground,
              fontSize = 11.sp,
              fontWeight = FontWeight.Black
            )
          }
        }
      }
    }

    // MAIN TITLE & META INFO
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      ) {
        Text(
          text = title,
          color = TextWhite,
          fontSize = 24.sp,
          fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
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
              text = String.format("%.1f", item.rating),
              color = GoldPrimary,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = " (${item.ratingCount})",
              color = TextMuted,
              fontSize = 12.sp
            )
          }

          Text(text = "•", color = TextMuted)
          Text(text = "${item.year}", color = TextWhite, fontSize = 13.sp)
          Text(text = "•", color = TextMuted)
          Text(text = "${item.durationMinutes} min", color = TextWhite, fontSize = 13.sp)
          Text(text = "•", color = TextMuted)
          Text(
            text = if (item.contentType == ContentType.SERIES) (if (isAr) "مسلسل" else "Series") else (if (isAr) "فيلم" else "Movie"),
            color = GoldAccent,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // PRIMARY ACTION BUTTON (PLAY / RESUME)
        val resumeText = if (progress != null && progress.progressFraction > 0.05f) {
          Strings.get("resume", appLanguage)
        } else {
          Strings.get("watch_now", appLanguage)
        }

        Button(
          onClick = { onPlay(null) },
          colors = ButtonDefaults.buttonColors(
            containerColor = GoldPrimary,
            contentColor = DarkBackground
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
        ) {
          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(26.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = resumeText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // SECONDARY ACTIONS: Watchlist, Favorite, Download
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          // Watchlist
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              .clickable(onClick = onToggleWatchlist)
              .padding(8.dp)
          ) {
            Icon(
              imageVector = if (isWatchlisted) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
              contentDescription = null,
              tint = if (isWatchlisted) GoldPrimary else TextWhite,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = if (isWatchlisted) Strings.get("in_list", appLanguage) else Strings.get("add_to_list", appLanguage),
              color = if (isWatchlisted) GoldPrimary else TextMuted,
              fontSize = 12.sp
            )
          }

          // Favorite
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              .clickable(onClick = onToggleFavorite)
              .padding(8.dp)
          ) {
            Icon(
              imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
              contentDescription = null,
              tint = if (isFavorite) AccentRed else TextWhite,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = Strings.get("favorite", appLanguage),
              color = if (isFavorite) AccentRed else TextMuted,
              fontSize = 12.sp
            )
          }

          // Download
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              .clickable { onDownload(null) }
              .padding(8.dp)
          ) {
            Icon(
              imageVector = if (isDownloaded) Icons.Filled.Check else Icons.Filled.Download,
              contentDescription = null,
              tint = if (isDownloaded) GoldPrimary else TextWhite,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = if (isDownloaded) Strings.get("downloaded", appLanguage) else Strings.get("download", appLanguage),
              color = if (isDownloaded) GoldPrimary else TextMuted,
              fontSize = 12.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // STORY OVERVIEW
        Text(
          text = Strings.get("overview", appLanguage),
          color = GoldPrimary,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = overview,
          color = TextWhite.copy(alpha = 0.9f),
          fontSize = 14.sp,
          lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // CAST & DIRECTOR
        Row(modifier = Modifier.fillMaxWidth()) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = Strings.get("director", appLanguage),
              color = TextMuted,
              fontSize = 12.sp
            )
            Text(
              text = item.director,
              color = TextWhite,
              fontSize = 14.sp,
              fontWeight = FontWeight.Medium
            )
          }
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = Strings.get("genre", appLanguage),
              color = TextMuted,
              fontSize = 12.sp
            )
            Text(
              text = genre,
              color = TextWhite,
              fontSize = 14.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }
    }

    // SEASONS & EPISODES (IF SERIES)
    if (item.contentType == ContentType.SERIES && item.seasons.isNotEmpty()) {
      item {
        Spacer(modifier = Modifier.height(20.dp))
        SectionHeader(title = Strings.get("seasons_and_episodes", appLanguage))

        // Season Selector Tabs
        if (item.seasons.size > 1) {
          TabRow(
            selectedTabIndex = selectedSeasonIndex,
            containerColor = DarkSurfaceCard,
            contentColor = GoldPrimary,
            indicator = { tabPositions ->
              TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedSeasonIndex]),
                color = GoldPrimary
              )
            }
          ) {
            item.seasons.forEachIndexed { index, season ->
              Tab(
                selected = selectedSeasonIndex == index,
                onClick = { selectedSeasonIndex = index },
                text = {
                  Text(
                    text = if (isAr) season.titleAr else season.title,
                    fontWeight = if (selectedSeasonIndex == index) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 13.sp
                  )
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
      }

      val currentSeason = item.seasons.getOrNull(selectedSeasonIndex) ?: item.seasons.firstOrNull()
      if (currentSeason != null) {
        items(currentSeason.episodes) { ep ->
          EpisodeRow(
            episode = ep,
            isArabic = isAr,
            onPlay = { onPlay(ep) },
            onDownload = { onDownload(ep) }
          )
        }
      }
    }

    // RATINGS & REVIEWS SECTION
    item {
      Spacer(modifier = Modifier.height(24.dp))
      SectionHeader(title = Strings.get("reviews", appLanguage))

      // Add Review Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = Strings.get("rate_prompt", appLanguage),
            color = TextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
          )

          Spacer(modifier = Modifier.height(8.dp))

          InteractiveRatingBar(
            currentRating = userRatingInput,
            onRatingChanged = { userRatingInput = it }
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = reviewCommentInput,
            onValueChange = { reviewCommentInput = it },
            placeholder = { Text(text = "اكتب رأيك السينمائي هنا...", color = TextMuted, fontSize = 13.sp) },
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = TextWhite,
              unfocusedTextColor = TextWhite,
              focusedBorderColor = GoldPrimary,
              unfocusedBorderColor = DarkBorderSubtle
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            Button(
              onClick = {
                if (reviewCommentInput.isNotBlank()) {
                  onAddReview(userRatingInput, reviewCommentInput)
                  reviewCommentInput = ""
                  showReviewSuccess = true
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DarkBackground),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(imageVector = Icons.Outlined.Send, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = Strings.get("submit", appLanguage), fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
          }

          if (showReviewSuccess) {
            Text(
              text = "شكراً لمشاركتك! تم إضافة تقييمك بنجاح.",
              color = GoldPrimary,
              fontSize = 12.sp,
              modifier = Modifier.padding(top = 6.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
    }

    // Existing Reviews List
    items(reviews) { r ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
        shape = RoundedCornerShape(10.dp)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = r.userName,
              color = GoldPrimary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = StarGold,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(2.dp))
              Text(
                text = "${r.rating}",
                color = TextWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = r.comment,
            color = TextWhite.copy(alpha = 0.85f),
            fontSize = 13.sp
          )
        }
      }
    }
  }
}

@Composable
fun EpisodeRow(
  episode: Episode,
  isArabic: Boolean,
  onPlay: () -> Unit,
  onDownload: () -> Unit,
  modifier: Modifier = Modifier
) {
  val title = if (isArabic) episode.titleAr else episode.title
  val overview = if (isArabic) episode.overviewAr else episode.overview

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 6.dp)
      .clickable(onClick = onPlay),
    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
    shape = RoundedCornerShape(10.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Thumbnail with play icon
      Box(
        modifier = Modifier
          .width(110.dp)
          .height(68.dp)
          .clip(RoundedCornerShape(8.dp))
      ) {
        AsyncImage(
          model = episode.thumbnailUrl,
          contentDescription = title,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.35f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Play",
            tint = GoldPrimary,
            modifier = Modifier.size(28.dp)
          )
        }
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "${episode.episodeNumber}. $title",
          color = TextWhite,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          maxLines = 1
        )
        Text(
          text = "${episode.durationMinutes} min",
          color = GoldPrimary,
          fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = overview,
          color = TextMuted,
          fontSize = 12.sp,
          maxLines = 2
        )
      }

      IconButton(onClick = onDownload) {
        Icon(
          imageVector = Icons.Default.Download,
          contentDescription = "Download",
          tint = TextMuted,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}
