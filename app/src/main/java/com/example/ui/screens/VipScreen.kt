package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserAccount
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkBorderSubtle
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.Strings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun VipScreen(
  currentUser: UserAccount?,
  appLanguage: String,
  onSubscribe: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val isAr = appLanguage == "ar"
  var selectedTier by remember { mutableIntStateOf(1) } // Default to Gold

  val tiers = listOf(
    VipPlan("Silver Plan", "الباقة الفضية", "$4.99", if (isAr) "/ شهرياً" else "/ month", "HD 1080p • بدون إعلانات • جهازين"),
    VipPlan("Gold VIP", "الباقة الذهبية (الأكثر طلباً)", "$39.99", if (isAr) "/ سنوياً (توفير 35%)" else "/ year (Save 35%)", "4K Ultra HD • تحميل غير محدود • 4 أجهزة • أولوية العروض"),
    VipPlan("Diamond VIP", "الباقة الماسية مدى الحياة", "$89.99", if (isAr) "دفعة واحدة للأبد" else "One-time payment", "4K HDR + Dolby Atmos • وصول VIP كامل مدى الحياة لجميع المنصات")
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground)
      .verticalScroll(rememberScrollState())
      .padding(16.dp, 16.dp, 16.dp, 90.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Top VIP Emblem
    Box(
      modifier = Modifier
        .size(70.dp)
        .clip(CircleShape)
        .background(
          Brush.radialGradient(listOf(GoldPrimary, GoldDark))
        ),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.WorkspacePremium,
        contentDescription = "VIP",
        tint = DarkBackground,
        modifier = Modifier.size(44.dp)
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Text(
      text = Strings.get("vip_upgrade_title", appLanguage),
      color = GoldPrimary,
      fontSize = 22.sp,
      fontWeight = FontWeight.ExtraBold,
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = Strings.get("vip_subtitle", appLanguage),
      color = TextMuted,
      fontSize = 13.sp,
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(20.dp))

    // VIP FEATURES LIST
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        VipFeatureRow(Strings.get("vip_feature_1", appLanguage))
        Spacer(modifier = Modifier.height(10.dp))
        VipFeatureRow(Strings.get("vip_feature_2", appLanguage))
        Spacer(modifier = Modifier.height(10.dp))
        VipFeatureRow(Strings.get("vip_feature_3", appLanguage))
        Spacer(modifier = Modifier.height(10.dp))
        VipFeatureRow(Strings.get("vip_feature_4", appLanguage))
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // PRICING TIERS
    tiers.forEachIndexed { index, plan ->
      val isSelected = selectedTier == index
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { selectedTier = index }
          .border(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) GoldPrimary else DarkBorderSubtle,
            shape = RoundedCornerShape(14.dp)
          ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isSelected) Color(0xFF231E12) else DarkSurfaceCard
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = if (isAr) plan.titleAr else plan.titleEn,
                color = if (isSelected) GoldPrimary else TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = plan.features,
                color = TextMuted,
                fontSize = 12.sp
              )
            }

            Column(horizontalAlignment = Alignment.End) {
              Text(
                text = plan.price,
                color = GoldPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = plan.period,
                color = TextMuted,
                fontSize = 11.sp
              )
            }
          }
        }
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    Spacer(modifier = Modifier.height(10.dp))

    // SUBSCRIBE BUTTON
    Button(
      onClick = {
        val chosen = tiers[selectedTier]
        onSubscribe(if (isAr) chosen.titleAr else chosen.titleEn)
      },
      colors = ButtonDefaults.buttonColors(
        containerColor = GoldPrimary,
        contentColor = DarkBackground
      ),
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
    ) {
      Icon(imageVector = Icons.Default.Diamond, contentDescription = null, modifier = Modifier.size(20.dp))
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = Strings.get("subscribe_now", appLanguage),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
      )
    }

    if (currentUser?.isVip == true) {
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = "✓ أنت مشترك بالفعل في باقة ${currentUser.vipTier}",
        color = GoldAccent,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
      )
    }
  }
}

@Composable
private fun VipFeatureRow(text: String) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Icon(
      imageVector = Icons.Default.CheckCircle,
      contentDescription = null,
      tint = GoldPrimary,
      modifier = Modifier.size(18.dp)
    )
    Spacer(modifier = Modifier.width(10.dp))
    Text(
      text = text,
      color = TextWhite,
      fontSize = 13.sp
    )
  }
}

private data class VipPlan(
  val titleEn: String,
  val titleAr: String,
  val price: String,
  val period: String,
  val features: String
)
