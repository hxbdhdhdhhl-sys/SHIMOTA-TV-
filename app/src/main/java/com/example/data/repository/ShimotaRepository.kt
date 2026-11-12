package com.example.data.repository

import com.example.data.model.AppNotification
import com.example.data.model.ContentType
import com.example.data.model.DownloadItem
import com.example.data.model.Episode
import com.example.data.model.MediaItem
import com.example.data.model.Season
import com.example.data.model.UserAccount
import com.example.data.model.UserReview
import com.example.data.model.WatchProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

object ShimotaRepository {

  // Seed Data: Real licensed streams (HLS .m3u8 & MP4) with rich metadata & artwork
  private val initialMediaList: List<MediaItem> = listOf(
    MediaItem(
      id = "m1",
      title = "Tears of Steel",
      titleAr = "دموع من فولاذ",
      overview = "In a dystopian future, a group of scientists and warriors assemble at the Oude Kerk in Amsterdam to stage a desperate attempt to rescue the world from destructive cyborgs.",
      overviewAr = "في مستقبل بائس، تجتمع مجموعة من العلماء والمحاربين في محاولة مستميتة لإنقاذ العالم من السايبورغ والآلات المدمرة وإعادة تشغيل خط الزمن.",
      posterUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1200&q=80",
      contentType = ContentType.MOVIE,
      genre = "Sci-Fi / Action",
      genreAr = "خيال علمي / حركة",
      year = 2024,
      rating = 8.9f,
      ratingCount = 1420,
      durationMinutes = 82,
      isVip = true,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "2024-03-15",
      videoUrl = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8",
      director = "Ian Hubert",
      cast = listOf("Derek de Lint", "Sergio Hasselbaink", "Rogier Schippers"),
      tags = listOf("4K UHD", "HLS Stream", "VIP Exclusive", "HDR10")
    ),
    MediaItem(
      id = "s1",
      title = "Cosmos Laundromat: First Cycle",
      titleAr = "غسيل الأكوان: الدورة الأولى",
      overview = "On a desolate island, a suicidal sheep named Franck meets a quirky salesman who offers him the gift of a lifetime: a chance to live many lives in parallel dimensions.",
      overviewAr = "في جزيرة مقفرة ومهجورة، يلتقي خروف يائس ببائع غامض يعرض عليه تجربة فريدة عبر السفر بين عوالم وأبعاد موازية لا حصر لها.",
      posterUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Fantasy / Drama",
      genreAr = "فنتازيا / دراما",
      year = 2024,
      rating = 9.2f,
      ratingCount = 2890,
      durationMinutes = 45,
      isVip = false,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "2024-01-20",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
      director = "Mathieu Auvray",
      cast = listOf("Pierre Bokma", "Reinout Scholten"),
      tags = listOf("Top Series", "Multi-Season", "Full HD"),
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: Alternate Realities",
          titleAr = "الموسم الأول: عوالم موازية",
          episodes = listOf(
            Episode(
              id = "s1_e1",
              episodeNumber = 1,
              title = "The Desolate Tree",
              titleAr = "الشجرة المعزولة",
              durationMinutes = 24,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=600&q=80",
              overview = "Franck makes a life-altering choice when an eccentric traveler approaches him.",
              overviewAr = "يتخذ فرانك قراراً يغير مصيره عندما يظهر أمامه مسافر غريب يمتلك جهازاً يسافر عبر الأبعاد."
            ),
            Episode(
              id = "s1_e2",
              episodeNumber = 2,
              title = "The Parallel Vortex",
              titleAr = "دوامة الأبعاد",
              durationMinutes = 28,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=600&q=80",
              overview = "Transported across realities, the rules of physics shift with every breath.",
              overviewAr = "يجد نفسه في عالم جديد حيث تنقلب قوانين الطبيعة وعليه إيجاد مخرج قبل فوات الأوان."
            ),
            Episode(
              id = "s1_e3",
              episodeNumber = 3,
              title = "The Final Fabric",
              titleAr = "النسيج الأخير",
              durationMinutes = 31,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=600&q=80",
              overview = "The secrets of the cosmic laundromat are unveiled in a dramatic confrontation.",
              overviewAr = "تتكشف أسرار المنظومة الكونية في مواجهة مصيرية تحدد بقاء العوالم."
            )
          )
        ),
        Season(
          seasonNumber = 2,
          title = "Season 2: Return to Cosmos",
          titleAr = "الموسم الثاني: العودة إلى الفضاء",
          episodes = listOf(
            Episode(
              id = "s1_e4",
              episodeNumber = 1,
              title = "Echoes of Eternity",
              titleAr = "أصداء الخلود",
              durationMinutes = 26,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyBlazes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=600&q=80",
              overview = "A new journey begins through uncharted quantum portals.",
              overviewAr = "بداية ملحمية لمسار استكشافي جديد عبر بوابات كوانتية لم تُكتشف من قبل."
            )
          )
        )
      )
    ),
    MediaItem(
      id = "m2",
      title = "Big Buck Bunny",
      titleAr = "مغامرة الأرنب الكبير",
      overview = "A large and lovable rabbit deals with bullying forest creatures in this world-renowned high-definition animated classic masterpiece.",
      overviewAr = "أرنب ضخم ولطيف يقرر تلقين مخلوقات الغابة المزعجة درساً لا ينسى في هذا العمل الكرتوني العالمي الممتع.",
      posterUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1511447333015-45b65e60f6d5?w=1200&q=80",
      contentType = ContentType.MOVIE,
      genre = "Animation / Comedy",
      genreAr = "رسوم متحركة / كوميديا",
      year = 2023,
      rating = 8.7f,
      ratingCount = 4210,
      durationMinutes = 60,
      isVip = false,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "2023-11-10",
      videoUrl = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8",
      director = "Sacha Goedegebure",
      cast = listOf("Bunny", "Frank the Flying Squirrel", "Rinky"),
      tags = listOf("Family", "HLS Multi-Bitrate", "Popular")
    ),
    MediaItem(
      id = "m3",
      title = "Sintel: The Dragon Quest",
      titleAr = "سينتيل: رحلة التنين",
      overview = "A fierce young woman embarks on a dangerous journey across icy mountains and arid deserts to rescue a baby dragon that became her companion.",
      overviewAr = "تنطلق محاربة شابة شجاعة في رحلة محفوفة بالمخاطر عبر الجبال الجليدية والصحاري لإنقاذ تنينها الصغير المختطف.",
      posterUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1200&q=80",
      contentType = ContentType.MOVIE,
      genre = "Fantasy / Adventure",
      genreAr = "فنتازيا / مغامرات",
      year = 2023,
      rating = 9.1f,
      ratingCount = 3120,
      durationMinutes = 75,
      isVip = true,
      isTrending = false,
      isComingSoon = false,
      releaseDate = "2023-09-01",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
      director = "Colin Levy",
      cast = listOf("Halina Reijn", "Thom Hoffman"),
      tags = listOf("VIP Premiere", "Epic Fantasy", "4K")
    ),
    MediaItem(
      id = "s2",
      title = "Bullrun: Highway Legends",
      titleAr = "بول ران: أساطير الطريق السريع",
      overview = "High octane cross-country driving competition pushing supercars and elite drivers to the absolute mechanical limit.",
      overviewAr = "سباق سيارات أسطوري يخوضه سائقون محترفون عبر تضاريس وطرق وعرة لاختبار قوة الآلات وعزيمة الأبطال.",
      posterUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Action / Thriller",
      genreAr = "حركة / إثارة",
      year = 2024,
      rating = 8.5f,
      ratingCount = 1850,
      durationMinutes = 50,
      isVip = true,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "2024-04-05",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
      director = "David Marcus",
      cast = listOf("Alex Roy", "Michael Green"),
      tags = listOf("Action", "Supercars", "Adrenaline"),
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: Coast to Coast",
          titleAr = "الموسم الأول: من الساحل إلى الساحل",
          episodes = listOf(
            Episode(
              id = "s2_e1",
              episodeNumber = 1,
              title = "Green Light at Dawn",
              titleAr = "الضوء الأخضر عند الفجر",
              durationMinutes = 48,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=600&q=80",
              overview = "Engines roar as thirty teams line up for the ultimate coast-to-coast rally.",
              overviewAr = "تنطلق ثلاثون سيارة خارقة في تحدي السرعة الأول مع شروق الشمس."
            ),
            Episode(
              id = "s2_e2",
              episodeNumber = 2,
              title = "Desert Thunder",
              titleAr = "رعد الصحراء",
              durationMinutes = 52,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=600&q=80",
              overview = "Heatwaves and steep canyons test both machine cooling and driver stamina.",
              overviewAr = "حرارة الصحراء الحارقة والمنعطفات الخطيرة تضع المتسابقين في اختبار حقيقي للبقاء."
            )
          )
        )
      )
    ),
    MediaItem(
      id = "m4",
      title = "Elephants Dream",
      titleAr = "حلم الفيلة",
      overview = "Two strange characters explore a vast, organic mechanical labyrinth that evolves in response to their subconscious fears and desires.",
      overviewAr = "يستكشف شخصان متاهة ميكانيكية عملاقة حية تتغير استجابةً لمخاوفهما وأحلامهما في تحفة سينمائية سريالية.",
      posterUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=1200&q=80",
      contentType = ContentType.MOVIE,
      genre = "Sci-Fi / Drama",
      genreAr = "خيال علمي / دراما",
      year = 2022,
      rating = 8.4f,
      ratingCount = 980,
      durationMinutes = 65,
      isVip = false,
      isTrending = false,
      isComingSoon = false,
      releaseDate = "2022-08-14",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
      director = "Bassam Kurdali",
      cast = listOf("Cas Jansen", "Tygo Gernandt"),
      tags = listOf("Sci-Fi", "Surreal", "Classic")
    ),
    MediaItem(
      id = "m5",
      title = "Outback: Wilderness Expeditions",
      titleAr = "البرية: رحلات الأعماق",
      overview = "An awe-inspiring cinematic documentary exploring untouched nature, volcanic ridges, and rugged wildlife frontiers.",
      overviewAr = "وثائقي سينمائي مذهل يسلط الضوء على الطبيعة العذراء وسلاسل الجبال البركانية وحياة البرية النادرة.",
      posterUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=1200&q=80",
      contentType = ContentType.MOVIE,
      genre = "Documentary / Nature",
      genreAr = "وثائقي / طبيعة",
      year = 2024,
      rating = 9.4f,
      ratingCount = 1120,
      durationMinutes = 90,
      isVip = true,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "2024-02-28",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
      director = "Elena Rostova",
      cast = listOf("Sir Richard Thorne (Narrator)"),
      tags = listOf("4K Nature", "VIP", "Documentary")
    ),
    // Coming Soon items
    MediaItem(
      id = "cs1",
      title = "Project Gooseberry: Genesis",
      titleAr = "مشروع غوسبيري: التكوين",
      overview = "An upcoming epic sci-fi blockbuster chronicling the first human expedition to an ancient Dyson sphere orbiting a dying star.",
      overviewAr = "عمل خيال علمي ضخم قادم يوثق أول رحلة استكشافية بشرية لغلاف دايسون الأثري حول نجم يحتضر.",
      posterUrl = "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=1200&q=80",
      contentType = ContentType.MOVIE,
      genre = "Sci-Fi / Adventure",
      genreAr = "خيال علمي / مغامرة",
      year = 2025,
      rating = 9.8f,
      ratingCount = 450,
      durationMinutes = 115,
      isVip = true,
      isTrending = false,
      isComingSoon = true,
      releaseDate = "2025-06-20",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
      director = "Ton Roosendaal",
      cast = listOf("Eva Green", "Mads Mikkelsen"),
      tags = listOf("Coming Soon", "Exclusive", "Summer 2025")
    ),
    MediaItem(
      id = "cs2",
      title = "Cyber Chronicles: Neo Arabia",
      titleAr = "سجلات السايبر: نيو أريبيا",
      overview = "A neon-lit cyberpunk series set in the soaring futuristic megacities of the Arabian peninsula in 2088.",
      overviewAr = "مسلسل سايبربانك مستقبلي تدور أحداثه بين ناطحات السحاب وميادين المستقبل عام 2088.",
      posterUrl = "https://images.unsplash.com/photo-1508739773434-c26b3d09e071?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Sci-Fi / Cyberpunk",
      genreAr = "سايبربانك / إثارة",
      year = 2025,
      rating = 9.6f,
      ratingCount = 380,
      durationMinutes = 55,
      isVip = true,
      isTrending = false,
      isComingSoon = true,
      releaseDate = "2025-08-15",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
      director = "Ziad Doueiri",
      cast = listOf("Tarek Bishara", "Nour Al-Ghamdi"),
      tags = listOf("Coming Soon", "Cyberpunk", "VIP Series")
    )
  )

  private val _mediaList = MutableStateFlow<List<MediaItem>>(initialMediaList)
  val mediaList: StateFlow<List<MediaItem>> = _mediaList.asStateFlow()

  private val _watchlistIds = MutableStateFlow<Set<String>>(setOf("m1", "s1"))
  val watchlistIds: StateFlow<Set<String>> = _watchlistIds.asStateFlow()

  private val _favoriteIds = MutableStateFlow<Set<String>>(setOf("m1", "m3"))
  val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

  // Continue watching map: key is "mediaId" or "mediaId_episodeId"
  private val _watchProgressMap = MutableStateFlow<Map<String, WatchProgress>>(
    mapOf(
      "m1" to WatchProgress(mediaId = "m1", positionMs = 2450000L, durationMs = 4920000L),
      "s1_s1_e1" to WatchProgress(mediaId = "s1", episodeId = "s1_e1", positionMs = 820000L, durationMs = 1440000L)
    )
  )
  val watchProgressMap: StateFlow<Map<String, WatchProgress>> = _watchProgressMap.asStateFlow()

  private val _reviewsMap = MutableStateFlow<Map<String, List<UserReview>>>(
    mapOf(
      "m1" to listOf(
        UserReview("r1", "m1", "أحمد السعيد", 5.0f, "إخراج سينمائي مذهل وجودة البث HLS ممتازة جداً!", "2024-11-01"),
        UserReview("r2", "m1", "Sarah Connor", 4.5f, "Great visual effects and futuristic atmosphere.", "2024-10-25")
      ),
      "s1" to listOf(
        UserReview("r3", "s1", "محمود كمال", 5.0f, "مسلسل فلسفي غريب وممتع، الترجمة متزامنة بشكل رائع.", "2024-10-18")
      )
    )
  )
  val reviewsMap: StateFlow<Map<String, List<UserReview>>> = _reviewsMap.asStateFlow()

  private val _downloads = MutableStateFlow<List<DownloadItem>>(
    listOf(
      DownloadItem(
        id = "d1",
        mediaId = "m2",
        title = "Big Buck Bunny (1080p)",
        quality = "1080p Full HD",
        progressPercent = 100,
        isCompleted = true,
        fileSizeMb = 340,
        videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
      )
    )
  )
  val downloads: StateFlow<List<DownloadItem>> = _downloads.asStateFlow()

  private val _currentUser = MutableStateFlow<UserAccount?>(
    UserAccount(
      id = "u1",
      name = "طارق المنصوري",
      email = "tariq@shimota.tv",
      isVip = true,
      vipTier = "VIP Diamond",
      isAdmin = true
    )
  )
  val currentUser: StateFlow<UserAccount?> = _currentUser.asStateFlow()

  private val _notifications = MutableStateFlow<List<AppNotification>>(
    listOf(
      AppNotification(
        id = "n1",
        title = "New Episode Added!",
        titleAr = "تمت إضافة حلقة جديدة!",
        body = "Episode 3 of Cosmos Laundromat is now streaming in 4K HDR.",
        bodyAr = "الحلقة 3 من مسلسل غوسمولوند رومات متوفرة الآن بدقة 4K فائقة الوضوح.",
        timeAgo = "منذ ساعة",
        mediaId = "s1"
      ),
      AppNotification(
        id = "n2",
        title = "VIP Premiere Alert",
        titleAr = "عرض حصري لمشتركي VIP",
        body = "Tears of Steel HLS remaster is now available for VIP members.",
        bodyAr = "فيلم دموع من فولاذ بنسخة البث المحسنة أصبح متاحاً الآن لمشتركي VIP.",
        timeAgo = "منذ يومين",
        mediaId = "m1"
      )
    )
  )
  val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

  // App Language: "ar" or "en"
  private val _appLanguage = MutableStateFlow<String>("ar")
  val appLanguage: StateFlow<String> = _appLanguage.asStateFlow()

  fun setLanguage(lang: String) {
    _appLanguage.value = lang
  }

  fun toggleWatchlist(mediaId: String) {
    val current = _watchlistIds.value.toMutableSet()
    if (current.contains(mediaId)) current.remove(mediaId) else current.add(mediaId)
    _watchlistIds.value = current
  }

  fun toggleFavorite(mediaId: String) {
    val current = _favoriteIds.value.toMutableSet()
    if (current.contains(mediaId)) current.remove(mediaId) else current.add(mediaId)
    _favoriteIds.value = current
  }

  fun saveWatchProgress(mediaId: String, episodeId: String?, positionMs: Long, durationMs: Long) {
    if (durationMs <= 0) return
    val key = if (episodeId != null) "${mediaId}_$episodeId" else mediaId
    val progress = WatchProgress(
      mediaId = mediaId,
      episodeId = episodeId,
      positionMs = positionMs,
      durationMs = durationMs,
      lastWatchedTimestamp = System.currentTimeMillis()
    )
    val map = _watchProgressMap.value.toMutableMap()
    map[key] = progress
    _watchProgressMap.value = map
  }

  fun getWatchProgress(mediaId: String, episodeId: String?): WatchProgress? {
    val key = if (episodeId != null) "${mediaId}_$episodeId" else mediaId
    return _watchProgressMap.value[key]
  }

  fun addReview(mediaId: String, rating: Float, comment: String) {
    val user = _currentUser.value?.name ?: "زائر"
    val newReview = UserReview(
      id = UUID.randomUUID().toString(),
      mediaId = mediaId,
      userName = user,
      rating = rating,
      comment = comment,
      date = "الآن"
    )
    val currentMap = _reviewsMap.value.toMutableMap()
    val list = currentMap[mediaId]?.toMutableList() ?: mutableListOf()
    list.add(0, newReview)
    currentMap[mediaId] = list
    _reviewsMap.value = currentMap

    // Update media average rating
    val avg = list.map { it.rating }.average().toFloat()
    _mediaList.value = _mediaList.value.map { item ->
      if (item.id == mediaId) {
        item.copy(rating = (avg * 10).toInt() / 10f, ratingCount = item.ratingCount + 1)
      } else item
    }
  }

  fun startDownload(media: MediaItem, episode: Episode?, quality: String = "1080p") {
    val id = UUID.randomUUID().toString()
    val title = if (episode != null) "${media.title} - S1E${episode.episodeNumber}" else media.title
    val stream = episode?.videoUrl ?: media.videoUrl
    val item = DownloadItem(
      id = id,
      mediaId = media.id,
      episodeId = episode?.id,
      title = title,
      quality = quality,
      progressPercent = 100, // completed simulated offline download
      isCompleted = true,
      fileSizeMb = if (episode != null) 280 else 720,
      videoUrl = stream
    )
    val current = _downloads.value.toMutableList()
    current.add(0, item)
    _downloads.value = current
  }

  fun removeDownload(downloadId: String) {
    _downloads.value = _downloads.value.filter { it.id != downloadId }
  }

  fun loginUser(email: String, pass: String): Boolean {
    val isAdmin = email.contains("admin", ignoreCase = true) || email == "tariq@shimota.tv"
    _currentUser.value = UserAccount(
      id = UUID.randomUUID().toString(),
      name = if (isAdmin) "مدير النظام (Admin)" else email.substringBefore("@").replaceFirstChar { it.uppercase() },
      email = email,
      isVip = true,
      vipTier = if (isAdmin) "VIP Diamond" else "VIP Gold",
      isAdmin = isAdmin
    )
    return true
  }

  fun registerUser(name: String, email: String, pass: String): Boolean {
    _currentUser.value = UserAccount(
      id = UUID.randomUUID().toString(),
      name = name,
      email = email,
      isVip = false,
      vipTier = "Free",
      isAdmin = false
    )
    return true
  }

  fun resetPassword(email: String): Boolean {
    return true
  }

  fun logout() {
    _currentUser.value = null
  }

  fun upgradeVip(tier: String) {
    _currentUser.value = _currentUser.value?.copy(isVip = true, vipTier = tier)
      ?: UserAccount(
        id = UUID.randomUUID().toString(),
        name = "عضو VIP",
        email = "vip@shimota.tv",
        isVip = true,
        vipTier = tier,
        isAdmin = false
      )
  }

  // Admin Operations
  fun addMediaItem(item: MediaItem) {
    val list = _mediaList.value.toMutableList()
    list.add(0, item)
    _mediaList.value = list

    // Notify users of new release
    val newNotification = AppNotification(
      id = UUID.randomUUID().toString(),
      title = "New Content Added!",
      titleAr = "تمت إضافة عمل جديد!",
      body = "${item.title} is now available on SHIMOTA TV.",
      bodyAr = "أصبح '${item.titleAr}' متاحاً الآن للمشاهدة على شيموتا تي في.",
      timeAgo = "الآن",
      mediaId = item.id
    )
    val notifList = _notifications.value.toMutableList()
    notifList.add(0, newNotification)
    _notifications.value = notifList
  }

  fun updateMediaItem(item: MediaItem) {
    _mediaList.value = _mediaList.value.map { if (it.id == item.id) item else it }
  }

  fun deleteMediaItem(mediaId: String) {
    _mediaList.value = _mediaList.value.filter { it.id != mediaId }
  }

  fun markNotificationsAsRead() {
    _notifications.value = _notifications.value.map { it.copy(isRead = true) }
  }
}
