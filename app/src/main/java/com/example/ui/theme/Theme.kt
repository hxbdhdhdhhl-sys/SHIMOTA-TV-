package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ShimotaDarkColorScheme = darkColorScheme(
  primary = GoldPrimary,
  onPrimary = DarkBackground,
  primaryContainer = GoldDark,
  onPrimaryContainer = TextWhite,
  secondary = GoldAccent,
  onSecondary = DarkBackground,
  secondaryContainer = DarkSurfaceElevated,
  onSecondaryContainer = GoldLight,
  tertiary = AccentRed,
  onTertiary = Color.White,
  background = DarkBackground,
  onBackground = TextWhite,
  surface = DarkSurface,
  onSurface = TextWhite,
  surfaceVariant = DarkSurfaceCard,
  onSurfaceVariant = TextMuted,
  outline = GoldPrimary.copy(alpha = 0.35f),
  outlineVariant = DarkBorderSubtle,
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = ShimotaDarkColorScheme,
    typography = Typography,
    content = content
  )
}
