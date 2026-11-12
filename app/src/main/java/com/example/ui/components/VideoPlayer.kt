package com.example.ui.components

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.ClosedCaptionDisabled
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.ui.theme.AccentRed
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import kotlinx.coroutines.delay

/**
 * Representation of video quality resolution options
 */
data class QualityOption(
  val title: String,
  val titleAr: String,
  val maxHeight: Int
)

val DefaultQualityOptions = listOf(
  QualityOption("Auto (Adaptive)", "تلقائي (تكيفي)", Int.MAX_VALUE),
  QualityOption("1080p Full HD", "1080p فائق الوضوح", 1080),
  QualityOption("720p HD", "720p عالي الدقة", 720),
  QualityOption("480p SD", "480p قياسي", 480),
  QualityOption("360p Data Saver", "360p موفر للبيانات", 360)
)

/**
 * Representation of subtitle/caption track options
 */
data class SubtitleOption(
  val id: String,
  val language: String,
  val displayName: String,
  val isOff: Boolean = false,
  val trackGroupIndex: Int = -1,
  val trackIndex: Int = -1
)

val DefaultSubtitleOptions = listOf(
  SubtitleOption(id = "off", language = "", displayName = "إيقاف الترجمة (Off)", isOff = true),
  SubtitleOption(id = "ar", language = "ar", displayName = "العربية (Arabic)", isOff = false),
  SubtitleOption(id = "en", language = "en", displayName = "English (الإنجليزية)", isOff = false)
)

/**
 * Modular VideoPlayer component using androidx.media3 (ExoPlayer)
 * supporting HLS (.m3u8), MP4, Quality Selector UI, and Subtitle Track Selection UI.
 */
