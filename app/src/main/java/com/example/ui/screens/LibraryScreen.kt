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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DownloadItem
import com.example.data.model.MediaItem
import com.example.ui.components.MediaCard
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.Strings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun LibraryScreen(
  allMedia: List<MediaItem>,
  watchlistIds: Set<String>,
  favoriteIds: Set<String>,
  downloads: List<DownloadItem>,
  appLanguage: String,
  onMediaClick: (MediaItem) -> Unit,
  onPlayDownload: (DownloadItem) -> Unit,
  onRemoveDownload: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val isAr = appLanguage == "ar"
  var selectedTab by remember { mutableIntStateOf(0) }

  val tabs = listOf(
    Strings.get("add_to_list", appLanguage), // Watchlist
    Strings.get("favorite", appLanguage),   // Favorites
    Strings.get("downloads", appLanguage)   // Downloads
  )

  val watchlistedMedia = allMedia.filter { watchlistIds.contains(it.id) }
  val favoriteMedia = allMedia.filter { favoriteIds.contains(it.id) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground)
  ) {
    // TABS
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = DarkSurfaceCard,
      contentColor = GoldPrimary,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
          modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
          color = GoldPrimary
        )
      }
    ) {
      tabs.forEachIndexed { index, title ->
        Tab(
          selected = selectedTab == index,
          onClick = { selectedTab = index },
          text = {
            Text(
              text = title,
              fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
              fontSize = 14.sp
            )
          }
        )
      }
    }

    when (selectedTab) {
      0 -> {
        // WATCHLIST GRID
        if (watchlistedMedia.isEmpty()) {
          EmptyState(
            message = if (isAr) "قائمة المشاهدة لاحقاً فارغة" else "Your Watchlist is empty"
          )
        } else {
          LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 140.dp),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            items(watchlistedMedia) { item ->
              MediaCard(
                item = item,
                isArabic = isAr,
                onClick = { onMediaClick(item) }
              )
            }
          }
        }
      }
      1 -> {
        // FAVORITES GRID
        if (favoriteMedia.isEmpty()) {
          EmptyState(
            message = if (isAr) "قائمة المفضلة فارغة حالياً" else "Your Favorites list is empty"
          )
        } else {
          LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 140.dp),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            items(favoriteMedia) { item ->
              MediaCard(
                item = item,
                isArabic = isAr,
                onClick = { onMediaClick(item) }
              )
            }
          }
        }
      }
      2 -> {
        // DOWNLOADS LIST (OFFLINE PLAYBACK)
        if (downloads.isEmpty()) {
          EmptyState(
            message = if (isAr) "لا توجد تنزيلات حالياً. حمّل أفلامك ومسلسلاتك المفضلة للمشاهدة بدون إنترنت." else "No downloaded media yet. Download titles to watch offline anytime."
          )
        } else {
          LazyColumn(
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            items(downloads) { dl ->
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable { onPlayDownload(dl) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.DownloadDone,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(32.dp)
                  )
                  Spacer(modifier = Modifier.width(14.dp))
                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = dl.title,
                      color = TextWhite,
                      fontSize = 14.sp,
                      fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                      text = "${dl.quality} • ${dl.fileSizeMb} MB • جاهز للمشاهدة دون إنترنت",
                      color = TextMuted,
                      fontSize = 12.sp
                    )
                  }
                  IconButton(onClick = { onPlayDownload(dl) }) {
                    Icon(
                      imageVector = Icons.Default.PlayArrow,
                      contentDescription = "Play offline",
                      tint = GoldPrimary,
                      modifier = Modifier.size(28.dp)
                    )
                  }
                  IconButton(onClick = { onRemoveDownload(dl.id) }) {
                    Icon(
                      imageVector = Icons.Default.DeleteOutline,
                      contentDescription = "Delete",
                      tint = TextMuted,
                      modifier = Modifier.size(22.dp)
                    )
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun EmptyState(message: String) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(32.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = message,
      color = TextMuted,
      fontSize = 15.sp,
      lineHeight = 22.sp
    )
  }
}
