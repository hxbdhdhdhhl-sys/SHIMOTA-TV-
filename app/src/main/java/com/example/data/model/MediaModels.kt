package com.example.data.model

enum class ContentType {
  MOVIE,
  SERIES
}

data class Episode(
  val id: String,
  val episodeNumber: Int,
  val title: String,
  val titleAr: String,
  val durationMinutes: Int,
  val videoUrl: String,
  val thumbnailUrl: String,
  val overview: String,
  val overviewAr: String,
  val airDate: String? = null
)

data class Season(
  val seasonNumber: Int,
  val title: String,
  val titleAr: String,
  val episodes: List<Episode>
)

data class MediaItem(
  val id: String,
  val title: String,
  val titleAr: String,
  val overview: String,
  val overviewAr: String,
  val posterUrl: String,
  val backdropUrl: String,
  val contentType: ContentType,
  val genre: String,
  val genreAr: String,
  val year: Int,
  val rating: Float,
  val ratingCount: Int,
  val durationMinutes: Int,
  val isVip: Boolean,
  val isTrending: Boolean,
  val isComingSoon: Boolean,
  val releaseDate: String,
  val videoUrl: String,
  val trailerUrl: String = "",
  val director: String = "SHIMOTA Studios",
  val cast: List<String> = listOf("طاقم العمل"),
  val seasons: List<Season> = emptyList(),
  val tags: List<String> = emptyList(),
  val audioLanguages: List<String> = listOf("العربية", "English"),
  val subtitleLanguages: List<String> = listOf("العربية", "English"),
  // Iraqi Series dedicated metadata
  val isIraqi: Boolean = false,
  val iraqiCategory: String? = null,
  val originalChannel: String? = null,
  val documentationSource: String? = null,
  val writer: String? = null
)

data class WatchProgress(
  val mediaId: String,
  val episodeId: String? = null,
  val positionMs: Long = 0L,
  val durationMs: Long = 0L,
  val lastWatchedTimestamp: Long = System.currentTimeMillis()
) {
  val progressFraction: Float
    get() = if (durationMs > 0) (positionMs.toFloat() / durationMs.toFloat()).coerceIn(0f, 1f) else 0f
}

data class UserReview(
  val id: String,
  val mediaId: String,
  val userName: String,
  val rating: Float,
  val comment: String,
  val date: String
)

data class DownloadItem(
  val id: String,
  val mediaId: String,
  val episodeId: String? = null,
  val title: String,
  val quality: String,
  val progressPercent: Int,
  val isCompleted: Boolean,
  val fileSizeMb: Int,
  val videoUrl: String
)

data class UserAccount(
  val id: String,
  val name: String,
  val email: String,
  val isVip: Boolean = false,
  val vipTier: String = "Free",
  val isAdmin: Boolean = false
)

data class AppNotification(
  val id: String,
  val title: String,
  val titleAr: String,
  val body: String,
  val bodyAr: String,
  val timeAgo: String,
  val isRead: Boolean = false,
  val mediaId: String? = null
)
