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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ContentType
import com.example.data.model.Episode
import com.example.data.model.MediaItem
import com.example.data.model.Season
import com.example.ui.theme.AccentRed
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.Strings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import java.util.UUID

@Composable
fun AdminScreen(
  mediaList: List<MediaItem>,
  appLanguage: String,
  onBack: () -> Unit,
  onAddMedia: (MediaItem) -> Unit,
  onDeleteMedia: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val isAr = appLanguage == "ar"
  var showAddDialog by remember { mutableStateOf(false) }

  // Stats calculation
  val totalMovies = mediaList.count { it.contentType == ContentType.MOVIE }
  val totalSeries = mediaList.count { it.contentType == ContentType.SERIES }
  val totalEpisodes = mediaList.filter { it.contentType == ContentType.SERIES }.sumOf { it.seasons.sumOf { s -> s.episodes.size } }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp)
    ) {
      // Header
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBack) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = Strings.get("admin_panel", appLanguage),
          color = GoldPrimary,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // STATS CARDS
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        StatCard(
          title = if (isAr) "الأفلام" else "Movies",
          count = "$totalMovies",
          icon = Icons.Default.Movie,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = if (isAr) "المسلسلات" else "Series",
          count = "$totalSeries",
          icon = Icons.Default.LiveTv,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = if (isAr) "الحلقات" else "Episodes",
          count = "$totalEpisodes",
          icon = Icons.Default.VideoLibrary,
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Content Management List
      Text(
        text = if (isAr) "قائمة الأعمال وروابط البث الحية" else "Media Content & Live Streams",
        color = GoldPrimary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp)
      )

      Spacer(modifier = Modifier.height(8.dp))

      LazyColumn(
        contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 100.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(mediaList) { item ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = if (isAr) item.titleAr else item.title,
                  color = TextWhite,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "${if (item.contentType == ContentType.SERIES) "مسلسل" else "فيلم"} • ${item.genre} • ${item.year}",
                  color = GoldPrimary,
                  fontSize = 12.sp
                )
                Text(
                  text = item.videoUrl,
                  color = TextMuted,
                  fontSize = 11.sp,
                  maxLines = 1
                )
              }

              IconButton(onClick = { onDeleteMedia(item.id) }) {
                Icon(
                  imageVector = Icons.Default.Delete,
                  contentDescription = "Delete",
                  tint = AccentRed
                )
              }
            }
          }
        }
      }
    }

    // FLOATING ACTION BUTTON (ADD CONTENT)
    FloatingActionButton(
      onClick = { showAddDialog = true },
      containerColor = GoldPrimary,
      contentColor = DarkBackground,
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(20.dp)
    ) {
      Icon(imageVector = Icons.Default.Add, contentDescription = "Add Content")
    }
  }

  // ADD CONTENT DIALOG
  if (showAddDialog) {
    AddMediaDialog(
      isArabic = isAr,
      appLanguage = appLanguage,
      onDismiss = { showAddDialog = false },
      onConfirm = { newItem ->
        onAddMedia(newItem)
        showAddDialog = false
      }
    )
  }
}

@Composable
private fun StatCard(
  title: String,
  count: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(imageVector = icon, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(24.dp))
      Spacer(modifier = Modifier.height(4.dp))
      Text(text = count, color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Black)
      Text(text = title, color = TextMuted, fontSize = 11.sp)
    }
  }
}

