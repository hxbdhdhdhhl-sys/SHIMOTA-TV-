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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppNotification
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.Strings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun NotificationsScreen(
  notifications: List<AppNotification>,
  appLanguage: String,
  onBack: () -> Unit,
  onNotificationClick: (String?) -> Unit,
  onMarkAllRead: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isAr = appLanguage == "ar"

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground)
      .padding(top = 16.dp)
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = Strings.get("notifications", appLanguage),
          color = GoldPrimary,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold
        )
      }

      TextButton(onClick = onMarkAllRead) {
        Icon(imageVector = Icons.Default.DoneAll, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = Strings.get("mark_all_read", appLanguage), color = GoldPrimary, fontSize = 12.sp)
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    if (notifications.isEmpty()) {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        Text(text = Strings.get("no_notifications", appLanguage), color = TextMuted, fontSize = 14.sp)
      }
    } else {
      LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(notifications) { notif ->
          val title = if (isAr) notif.titleAr else notif.title
          val body = if (isAr) notif.bodyAr else notif.body

          Card(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onNotificationClick(notif.mediaId) },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.Top
            ) {
              Box(
                modifier = Modifier
                  .size(38.dp)
                  .clip(CircleShape)
                  .background(GoldPrimary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Notifications,
                  contentDescription = null,
                  tint = GoldPrimary,
                  modifier = Modifier.size(20.dp)
                )
              }

              Spacer(modifier = Modifier.width(12.dp))

              Column(modifier = Modifier.weight(1f)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = title,
                    color = TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = notif.timeAgo,
                    color = TextMuted,
                    fontSize = 11.sp
                  )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                  text = body,
                  color = TextWhite.copy(alpha = 0.85f),
                  fontSize = 13.sp,
                  lineHeight = 18.sp
                )
              }
            }
          }
        }
      }
    }
  }
}
