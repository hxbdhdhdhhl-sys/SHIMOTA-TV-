package com.example

import com.example.data.model.ContentType
import com.example.data.model.MediaItem
import com.example.data.repository.ShimotaRepository
import com.example.ui.theme.Strings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun testRepositoryInitialData() {
    val repo = ShimotaRepository
    val list = repo.mediaList.value
    assertTrue("Media list should not be empty", list.isNotEmpty())

    val movies = list.filter { it.contentType == ContentType.MOVIE }
    val series = list.filter { it.contentType == ContentType.SERIES }
    assertTrue("Should contain movies", movies.isNotEmpty())
    assertTrue("Should contain series", series.isNotEmpty())

    // Verify series have seasons and episodes
    val seriesWithSeasons = series.firstOrNull { it.seasons.isNotEmpty() }
    assertNotNull("Should have at least one series with seasons", seriesWithSeasons)
    assertTrue("Season should have episodes", seriesWithSeasons!!.seasons.first().episodes.isNotEmpty())
  }

  @Test
  fun testWatchlistAndFavorites() {
    val repo = ShimotaRepository
    val testId = "test_media_123"

    assertFalse(repo.watchlistIds.value.contains(testId))
    repo.toggleWatchlist(testId)
    assertTrue(repo.watchlistIds.value.contains(testId))
    repo.toggleWatchlist(testId)
    assertFalse(repo.watchlistIds.value.contains(testId))

    assertFalse(repo.favoriteIds.value.contains(testId))
    repo.toggleFavorite(testId)
    assertTrue(repo.favoriteIds.value.contains(testId))
    repo.toggleFavorite(testId)
    assertFalse(repo.favoriteIds.value.contains(testId))
  }

  @Test
  fun testWatchProgressSaving() {
    val repo = ShimotaRepository
    repo.saveWatchProgress("m1", null, 15000L, 60000L)
    val progress = repo.getWatchProgress("m1", null)
    assertNotNull(progress)
    assertEquals(15000L, progress!!.positionMs)
    assertEquals(60000L, progress.durationMs)
    assertEquals(0.25f, progress.progressFraction, 0.01f)
  }

  @Test
  fun testUserAuthAndVip() {
    val repo = ShimotaRepository
    repo.loginUser("cinema@shimota.tv", "pass123")
    val user = repo.currentUser.value
    assertNotNull(user)
    assertEquals("cinema@shimota.tv", user!!.email)

    repo.upgradeVip("VIP Diamond")
    val updatedUser = repo.currentUser.value
    assertTrue(updatedUser!!.isVip)
    assertEquals("VIP Diamond", updatedUser.vipTier)
  }

  @Test
  fun testLocalizationStrings() {
    assertEquals("SHIMOTA TV", Strings.get("app_name", "ar"))
    assertEquals("الرئيسية", Strings.get("home", "ar"))
    assertEquals("Home", Strings.get("home", "en"))
    assertEquals("مكتبتي", Strings.get("library", "ar"))
    assertEquals("Library", Strings.get("library", "en"))
  }

  @Test
  fun testAdminAddAndDeleteMedia() {
    val repo = ShimotaRepository
    val initialCount = repo.mediaList.value.size
    val newItem = MediaItem(
      id = "custom_test_item",
      title = "Test Cinema",
      titleAr = "سينما تجريبية",
      overview = "Test overview",
      overviewAr = "وصف تجريبي",
      posterUrl = "https://example.com/poster.jpg",
      backdropUrl = "https://example.com/backdrop.jpg",
      contentType = ContentType.MOVIE,
      genre = "Action",
      genreAr = "حركة",
      year = 2025,
      rating = 9.5f,
      ratingCount = 10,
      durationMinutes = 110,
      isVip = true,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "2025",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    )

    repo.addMediaItem(newItem)
    assertEquals(initialCount + 1, repo.mediaList.value.size)

    repo.deleteMediaItem("custom_test_item")
    assertEquals(initialCount, repo.mediaList.value.size)
  }

  @Test
  fun testVideoPlayerTrackOptions() {
    val qualityOptions = com.example.ui.components.DefaultQualityOptions
    assertTrue("Should have quality options", qualityOptions.isNotEmpty())
    assertEquals(Int.MAX_VALUE, qualityOptions.first().maxHeight)

    val subtitleOptions = com.example.ui.components.DefaultSubtitleOptions
    assertTrue("Should have subtitle options", subtitleOptions.isNotEmpty())
    assertTrue("First option should be off", subtitleOptions.first().isOff)
    assertTrue("Should have Arabic subtitle option", subtitleOptions.any { it.language == "ar" })
  }

  @Test
  fun testStreamUrlValidation() {
    val invalidUrl1 = ""
    val invalidUrl2 = "unlicensed_random_string"
    val validUrl = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"

    fun isUrlInvalid(url: String) = url.isBlank() || !url.startsWith("http")

    assertTrue(isUrlInvalid(invalidUrl1))
    assertTrue(isUrlInvalid(invalidUrl2))
    assertFalse(isUrlInvalid(validUrl))
  }

  @Test
  fun testSearchAndFilterLogic() {
    val repo = ShimotaRepository
    val list = repo.mediaList.value

    // Arabic search test
    val arabicMatches = list.filter { it.titleAr.contains("فولاذ") }
    assertTrue("Should find 'دموع من فولاذ'", arabicMatches.isNotEmpty())

    // English search test
    val englishMatches = list.filter { it.title.contains("Bunny", ignoreCase = true) }
    assertTrue("Should find 'Big Buck Bunny'", englishMatches.isNotEmpty())

    // Genre filter
    val sciFiMatches = list.filter { it.genre.contains("Sci-Fi") || it.genreAr.contains("خيال علمي") }
    assertTrue("Should find Sci-Fi items", sciFiMatches.isNotEmpty())
  }
}
