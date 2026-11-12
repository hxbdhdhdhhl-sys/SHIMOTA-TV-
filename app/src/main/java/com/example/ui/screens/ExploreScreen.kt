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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ContentType
import com.example.data.model.MediaItem
import com.example.ui.components.CategoryChip
import com.example.ui.components.MediaCard
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.Strings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun ExploreScreen(
  mediaList: List<MediaItem>,
  appLanguage: String,
  onMediaClick: (MediaItem) -> Unit,
  modifier: Modifier = Modifier
) {
  val isAr = appLanguage == "ar"
  var searchQuery by remember { mutableStateOf("") }
  var selectedType by remember { mutableStateOf<ContentType?>(null) }
  var selectedGenre by remember { mutableStateOf<String?>(null) }
  var selectedYear by remember { mutableStateOf<Int?>(null) }

  val genres = listOf(
    if (isAr) "خيال علمي" else "Sci-Fi",
    if (isAr) "حركة" else "Action",
    if (isAr) "دراما" else "Drama",
    if (isAr) "فنتازيا" else "Fantasy",
    if (isAr) "رسوم متحركة" else "Animation",
    if (isAr) "وثائقي" else "Documentary"
  )

  val years = listOf(2025, 2024, 2023, 2022)

  val filteredList = mediaList.filter { item ->
    val matchesSearch = searchQuery.isBlank() ||
      item.title.contains(searchQuery, ignoreCase = true) ||
      item.titleAr.contains(searchQuery, ignoreCase = true) ||
      item.overview.contains(searchQuery, ignoreCase = true) ||
      item.overviewAr.contains(searchQuery, ignoreCase = true) ||
      item.cast.any { it.contains(searchQuery, ignoreCase = true) }

    val matchesType = selectedType == null || item.contentType == selectedType
    val matchesGenre = selectedGenre == null ||
      item.genre.contains(selectedGenre!!, ignoreCase = true) ||
      item.genreAr.contains(selectedGenre!!, ignoreCase = true)
    val matchesYear = selectedYear == null || item.year == selectedYear

    matchesSearch && matchesType && matchesGenre && matchesYear
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground)
      .padding(top = 16.dp)
  ) {
    // SEARCH BAR
    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      placeholder = { Text(text = Strings.get("search_placeholder", appLanguage), color = TextMuted, fontSize = 13.sp) },
      leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = GoldPrimary) },
      trailingIcon = {
        if (searchQuery.isNotEmpty()) {
          IconButton(onClick = { searchQuery = "" }) {
            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = TextMuted)
          }
        }
      },
      singleLine = true,
      shape = RoundedCornerShape(12.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = TextWhite,
        unfocusedTextColor = TextWhite,
        focusedContainerColor = DarkSurfaceElevated,
        unfocusedContainerColor = DarkSurfaceElevated,
        focusedBorderColor = GoldPrimary,
        unfocusedBorderColor = DarkBorderSubtle
      )
    )

    Spacer(modifier = Modifier.height(12.dp))

    // CONTENT TYPE FILTER CHIPS (All, Movies, Series)
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      item {
        CategoryChip(
          label = Strings.get("all", appLanguage),
          isSelected = selectedType == null,
          onClick = { selectedType = null }
        )
      }
      item {
        CategoryChip(
          label = Strings.get("movies", appLanguage),
          isSelected = selectedType == ContentType.MOVIE,
          onClick = { selectedType = if (selectedType == ContentType.MOVIE) null else ContentType.MOVIE }
        )
      }
      item {
        CategoryChip(
          label = Strings.get("series", appLanguage),
          isSelected = selectedType == ContentType.SERIES,
          onClick = { selectedType = if (selectedType == ContentType.SERIES) null else ContentType.SERIES }
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // GENRES FILTER CHIPS
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(genres) { g ->
        CategoryChip(
          label = g,
          isSelected = selectedGenre == g,
          onClick = { selectedGenre = if (selectedGenre == g) null else g }
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // YEARS FILTER CHIPS
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(years) { y ->
        CategoryChip(
          label = "$y",
          isSelected = selectedYear == y,
          onClick = { selectedYear = if (selectedYear == y) null else y }
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // RESULTS GRID
    if (filteredList.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(bottom = 80.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = if (isAr) "لا توجد نتائج تطابق بحثك" else "No matching titles found",
          color = TextMuted,
          fontSize = 15.sp
        )
      }
    } else {
      LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 90.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(filteredList) { item ->
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
