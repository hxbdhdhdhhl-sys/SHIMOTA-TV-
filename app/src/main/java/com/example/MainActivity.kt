package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.ContentType
import com.example.data.model.DownloadItem
import com.example.data.model.Episode
import com.example.data.model.MediaItem
import com.example.data.repository.ShimotaRepository
import com.example.ui.components.ShimotaVideoPlayer
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.ComingSoonScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.MediaDetailScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.ProfileAndSettingsScreen
import com.example.ui.screens.VipScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.Strings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        ShimotaApp()
      }
    }
  }
}

enum class ScreenTab {
  HOME,
  EXPLORE,
  COMING_SOON,
  LIBRARY,
  VIP,
  PROFILE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShimotaApp() {
  val repo = ShimotaRepository
  val mediaList by repo.mediaList.collectAsState()
  val watchlistIds by repo.watchlistIds.collectAsState()
  val favoriteIds by repo.favoriteIds.collectAsState()
  val watchProgressMap by repo.watchProgressMap.collectAsState()
  val reviewsMap by repo.reviewsMap.collectAsState()
  val downloads by repo.downloads.collectAsState()
  val currentUser by repo.currentUser.collectAsState()
  val notifications by repo.notifications.collectAsState()
  val appLanguage by repo.appLanguage.collectAsState()

  val layoutDirection = if (appLanguage == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr

  var currentTab by remember { mutableStateOf(ScreenTab.HOME) }
  var selectedMedia by remember { mutableStateOf<MediaItem?>(null) }
  var activePlayerMedia by remember { mutableStateOf<Pair<MediaItem, Episode?>?>(null) }
  var showNotificationsScreen by remember { mutableStateOf(false) }
  var showAdminScreen by remember { mutableStateOf(false) }

  val unreadNotificationsCount = notifications.count { !it.isRead }

  CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
    // If Video Player is active, show player full screen with back handler
    if (activePlayerMedia != null) {
      val (media, episode) = activePlayerMedia!!
      val videoUrl = episode?.videoUrl ?: media.videoUrl
      val title = if (appLanguage == "ar") media.titleAr else media.title
      val epSubtitle = episode?.let { if (appLanguage == "ar") "${it.titleAr} (حلقة ${it.episodeNumber})" else "${it.title} (Ep. ${it.episodeNumber})" } ?: ""
      val currentProgress = repo.getWatchProgress(media.id, episode?.id)?.positionMs ?: 0L

      BackHandler {
        activePlayerMedia = null
      }

      ShimotaVideoPlayer(
        videoUrl = videoUrl,
        title = title,
        subtitle = epSubtitle,
        initialPositionMs = currentProgress,
        onClose = { activePlayerMedia = null },
        onSaveProgress = { pos, dur ->
          repo.saveWatchProgress(media.id, episode?.id, pos, dur)
        }
      )
    } else if (showNotificationsScreen) {
      BackHandler {
        showNotificationsScreen = false
      }
      NotificationsScreen(
        notifications = notifications,
        appLanguage = appLanguage,
        onBack = { showNotificationsScreen = false },
        onNotificationClick = { mediaId ->
          showNotificationsScreen = false
          if (mediaId != null) {
            val found = mediaList.find { it.id == mediaId }
            if (found != null) selectedMedia = found
          }
        },
        onMarkAllRead = { repo.markNotificationsAsRead() }
      )
    } else if (showAdminScreen) {
      BackHandler {
        showAdminScreen = false
      }
      AdminScreen(
        mediaList = mediaList,
        appLanguage = appLanguage,
        onBack = { showAdminScreen = false },
        onAddMedia = { newItem -> repo.addMediaItem(newItem) },
        onDeleteMedia = { id -> repo.deleteMediaItem(id) }
      )
    } else if (selectedMedia != null) {
      val media = selectedMedia!!
      val progress = repo.getWatchProgress(media.id, null)
      val isWatchlisted = watchlistIds.contains(media.id)
      val isFavorite = favoriteIds.contains(media.id)
      val reviews = reviewsMap[media.id] ?: emptyList()
      val isDownloaded = downloads.any { it.mediaId == media.id }

      BackHandler {
        selectedMedia = null
      }

      MediaDetailScreen(
        item = media,
        progress = progress,
        isWatchlisted = isWatchlisted,
        isFavorite = isFavorite,
        reviews = reviews,
        isDownloaded = isDownloaded,
        appLanguage = appLanguage,
        onBack = { selectedMedia = null },
        onPlay = { ep -> activePlayerMedia = Pair(media, ep) },
        onToggleWatchlist = { repo.toggleWatchlist(media.id) },
        onToggleFavorite = { repo.toggleFavorite(media.id) },
        onDownload = { ep -> repo.startDownload(media, ep) },
        onAddReview = { rating, comment -> repo.addReview(media.id, rating, comment) }
      )
    } else {
      // Main App Scaffold with TopBar and Bottom Navigation
      Scaffold(
        topBar = {
          TopAppBar(
            title = {
              Text(
                text = "SHIMOTA TV",
                color = GoldPrimary,
                fontWeight = FontWeight.Black,
                fontSize = 20.sp,
                letterSpacing = 1.sp
              )
            },
            actions = {
              // Notification icon with badge
              IconButton(onClick = { showNotificationsScreen = true }) {
                BadgedBox(
                  badge = {
                    if (unreadNotificationsCount > 0) {
                      Badge(
                        containerColor = GoldPrimary,
                        contentColor = DarkBackground
                      ) {
                        Text(text = "$unreadNotificationsCount")
                      }
                    }
                  }
                ) {
                  Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = TextWhite
                  )
                }
              }
            },
            colors = TopAppBarDefaults.topAppBarColors(
              containerColor = DarkSurface,
              titleContentColor = GoldPrimary
            )
          )
        },
        bottomBar = {
          NavigationBar(
            containerColor = DarkSurface,
            contentColor = GoldPrimary
          ) {
            NavigationBarItem(
              selected = currentTab == ScreenTab.HOME,
              onClick = { currentTab = ScreenTab.HOME },
              icon = { Icon(Icons.Default.Home, contentDescription = Strings.get("home", appLanguage)) },
              label = { Text(Strings.get("home", appLanguage), fontSize = 11.sp) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted,
                indicatorColor = DarkBackground
              )
            )

            NavigationBarItem(
              selected = currentTab == ScreenTab.EXPLORE,
              onClick = { currentTab = ScreenTab.EXPLORE },
              icon = { Icon(Icons.Default.Search, contentDescription = Strings.get("explore", appLanguage)) },
              label = { Text(Strings.get("explore", appLanguage), fontSize = 11.sp) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted,
                indicatorColor = DarkBackground
              )
            )

            NavigationBarItem(
              selected = currentTab == ScreenTab.COMING_SOON,
              onClick = { currentTab = ScreenTab.COMING_SOON },
              icon = { Icon(Icons.Default.Upcoming, contentDescription = Strings.get("coming_soon", appLanguage)) },
              label = { Text(Strings.get("coming_soon", appLanguage), fontSize = 11.sp) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted,
                indicatorColor = DarkBackground
              )
            )

            NavigationBarItem(
              selected = currentTab == ScreenTab.LIBRARY,
              onClick = { currentTab = ScreenTab.LIBRARY },
              icon = { Icon(Icons.Default.VideoLibrary, contentDescription = Strings.get("library", appLanguage)) },
              label = { Text(Strings.get("library", appLanguage), fontSize = 11.sp) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted,
                indicatorColor = DarkBackground
              )
            )

            NavigationBarItem(
              selected = currentTab == ScreenTab.VIP,
              onClick = { currentTab = ScreenTab.VIP },
              icon = { Icon(Icons.Default.Diamond, contentDescription = Strings.get("vip", appLanguage)) },
              label = { Text(Strings.get("vip", appLanguage), fontSize = 11.sp) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted,
                indicatorColor = DarkBackground
              )
            )

            NavigationBarItem(
              selected = currentTab == ScreenTab.PROFILE,
              onClick = { currentTab = ScreenTab.PROFILE },
              icon = { Icon(Icons.Default.Person, contentDescription = Strings.get("profile", appLanguage)) },
              label = { Text(Strings.get("profile", appLanguage), fontSize = 11.sp) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GoldPrimary,
                selectedTextColor = GoldPrimary,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted,
                indicatorColor = DarkBackground
              )
            )
          }
        }
      ) { innerPadding ->
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
          when (currentTab) {
            ScreenTab.HOME -> {
              HomeScreen(
                mediaList = mediaList,
                watchlistIds = watchlistIds,
                watchProgressMap = watchProgressMap,
                appLanguage = appLanguage,
                onMediaClick = { selectedMedia = it },
                onPlayClick = { activePlayerMedia = Pair(it, null) },
                onToggleWatchlist = { repo.toggleWatchlist(it) },
                onVipClick = { currentTab = ScreenTab.VIP }
              )
            }
            ScreenTab.EXPLORE -> {
              ExploreScreen(
                mediaList = mediaList,
                appLanguage = appLanguage,
                onMediaClick = { selectedMedia = it }
              )
            }
            ScreenTab.COMING_SOON -> {
              ComingSoonScreen(
                items = mediaList,
                appLanguage = appLanguage,
                onPlayTrailer = { activePlayerMedia = Pair(it, null) }
              )
            }
            ScreenTab.LIBRARY -> {
              LibraryScreen(
                allMedia = mediaList,
                watchlistIds = watchlistIds,
                favoriteIds = favoriteIds,
                downloads = downloads,
                appLanguage = appLanguage,
                onMediaClick = { selectedMedia = it },
                onPlayDownload = { dl ->
                  val found = mediaList.find { it.id == dl.mediaId }
                  if (found != null) {
                    val ep = found.seasons.flatMap { it.episodes }.find { it.id == dl.episodeId }
                    activePlayerMedia = Pair(found, ep)
                  } else {
                    val fallback = MediaItem(
                      id = dl.mediaId,
                      title = dl.title,
                      titleAr = dl.title,
                      overview = "محتوى محمل للمشاهدة بدون إنترنت",
                      overviewAr = "محتوى محمل للمشاهدة بدون إنترنت",
                      posterUrl = "",
                      backdropUrl = "",
                      contentType = ContentType.MOVIE,
                      genre = "Offline",
                      genreAr = "تنزيل محلي",
                      year = 2024,
                      rating = 9.0f,
                      ratingCount = 1,
                      durationMinutes = 60,
                      isVip = false,
                      isTrending = false,
                      isComingSoon = false,
                      releaseDate = "2024",
                      videoUrl = dl.videoUrl
                    )
                    activePlayerMedia = Pair(fallback, null)
                  }
                },
                onRemoveDownload = { repo.removeDownload(it) }
              )
            }
            ScreenTab.VIP -> {
              VipScreen(
                currentUser = currentUser,
                appLanguage = appLanguage,
                onSubscribe = { tier -> repo.upgradeVip(tier) }
              )
            }
            ScreenTab.PROFILE -> {
              ProfileAndSettingsScreen(
                currentUser = currentUser,
                appLanguage = appLanguage,
                onLanguageChange = { newLang -> repo.setLanguage(newLang) },
                onOpenAdmin = { showAdminScreen = true },
                onOpenVip = { currentTab = ScreenTab.VIP },
                onOpenNotifications = { showNotificationsScreen = true },
                onLogin = { email, pass -> repo.loginUser(email, pass) },
                onRegister = { name, email, pass -> repo.registerUser(name, email, pass) },
                onResetPassword = { email -> repo.resetPassword(email) },
                onLogout = { repo.logout() }
              )
            }
          }
        }
      }
    }
  }
}
