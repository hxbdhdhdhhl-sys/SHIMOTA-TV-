package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserAccount
import com.example.ui.theme.AccentRed
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.Strings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun ProfileAndSettingsScreen(
  currentUser: UserAccount?,
  appLanguage: String,
  onLanguageChange: (String) -> Unit,
  onOpenAdmin: () -> Unit,
  onOpenVip: () -> Unit,
  onOpenNotifications: () -> Unit,
  onLogin: (String, String) -> Boolean,
  onRegister: (String, String, String) -> Boolean,
  onResetPassword: (String) -> Boolean,
  onLogout: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isAr = appLanguage == "ar"

  var showAuthDialog by remember { mutableStateOf(false) }
  var authMode by remember { mutableStateOf("login") } // "login", "register", "forgot"

  // Auth Inputs
  var emailInput by remember { mutableStateOf("") }
  var passInput by remember { mutableStateOf("") }
  var nameInput by remember { mutableStateOf("") }
  var authMessage by remember { mutableStateOf<String?>(null) }

  // Settings states
  var streamQualityPref by remember { mutableStateOf("1080p Full HD") }
  var notificationsEnabled by remember { mutableStateOf(true) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground)
      .verticalScroll(rememberScrollState())
      .padding(16.dp, 20.dp, 16.dp, 90.dp)
  ) {
    Text(
      text = Strings.get("profile", appLanguage),
      color = GoldPrimary,
      fontSize = 24.sp,
      fontWeight = FontWeight.ExtraBold
    )

    Spacer(modifier = Modifier.height(16.dp))

    // USER CARD
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(62.dp)
            .clip(CircleShape)
            .background(
              Brush.radialGradient(listOf(GoldPrimary, GoldDark))
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = DarkBackground,
            modifier = Modifier.size(36.dp)
          )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
          if (currentUser != null) {
            Text(
              text = currentUser.name,
              color = TextWhite,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = currentUser.email,
              color = TextMuted,
              fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .background(
                    if (currentUser.isVip) Brush.horizontalGradient(listOf(GoldPrimary, GoldDark))
                    else Brush.horizontalGradient(listOf(Color(0xFF475569), Color(0xFF334155))),
                    RoundedCornerShape(4.dp)
                  )
                  .padding(horizontal = 8.dp, vertical = 2.dp)
              ) {
                Text(
                  text = currentUser.vipTier,
                  color = if (currentUser.isVip) DarkBackground else TextWhite,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold
                )
              }

              if (currentUser.isAdmin) {
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                  modifier = Modifier
                    .background(Color(0xFF3B82F6), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "ADMIN",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          } else {
            Text(
              text = if (isAr) "مرحباً بك كزائر" else "Welcome Guest",
              color = TextWhite,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = if (isAr) "سجل الدخول لحفظ تقدم المشاهدة والمزامنة" else "Sign in to sync watchlist & watch progress",
              color = TextMuted,
              fontSize = 12.sp
            )
          }
        }

        if (currentUser == null) {
          Button(
            onClick = {
              authMode = "login"
              showAuthDialog = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DarkBackground),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text(text = Strings.get("login", appLanguage), fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // VIP CARD PROMOTION
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onOpenVip),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.horizontalGradient(
              listOf(Color(0xFF261E0E), DarkSurfaceCard)
            )
          )
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.WorkspacePremium,
          contentDescription = null,
          tint = GoldPrimary,
          modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = Strings.get("vip", appLanguage),
            color = GoldPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = Strings.get("vip_subtitle", appLanguage),
            color = TextMuted,
            fontSize = 11.sp
          )
        }
        Icon(
          imageVector = Icons.Default.ArrowForwardIos,
          contentDescription = null,
          tint = GoldPrimary,
          modifier = Modifier.size(16.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // SETTINGS SECTION
    Text(
      text = Strings.get("settings", appLanguage),
      color = GoldPrimary,
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(10.dp))

    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        // Language Switch (AR / EN)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              val nextLang = if (appLanguage == "ar") "en" else "ar"
              onLanguageChange(nextLang)
            }
            .padding(vertical = 10.dp, horizontal = 4.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = Strings.get("language", appLanguage), color = TextWhite, fontSize = 14.sp)
          }
          Text(
            text = if (appLanguage == "ar") "العربية (RTL)" else "English (LTR)",
            color = GoldPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Notifications Screen shortcut
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOpenNotifications)
            .padding(vertical = 10.dp, horizontal = 4.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = Strings.get("notifications", appLanguage), color = TextWhite, fontSize = 14.sp)
          }
          Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Streaming Quality Preference
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 4.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.HighQuality, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = Strings.get("quality", appLanguage), color = TextWhite, fontSize = 14.sp)
          }
          Text(text = streamQualityPref, color = GoldPrimary, fontSize = 13.sp)
        }
      }
    }

    // ADMIN DASHBOARD BUTTON (Accessible for admin or to test admin management)
    Spacer(modifier = Modifier.height(20.dp))
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onOpenAdmin),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.AdminPanelSettings,
          contentDescription = null,
          tint = GoldPrimary,
          modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = Strings.get("admin_panel", appLanguage),
            color = GoldPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = if (isAr) "إدارة محتوى الأفلام والمسلسلات والحلقات وروابط البث" else "Manage movies, series, episodes & stream links",
            color = TextMuted,
            fontSize = 11.sp
          )
        }
        Icon(
          imageVector = Icons.Default.ArrowForwardIos,
          contentDescription = null,
          tint = GoldPrimary,
          modifier = Modifier.size(16.dp)
        )
      }
    }

    // LOGOUT / SIGN IN
    if (currentUser != null) {
      Spacer(modifier = Modifier.height(20.dp))
      OutlinedButton(
        onClick = onLogout,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentRed),
        modifier = Modifier
          .fillMaxWidth()
          .height(46.dp)
      ) {
        Icon(imageVector = Icons.Default.Logout, contentDescription = null, tint = AccentRed, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = Strings.get("logout", appLanguage), color = AccentRed, fontSize = 14.sp, fontWeight = FontWeight.Bold)
      }
    }
  }

  // AUTH MODAL DIALOG (Login, Register, Forgot Password)
  if (showAuthDialog) {
    AlertDialog(
      onDismissRequest = {
        showAuthDialog = false
        authMessage = null
      },
      containerColor = DarkSurfaceElevated,
      title = {
        Text(
          text = when (authMode) {
            "register" -> Strings.get("register", appLanguage)
            "forgot" -> Strings.get("forgot_password", appLanguage)
            else -> Strings.get("login", appLanguage)
          },
          color = GoldPrimary,
          fontWeight = FontWeight.Bold
        )
      },
      text = {
        Column(modifier = Modifier.fillMaxWidth()) {
          if (authMode == "register") {
            OutlinedTextField(
              value = nameInput,
              onValueChange = { nameInput = it },
              label = { Text(text = Strings.get("name", appLanguage), color = TextMuted) },
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = GoldPrimary),
              modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
          }

          OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            label = { Text(text = Strings.get("email", appLanguage), color = TextMuted) },
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = GoldPrimary),
            modifier = Modifier.fillMaxWidth()
          )

          if (authMode != "forgot") {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
              value = passInput,
              onValueChange = { passInput = it },
              label = { Text(text = Strings.get("password", appLanguage), color = TextMuted) },
              visualTransformation = PasswordVisualTransformation(),
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = GoldPrimary),
              modifier = Modifier.fillMaxWidth()
            )
          }

          if (authMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = authMessage!!, color = GoldPrimary, fontSize = 12.sp)
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            if (authMode == "login") {
              Text(
                text = Strings.get("forgot_password", appLanguage),
                color = TextMuted,
                fontSize = 12.sp,
                modifier = Modifier.clickable { authMode = "forgot" }
              )
              Text(
                text = Strings.get("register", appLanguage),
                color = GoldPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { authMode = "register" }
              )
            } else {
              Text(
                text = Strings.get("login", appLanguage),
                color = GoldPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { authMode = "login" }
              )
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            when (authMode) {
              "login" -> {
                if (emailInput.isNotBlank()) {
                  onLogin(emailInput, passInput)
                  showAuthDialog = false
                }
              }
              "register" -> {
                if (emailInput.isNotBlank() && nameInput.isNotBlank()) {
                  onRegister(nameInput, emailInput, passInput)
                  showAuthDialog = false
                }
              }
              "forgot" -> {
                if (emailInput.isNotBlank()) {
                  onResetPassword(emailInput)
                  authMessage = Strings.get("reset_link_sent", appLanguage)
                }
              }
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DarkBackground)
        ) {
          Text(text = Strings.get("submit", appLanguage), fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showAuthDialog = false }) {
          Text(text = Strings.get("cancel", appLanguage), color = TextMuted)
        }
      }
    )
  }
}