@OptIn(UnstableApi::class)
@ExperimentalMaterial3Api
@Composable
fun VideoPlayer(
  videoUrl: String,
  title: String = "",
  initialPositionMs: Long = 0L,
  onBack: (() -> Unit)? = null,
  onProgressUpdate: ((positionMs: Long, durationMs: Long) -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  var isPlaying by remember { mutableStateOf(true) }
  var isBuffering by remember { mutableStateOf(true) }
  var currentPosition by remember { mutableLongStateOf(initialPositionMs) }
  var totalDuration by remember { mutableLongStateOf(0L) }
  var areControlsVisible by remember { mutableStateOf(true) }
  var isDraggingSlider by remember { mutableStateOf(false) }
  var sliderFraction by remember { mutableFloatStateOf(0f) }

  // Quality Selection State
  var showQualitySheet by remember { mutableStateOf(false) }
  var selectedQuality by remember { mutableStateOf(DefaultQualityOptions.first()) }
  var availableQualities by remember { mutableStateOf(DefaultQualityOptions) }

  // Subtitle Track Selection State
  var showSubtitleSheet by remember { mutableStateOf(false) }
  var selectedSubtitle by remember { mutableStateOf(DefaultSubtitleOptions[1]) } // Default to Arabic
  var availableSubtitles by remember { mutableStateOf(DefaultSubtitleOptions) }

  // Auto-hide controls
  LaunchedEffect(areControlsVisible, isPlaying) {
    if (areControlsVisible && isPlaying) {
      delay(4000)
      areControlsVisible = false
    }
  }

  val currentPositionState = rememberUpdatedState(currentPosition)
  val totalDurationState = rememberUpdatedState(totalDuration)

  val isUrlInvalid = videoUrl.isBlank() || !videoUrl.startsWith("http")
  var playbackError by remember {
    mutableStateOf(
      if (isUrlInvalid) "عذراً، رابط البث غير متوفر أو غير مرخص لهذا العمل حالياً وفقاً لحقوق التوزيع."
      else null
    )
  }

  // Initialize ExoPlayer with HLS and adaptive streaming configuration
  val exoPlayer = remember(videoUrl) {
    ExoPlayer.Builder(context).build().apply {
      if (!isUrlInvalid) {
        val mediaItem = if (videoUrl.contains(".m3u8", ignoreCase = true)) {
          MediaItem.Builder()
            .setUri(videoUrl)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()
        } else {
          MediaItem.fromUri(videoUrl)
        }

        setMediaItem(mediaItem)
        prepare()
        if (initialPositionMs > 0) {
          seekTo(initialPositionMs)
        }
        playWhenReady = true
      }
    }
  }

  // Periodic position observer
  LaunchedEffect(exoPlayer, playbackError) {
    while (playbackError == null) {
      if (exoPlayer.isPlaying) {
        val pos = exoPlayer.currentPosition
        val dur = exoPlayer.duration.coerceAtLeast(0L)
        currentPosition = pos
        totalDuration = dur
        if (!isDraggingSlider && dur > 0) {
          sliderFraction = (pos.toFloat() / dur.toFloat()).coerceIn(0f, 1f)
        }
        onProgressUpdate?.invoke(pos, dur)
      }
      delay(1000)
    }
  }

  // ExoPlayer listeners for playback, video quality tracks, and subtitle tracks
  DisposableEffect(exoPlayer) {
    val listener = object : Player.Listener {
      override fun onPlaybackStateChanged(state: Int) {
        isBuffering = state == Player.STATE_BUFFERING
        if (state == Player.STATE_READY) {
          totalDuration = exoPlayer.duration.coerceAtLeast(0L)
        }
      }

      override fun onIsPlayingChanged(playing: Boolean) {
        isPlaying = playing
      }

      override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
        isBuffering = false
        playbackError = "تعذر تشغيل البث. قد يكون الرابط منتهي الصلاحية أو غير مرخص قانونياً للعرض."
      }

      override fun onTracksChanged(tracks: Tracks) {
        // 1. Inspect available video tracks from HLS playlist
        val detectedHeights = mutableSetOf<Int>()
        val detectedSubtitles = mutableListOf<SubtitleOption>()
        detectedSubtitles.add(SubtitleOption(id = "off", language = "", displayName = "إيقاف الترجمة (Off)", isOff = true))

        for (groupIndex in 0 until tracks.groups.size) {
          val trackGroup = tracks.groups[groupIndex]
          if (trackGroup.type == C.TRACK_TYPE_VIDEO) {
            for (i in 0 until trackGroup.length) {
              val format = trackGroup.getTrackFormat(i)
              if (format.height > 0) {
                detectedHeights.add(format.height)
              }
            }
          } else if (trackGroup.type == C.TRACK_TYPE_TEXT) {
            for (i in 0 until trackGroup.length) {
              val format = trackGroup.getTrackFormat(i)
              val lang = format.language ?: "und"
              val label = format.label ?: when (lang.lowercase()) {
                "ar", "ara" -> "العربية (Arabic)"
                "en", "eng" -> "English"
                "fr", "fra" -> "Français"
                "es", "spa" -> "Español"
                else -> "Subtitle (${lang.uppercase()})"
              }
              detectedSubtitles.add(
                SubtitleOption(
                  id = "${groupIndex}_$i",
                  language = lang,
                  displayName = label,
                  isOff = false,
                  trackGroupIndex = groupIndex,
                  trackIndex = i
                )
              )
            }
          }
        }

        if (detectedHeights.isNotEmpty()) {
          val dynamicOptions = mutableListOf(
            QualityOption("Auto (Adaptive)", "تلقائي (تكيفي)", Int.MAX_VALUE)
          )
          detectedHeights.sortedDescending().forEach { h ->
            dynamicOptions.add(QualityOption("${h}p", "${h}p", h))
          }
          availableQualities = dynamicOptions
        }

        // Keep standard language fallbacks if stream doesn't bundle text tracks
        if (detectedSubtitles.size > 1) {
          availableSubtitles = detectedSubtitles
        }
      }
    }

    exoPlayer.addListener(listener)

    onDispose {
      onProgressUpdate?.invoke(currentPositionState.value, totalDurationState.value)
      exoPlayer.removeListener(listener)
      exoPlayer.stop()
      exoPlayer.release()
    }
  }

  // Apply trackSelectionParameters when quality changes
  fun applyQuality(quality: QualityOption) {
    selectedQuality = quality
    if (quality.maxHeight == Int.MAX_VALUE) {
      exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
        .buildUpon()
        .clearVideoSizeConstraints()
        .build()
    } else {
      exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
        .buildUpon()
        .setMaxVideoSize(Int.MAX_VALUE, quality.maxHeight)
        .build()
    }
    showQualitySheet = false
  }

  // Apply Subtitle Track Selection on ExoPlayer
  fun applySubtitle(subtitle: SubtitleOption) {
    selectedSubtitle = subtitle
    if (subtitle.isOff) {
      // Disable text track
      exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
        .buildUpon()
        .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true)
        .clearOverridesOfType(C.TRACK_TYPE_TEXT)
        .build()
    } else {
      val builder = exoPlayer.trackSelectionParameters
        .buildUpon()
        .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, false)
        .setPreferredTextLanguage(subtitle.language)

      // If mapped to a real Media3 TrackGroup, set override
      if (subtitle.trackGroupIndex >= 0 && subtitle.trackGroupIndex < exoPlayer.currentTracks.groups.size) {
        val group = exoPlayer.currentTracks.groups[subtitle.trackGroupIndex].mediaTrackGroup
        builder.setOverrideForType(
          TrackSelectionOverride(group, listOf(subtitle.trackIndex))
        )
      }
      exoPlayer.trackSelectionParameters = builder.build()
    }
    showSubtitleSheet = false
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Black)
  ) {
    // 1. AndroidView Player Surface
    AndroidView(
      factory = { ctx ->
        PlayerView(ctx).apply {
          player = exoPlayer
          useController = false
          layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
          )
        }
      },
      modifier = Modifier
        .fillMaxSize()
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null
        ) {
          areControlsVisible = !areControlsVisible
        }
    )

    // 2. Buffering Spinner
    if (isBuffering) {
      CircularProgressIndicator(
        color = GoldPrimary,
        modifier = Modifier
          .size(50.dp)
          .align(Alignment.Center)
      )
    }

    // 3. Subtitle Cue Overlay Simulation / Confirmation
    if (!selectedSubtitle.isOff) {
      Box(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = if (areControlsVisible) 92.dp else 24.dp)
          .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
          .padding(horizontal = 12.dp, vertical = 4.dp)
      ) {
        Text(
          text = if (selectedSubtitle.language == "ar") "◄ ترجمة شيموتا السينمائية: العربية ►" else "◄ SHIMOTA CC Subtitle: English ►",
          color = Color(0xFFFFFAEE),
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }

    // Error Overlay for missing or unlicensed streams
    if (playbackError != null) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.Black.copy(alpha = 0.94f))
          .padding(24.dp),
        contentAlignment = Alignment.Center
      ) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
        ) {
          Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(AccentRed.copy(alpha = 0.18f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning",
                tint = GoldPrimary,
                modifier = Modifier.size(36.dp)
              )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
              text = "تنبيه البث والترخيص القانوني",
              color = GoldPrimary,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = playbackError!!,
              color = TextWhite,
              fontSize = 14.sp,
              textAlign = TextAlign.Center,
              lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "يلتزم تطبيق SHIMOTA TV بمعايير حقوق الملكية الفكرية وعرض الأعمال المصرح بها فقط.",
              color = TextMuted,
              fontSize = 12.sp,
              textAlign = TextAlign.Center,
              lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Button(
                onClick = { onBack?.invoke() },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DarkBackground),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                  .weight(1f)
                  .height(48.dp)
              ) {
                Text(text = "إغلاق المشغل", fontWeight = FontWeight.Bold, fontSize = 14.sp)
              }

              if (!isUrlInvalid) {
                OutlinedButton(
                  onClick = {
                    playbackError = null
                    isBuffering = true
                    exoPlayer.prepare()
                    exoPlayer.play()
                  },
                  shape = RoundedCornerShape(10.dp),
                  colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite),
                  modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                ) {
                  Text(text = "إعادة المحاولة", fontSize = 13.sp)
                }
              }
            }
          }
        }
      }
    }

    // 4. Interactive Overlay Controls
    AnimatedVisibility(
      visible = areControlsVisible && playbackError == null,
      enter = fadeIn(),
      exit = fadeOut(),
      modifier = Modifier.fillMaxSize()
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            Brush.verticalGradient(
              colors = listOf(
                Color.Black.copy(alpha = 0.75f),
                Color.Transparent,
                Color.Black.copy(alpha = 0.85f)
              )
            )
          )
      ) {
        // Top Bar
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          if (onBack != null) {
            IconButton(onClick = onBack) {
              Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = TextWhite
              )
            }
          } else {
            Spacer(modifier = Modifier.size(24.dp))
          }

          Text(
            text = title,
            color = TextWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier
              .weight(1f)
              .padding(horizontal = 12.dp)
          )

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Subtitle Track Selection Trigger Button
            IconButton(
              onClick = { showSubtitleSheet = true },
              modifier = Modifier
                .clip(CircleShape)
                .background(
                  if (!selectedSubtitle.isOff) GoldPrimary.copy(alpha = 0.25f)
                  else Color.Black.copy(alpha = 0.45f)
                )
            ) {
              Icon(
                imageVector = if (!selectedSubtitle.isOff) Icons.Default.ClosedCaption else Icons.Default.ClosedCaptionDisabled,
                contentDescription = "Select Subtitles",
                tint = if (!selectedSubtitle.isOff) GoldPrimary else TextWhite
              )
            }

            // Quality Selector Trigger Button
            IconButton(
              onClick = { showQualitySheet = true },
              modifier = Modifier
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.45f))
            ) {
              Icon(
                imageVector = Icons.Default.HighQuality,
                contentDescription = "Select Quality",
                tint = GoldPrimary
              )
            }
          }
        }

        // Center Play / Rewind / Fast-Forward
        Row(
          modifier = Modifier.align(Alignment.Center),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(28.dp)
        ) {
          IconButton(
            onClick = {
              val newPos = (exoPlayer.currentPosition - 10000L).coerceAtLeast(0L)
              exoPlayer.seekTo(newPos)
              currentPosition = newPos
            },
            modifier = Modifier
              .size(48.dp)
              .background(Color.Black.copy(alpha = 0.5f), CircleShape)
          ) {
            Icon(
              imageVector = Icons.Default.Replay10,
              contentDescription = "Rewind 10s",
              tint = TextWhite,
              modifier = Modifier.size(30.dp)
            )
          }

          IconButton(
            onClick = {
              if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
            },
            modifier = Modifier
              .size(64.dp)
              .background(
                Brush.linearGradient(listOf(GoldPrimary, GoldAccent)),
                CircleShape
              )
          ) {
            Icon(
              imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
              contentDescription = if (isPlaying) "Pause" else "Play",
              tint = DarkBackground,
              modifier = Modifier.size(38.dp)
            )
          }

          IconButton(
            onClick = {
              val newPos = (exoPlayer.currentPosition + 10000L).coerceAtMost(exoPlayer.duration)
              exoPlayer.seekTo(newPos)
              currentPosition = newPos
            },
            modifier = Modifier
              .size(48.dp)
              .background(Color.Black.copy(alpha = 0.5f), CircleShape)
          ) {
            Icon(
              imageVector = Icons.Default.Forward10,
              contentDescription = "Forward 10s",
              tint = TextWhite,
              modifier = Modifier.size(30.dp)
            )
          }
        }

        // Bottom Progress & Scrubber
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
          Slider(
            value = sliderFraction,
            onValueChange = { frac ->
              isDraggingSlider = true
              sliderFraction = frac
            },
            onValueChangeFinished = {
              isDraggingSlider = false
              val seekMs = (sliderFraction * totalDuration).toLong()
              exoPlayer.seekTo(seekMs)
              currentPosition = seekMs
            },
            colors = SliderDefaults.colors(
              thumbColor = GoldPrimary,
              activeTrackColor = GoldPrimary,
              inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "${formatDuration(currentPosition)} / ${formatDuration(totalDuration)}",
              color = TextWhite,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              // Current Subtitle Pill
              Row(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .clickable { showSubtitleSheet = true }
                  .background(if (!selectedSubtitle.isOff) GoldPrimary.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.15f))
                  .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Subtitles,
                  contentDescription = null,
                  tint = if (!selectedSubtitle.isOff) GoldPrimary else TextMuted,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = if (selectedSubtitle.isOff) "CC Off" else selectedSubtitle.language.uppercase(),
                  color = if (!selectedSubtitle.isOff) GoldPrimary else TextWhite,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }

              // Current Quality Pill
              Row(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .clickable { showQualitySheet = true }
                  .background(Color.White.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Settings,
                  contentDescription = null,
                  tint = GoldPrimary,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = selectedQuality.title,
                  color = GoldPrimary,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    }

    // 5. Quality Selector BottomSheet
    if (showQualitySheet) {
      ModalBottomSheet(
        onDismissRequest = { showQualitySheet = false },
        containerColor = DarkSurfaceElevated,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "اختيار جودة البث (Video Quality)",
              color = GoldPrimary,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold
            )
            Icon(
              imageVector = Icons.Default.HighQuality,
              contentDescription = null,
              tint = GoldPrimary,
              modifier = Modifier.size(24.dp)
            )
          }

          Text(
            text = "اختر دقة العرض المناسبة لسرعة اتصالك:",
            color = TextMuted,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
          )

          availableQualities.forEach { option ->
            val isSelected = selectedQuality == option

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable { applyQuality(option) }
                .background(if (isSelected) DarkSurfaceCard else Color.Transparent)
                .padding(horizontal = 12.dp, vertical = 10.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                  selected = isSelected,
                  onClick = { applyQuality(option) },
                  colors = RadioButtonDefaults.colors(selectedColor = GoldPrimary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(
                    text = option.title,
                    color = if (isSelected) GoldPrimary else TextWhite,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                  )
                  Text(
                    text = option.titleAr,
                    color = TextMuted,
                    fontSize = 11.sp
                  )
                }
              }

              if (isSelected) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = "Selected",
                  tint = GoldPrimary,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(24.dp))
        }
      }
    }

    // 6. Subtitle Track Selection BottomSheet (واجهة تبديل لغات الترجمة)
    if (showSubtitleSheet) {
      ModalBottomSheet(
        onDismissRequest = { showSubtitleSheet = false },
        containerColor = DarkSurfaceElevated,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "اختيار لغة الترجمة (Subtitles / CC)",
              color = GoldPrimary,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold
            )
            Icon(
              imageVector = Icons.Default.Subtitles,
              contentDescription = null,
              tint = GoldPrimary,
              modifier = Modifier.size(24.dp)
            )
          }

          Text(
            text = "قم بتبديل مسار الترجمة أو إيقافها:",
            color = TextMuted,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
          )

          availableSubtitles.forEach { option ->
            val isSelected = selectedSubtitle.id == option.id

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable { applySubtitle(option) }
                .background(if (isSelected) DarkSurfaceCard else Color.Transparent)
                .padding(horizontal = 12.dp, vertical = 10.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                  selected = isSelected,
                  onClick = { applySubtitle(option) },
                  colors = RadioButtonDefaults.colors(selectedColor = GoldPrimary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = option.displayName,
                  color = if (isSelected) GoldPrimary else TextWhite,
                  fontSize = 14.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              }

              if (isSelected) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = "Selected",
                  tint = GoldPrimary,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(24.dp))
        }
      }
    }
  }
}

private fun formatDuration(millis: Long): String {
  val totalSeconds = (millis / 1000).coerceAtLeast(0)
  val hours = totalSeconds / 3600
  val minutes = (totalSeconds % 3600) / 60
  val seconds = totalSeconds % 60
  return if (hours > 0) {
    String.format("%02d:%02d:%02d", hours, minutes, seconds)
  } else {
    String.format("%02d:%02d", minutes, seconds)
  }
}