@Composable
private fun AddMediaDialog(
  isArabic: Boolean,
  appLanguage: String,
  onDismiss: () -> Unit,
  onConfirm: (MediaItem) -> Unit
) {
  var titleEn by remember { mutableStateOf("") }
  var titleAr by remember { mutableStateOf("") }
  var overviewEn by remember { mutableStateOf("") }
  var overviewAr by remember { mutableStateOf("") }
  var streamUrl by remember { mutableStateOf("https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8") }
  var posterUrl by remember { mutableStateOf("https://images.unsplash.com/photo-1534447677768-be436bb09401?w=800&q=80") }
  var isSeries by remember { mutableStateOf(false) }
  var isVip by remember { mutableStateOf(false) }
  var genre by remember { mutableStateOf("Action / Sci-Fi") }
  var year by remember { mutableIntStateOf(2024) }

  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = DarkSurfaceElevated,
    title = {
      Text(
        text = Strings.get("admin_add_content", appLanguage),
        color = GoldPrimary,
        fontWeight = FontWeight.Bold
      )
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
      ) {
        OutlinedTextField(
          value = titleEn,
          onValueChange = { titleEn = it },
          label = { Text("Title (English)", color = TextMuted) },
          colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = GoldPrimary),
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
          value = titleAr,
          onValueChange = { titleAr = it },
          label = { Text("العنوان بالعربية", color = TextMuted) },
          colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = GoldPrimary),
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
          value = streamUrl,
          onValueChange = { streamUrl = it },
          label = { Text("رابط البث HLS (.m3u8) أو MP4", color = TextMuted) },
          colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = GoldPrimary),
          modifier = Modifier.fillMaxWidth()
        )
        Text(
          text = "ملاحظة: يرجى استخدام روابط بث مرخصة وقانونية حصراً. سيتم فحص الرابط تلقائياً في المشغل.",
          color = GoldPrimary,
          fontSize = 11.sp,
          modifier = Modifier.padding(top = 2.dp, bottom = 4.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = overviewAr,
          onValueChange = { overviewAr = it },
          label = { Text("قصة العمل", color = TextMuted) },
          colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = GoldPrimary),
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "مسلسل مع مواسم وحلقات؟", color = TextWhite, fontSize = 13.sp)
          Switch(
            checked = isSeries,
            onCheckedChange = { isSeries = it },
            colors = SwitchDefaults.colors(checkedThumbColor = GoldPrimary)
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "حصري VIP؟", color = TextWhite, fontSize = 13.sp)
          Switch(
            checked = isVip,
            onCheckedChange = { isVip = it },
            colors = SwitchDefaults.colors(checkedThumbColor = GoldPrimary)
          )
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (titleEn.isNotBlank() || titleAr.isNotBlank()) {
            val id = UUID.randomUUID().toString()
            val seasons = if (isSeries) {
              listOf(
                Season(
                  seasonNumber = 1,
                  title = "Season 1",
                  titleAr = "الموسم الأول",
                  episodes = listOf(
                    Episode(
                      id = "${id}_e1",
                      episodeNumber = 1,
                      title = "Pilot Episode",
                      titleAr = "الحلقة الأولى",
                      durationMinutes = 45,
                      videoUrl = streamUrl,
                      thumbnailUrl = posterUrl,
                      overview = "First episode of the season",
                      overviewAr = "بداية أحداث المسلسل المثير"
                    )
                  )
                )
              )
            } else emptyList()

            val newItem = MediaItem(
              id = id,
              title = titleEn.ifEmpty { titleAr },
              titleAr = titleAr.ifEmpty { titleEn },
              overview = overviewEn.ifEmpty { overviewAr },
              overviewAr = overviewAr.ifEmpty { overviewEn },
              posterUrl = posterUrl,
              backdropUrl = posterUrl,
              contentType = if (isSeries) ContentType.SERIES else ContentType.MOVIE,
              genre = genre,
              genreAr = "حركة وإثارة",
              year = year,
              rating = 9.0f,
              ratingCount = 1,
              durationMinutes = 90,
              isVip = isVip,
              isTrending = true,
              isComingSoon = false,
              releaseDate = "2024",
              videoUrl = streamUrl,
              seasons = seasons
            )
            onConfirm(newItem)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DarkBackground)
      ) {
        Text(text = Strings.get("save", appLanguage), fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(text = Strings.get("cancel", appLanguage), color = TextMuted)
      }
    }
  )
}
