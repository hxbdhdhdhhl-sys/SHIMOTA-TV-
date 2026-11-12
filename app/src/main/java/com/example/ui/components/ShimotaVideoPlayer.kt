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
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
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
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.ui.theme.AccentRed
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import kotlinx.coroutines.delay

@OptIn(UnstableApi::class)
@ExperimentalMaterial3Api
@Composable
fun ShimotaVideoPlayer(
  videoUrl: String,
  title: String,
  subtitle: String = "",
  initialPositionMs: Long = 0L,
  onClose: () -> Unit,
  onSaveProgress: (positionMs: Long, durationMs: Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  var isPlaying by remember { mutableStateOf(true) }
  var isBuffering by remember { mutableStateOf(true) }
  var currentPosition by remember { mutableLongStateOf(initialPositionMs) }
  var totalDuration by remember { mutableLongStateOf(0L) }
  var areControlsVisible by remember { mutableStateOf(true) }
  var isDraggingSlider by remember { mutableStateOf(false) }
  var sliderPosition by remember { mutableFloatStateOf(0f) }

  var resizeMode by remember { mutableStateOf(AspectRatioFrameLayout.RESIZE_MODE_FIT) }
  var showQualitySheet by remember { mutableStateOf(false) }
  var showSubtitleSheet by remember { mutableStateOf(false) }
  var selectedQuality by remember { mutableStateOf("تلقائي (Auto 1080p)") }
  var selectedSubtitle by remember { mutableStateOf("العربية (Arabic)") }

  // Auto-hide controls timer
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

  // Periodic progress update
  LaunchedEffect(exoPlayer, playbackError) {
    while (playbackError == null) {
      if (exoPlayer.isPlaying) {
        val pos = exoPlayer.currentPosition
        val dur = exoPlayer.duration.coerceAtLeast(0L)
        currentPosition = pos
        totalDuration = dur
        if (!isDraggingSlider && dur > 0) {
          sliderPosition = (pos.toFloat() / dur.toFloat()).coerceIn(0f, 1f)
        }
        onSaveProgress(pos, dur)
      }
      delay(1000)
    }
  }

  // Player listener
  DisposableEffect(exoPlayer) {
    val listener = object : Player.Listener {
      override fun onPlaybackStateChanged(playbackState: Int) {
        isBuffering = playbackState == Player.STATE_BUFFERING
        if (playbackState == Player.STATE_READY) {
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
    }
    exoPlayer.addListener(listener)

    onDispose {
      onSaveProgress(currentPositionState.value, totalDurationState.value)
      exoPlayer.removeListener(listener)
      exoPlayer.stop()
      exoPlayer.release()
    }
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Black)
  ) {
    // Video Surface
    AndroidView(
      factory = { ctx ->
        PlayerView(ctx).apply {
          player = exoPlayer
          useController = false
          layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
          )
          this.resizeMode = resizeMode
        }
      },
      update = { playerView ->
        playerView.resizeMode = resizeMode
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

    // Buffering indicator
    if (isBuffering) {
      CircularProgressIndicator(
        color = GoldPrimary,
        modifier = Modifier
          .size(54.dp)
          .align(Alignment.Center)
      )
    }

    // Selected Subtitle Display Simulation
    if (selectedSubtitle != "إيقاف (Off)") {
      Box(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = if (areControlsVisible) 90.dp else 24.dp)
          .background(Color.Black.copy(alpha = 0.65f), RoundedCornerShape(4.dp))
          .padding(horizontal = 12.dp, vertical = 4.dp)
      ) {
        Text(
          text = if (selectedSubtitle.contains("العربية")) "◄ ترجمة شيموتا السينمائية معتمدة ►" else "◄ SHIMOTA Cinema CC Subtitle ►",
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
              text = "يلتزم تطبيق SHIMOTA TV بحماية حقوق الملكية الفكرية وعرض الأعمال المصرح بها فقط.",
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
                onClick = onClose,
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

    // Overlay Controls
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
        // TOP BAR
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
            .padding(horizontal = 16.dp, vertical = 20.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          IconButton(
            onClick = {
              onSaveProgress(currentPosition, totalDuration)
              onClose()
            }
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close Player",
              tint = TextWhite,
              modifier = Modifier.size(28.dp)
            )
          }

          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
          ) {
            Text(
              text = title,
              color = TextWhite,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              maxLines = 1
            )
            if (subtitle.isNotEmpty()) {
              Text(
                text = subtitle,
                color = GoldPrimary,
                fontSize = 12.sp,
                maxLines = 1
              )
            }
          }

          Row {
            // Quality Selector
            IconButton(onClick = { showQualitySheet = true }) {
              Icon(
                imageVector = Icons.Default.HighQuality,
                contentDescription = "Quality",
                tint = GoldPrimary
              )
            }
            // Subtitle Selector
            IconButton(onClick = { showSubtitleSheet = true }) {
              Icon(
                imageVector = Icons.Default.Subtitles,
                contentDescription = "Subtitles",
                tint = TextWhite
              )
            }
          }
        }

        // CENTER PLAY / PAUSE & SEEK BUTTONS
        Row(
          modifier = Modifier.align(Alignment.Center),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
          // Replay 10s
          IconButton(
            onClick = {
              val newPos = (exoPlayer.currentPosition - 10000L).coerceAtLeast(0L)
              exoPlayer.seekTo(newPos)
              currentPosition = newPos
            },
            modifier = Modifier
              .size(50.dp)
              .background(Color.Black.copy(alpha = 0.5f), CircleShape)
          ) {
            Icon(
              imageVector = Icons.Default.Replay10,
              contentDescription = "Rewind 10s",
              tint = TextWhite,
              modifier = Modifier.size(32.dp)
            )
          }

          // Play / Pause Button
          IconButton(
            onClick = {
              if (exoPlayer.isPlaying) {
                exoPlayer.pause()
              } else {
                exoPlayer.play()
              }
            },
            modifier = Modifier
              .size(68.dp)
              .background(
                Brush.linearGradient(listOf(GoldPrimary, GoldAccent)),
                CircleShape
              )
          ) {
            Icon(
              imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
              contentDescription = if (isPlaying) "Pause" else "Play",
              tint = DarkBackground,
              modifier = Modifier.size(40.dp)
            )
          }

          // Forward 10s
          IconButton(
            onClick = {
              val newPos = (exoPlayer.currentPosition + 10000L).coerceAtMost(exoPlayer.duration)
              exoPlayer.seekTo(newPos)
              currentPosition = newPos
            },
            modifier = Modifier
              .size(50.dp)
              .background(Color.Black.copy(alpha = 0.5f), CircleShape)
          ) {
            Icon(
              imageVector = Icons.Default.Forward10,
              contentDescription = "Forward 10s",
              tint = TextWhite,
              modifier = Modifier.size(32.dp)
            )
          }
        }

        // BOTTOM BAR
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
          // Slider
          Slider(
            value = sliderPosition,
            onValueChange = { frac ->
              isDraggingSlider = true
              sliderPosition = frac
            },
            onValueChangeFinished = {
              isDraggingSlider = false
              val seekMs = (sliderPosition * totalDuration).toLong()
              exoPlayer.seekTo(seekMs)
              currentPosition = seekMs
            },
            colors = SliderDefaults.colors(
              thumbColor = GoldPrimary,
              activeTrackColor = GoldPrimary,
              inactiveTrackColor = Color.White.copy(alpha = 0.25f)
            ),
            modifier = Modifier.fillMaxWidth()
          )

          // Time and Screen Controls
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "${formatTime(currentPosition)} / ${formatTime(totalDuration)}",
              color = TextWhite,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
              // Resize Mode Toggle
              IconButton(
                onClick = {
                  resizeMode = if (resizeMode == AspectRatioFrameLayout.RESIZE_MODE_FIT) {
                    AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                  } else {
                    AspectRatioFrameLayout.RESIZE_MODE_FIT
                  }
                }
              ) {
                Icon(
                  imageVector = Icons.Default.AspectRatio,
                  contentDescription = "Aspect Ratio",
                  tint = if (resizeMode == AspectRatioFrameLayout.RESIZE_MODE_ZOOM) GoldPrimary else TextWhite
                )
              }
            }
          }
        }
      }
    }

    // QUALITY SELECTOR SHEET
    if (showQualitySheet) {
      ModalBottomSheet(
        onDismissRequest = { showQualitySheet = false },
        containerColor = DarkSurfaceElevated
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Text(
            text = "اختيار جودة البث (Video Quality)",
            color = GoldPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(12.dp))

          val qualities = listOf(
            "تلقائي (Auto 1080p)",
            "4K Ultra HD (VIP Only)",
            "1080p Full HD",
            "720p HD",
            "480p SD (توفير البيانات)"
          )

          fun applyQualitySelection(q: String) {
            selectedQuality = q
            when {
              q.contains("Auto", ignoreCase = true) -> {
                exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
                  .buildUpon()
                  .clearVideoSizeConstraints()
                  .build()
              }
              q.contains("4K", ignoreCase = true) -> {
                exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
                  .buildUpon()
                  .setMaxVideoSize(3840, 2160)
                  .build()
              }
              q.contains("1080p", ignoreCase = true) -> {
                exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
                  .buildUpon()
                  .setMaxVideoSize(1920, 1080)
                  .build()
              }
              q.contains("720p", ignoreCase = true) -> {
                exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
                  .buildUpon()
                  .setMaxVideoSize(1280, 720)
                  .build()
              }
              q.contains("480p", ignoreCase = true) -> {
                exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
                  .buildUpon()
                  .setMaxVideoSize(854, 480)
                  .build()
              }
            }
            showQualitySheet = false
          }

          qualities.forEach { q ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  applyQualitySelection(q)
                }
                .padding(vertical = 10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              RadioButton(
                selected = selectedQuality == q,
                onClick = {
                  applyQualitySelection(q)
                },
                colors = RadioButtonDefaults.colors(selectedColor = GoldPrimary)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = q,
                color = if (selectedQuality == q) GoldPrimary else TextWhite,
                fontSize = 15.sp,
                fontWeight = if (selectedQuality == q) FontWeight.Bold else FontWeight.Normal
              )
            }
          }
        }
      }
    }

    // SUBTITLE SELECTOR SHEET
    if (showSubtitleSheet) {
      ModalBottomSheet(
        onDismissRequest = { showSubtitleSheet = false },
        containerColor = DarkSurfaceElevated
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Text(
            text = "اختيار الترجمة (Subtitles)",
            color = GoldPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(12.dp))

          val subtitles = listOf(
            "العربية (Arabic)",
            "English (الإنجليزية)",
            "العربية (مع وصف صوتي)",
            "إيقاف (Off)"
          )

          subtitles.forEach { sub ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  selectedSubtitle = sub
                  showSubtitleSheet = false
                }
                .padding(vertical = 10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              RadioButton(
                selected = selectedSubtitle == sub,
                onClick = {
                  selectedSubtitle = sub
                  showSubtitleSheet = false
                },
                colors = RadioButtonDefaults.colors(selectedColor = GoldPrimary)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = sub,
                color = if (selectedSubtitle == sub) GoldPrimary else TextWhite,
                fontSize = 15.sp,
                fontWeight = if (selectedSubtitle == sub) FontWeight.Bold else FontWeight.Normal
              )
            }
          }
        }
      }
    }
  }
}

private fun formatTime(millis: Long): String {
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
