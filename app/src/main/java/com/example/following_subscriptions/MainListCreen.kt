package com.example.following_subscriptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.following_subscriptions.ui.theme.CreamWhite
import com.example.following_subscriptions.ui.theme.DarkBlue
import com.example.following_subscriptions.ui.theme.DeepBlue
import com.example.following_subscriptions.ui.theme.DuricFont
import com.example.following_subscriptions.ui.theme.InactiveGray
import com.example.following_subscriptions.ui.theme.LletreFont

data class Subscription(
    val id: Int,
    val name: String,
    val category: String,
    val date: String,
    val price: String,
    val iconRes: Int,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainListScreen(
    startScreen: String = "main", onNavigate: (String) -> Unit
) {
    val subscriptions = remember { mutableStateListOf<Subscription>() }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Scaffold(containerColor = DeepBlue, bottomBar = {
        SubscriptionBottomBar(
            currentScreen = startScreen, onTabClick = { tab ->
                onNavigate(tab)
            })
    }, floatingActionButton = {
        if (startScreen == "main") {
            FloatingActionButton(
                onClick = { showSheet = true },
                containerColor = Color.Black,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить", tint = CreamWhite)
            }
        }
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (startScreen) {
                "main" -> {
                    MainListContent(subscriptions)
                }

                "setting" -> {
                    SettingScreen()
                }

                "stats" -> {
                    Text(
                        "Статистика",
                        fontSize = 30.sp,
                        color = CreamWhite,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            containerColor = DeepBlue,
            dragHandle = { BottomSheetDefaults.DragHandle(color = CreamWhite) },
            sheetState = sheetState
        ) {
            AddSubscriptionCard(onAddClick = { name, category, price, date, icon, color ->
                subscriptions.add(
                    Subscription(
                        id = subscriptions.size,
                        name = name,
                        category = category,
                        date = date,
                        price = price,
                        iconRes = icon,
                        color = color
                    )
                )
                showSheet = false
            })
        }
    }
}

@Composable
fun MainListContent(subscription: List<Subscription>){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ){
        Text(
            text = "ВАШИ ПОДПИСКИ",
            color = CreamWhite,
            fontSize = 40.sp,
            fontFamily = LletreFont,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp),
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier.padding(top = 10.dp, bottom =16.dp)
                .size(40.dp)
                .background(Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ){
            Icon(Icons.Default.MoreVert, contentDescription = null, tint = CreamWhite)
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom =32.dp)
        ) {
            items(subscription){ sub->
                SubscriptionItem(sub)
            }
        }
    }
}


@Composable
fun SubscriptionItem(sub: Subscription) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = sub.color)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = sub.iconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = sub.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(text = sub.category, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = sub.date, color = Color.White, fontSize = 14.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Switch(
                    checked = true, onCheckedChange = {}, colors = SwitchDefaults.colors(
                        checkedThumbColor = CreamWhite,
                        checkedTrackColor = Color.Black.copy(alpha = 0.3f)
                    )
                )
                Text(text = "${sub.price} р/мес", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SubscriptionBottomBar(
    currentScreen: String, onTabClick: (String) -> Unit
) {
    val tabs = listOf(
        Triple("stats", "Статистика", R.drawable.ic_stats),
        Triple("main", "Подписка", R.drawable.ic_subs),
        Triple("setting", "Настройки", R.drawable.ic_settings)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEach { (id, text, iconRes) ->
            val isActive = currentScreen == id
            val contentColor = if (isActive) CreamWhite else InactiveGray

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onTabClick(id) }
                    .padding(8.dp)) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = text,
                    tint = contentColor,
                    modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = text, color = contentColor, fontSize = 12.sp, fontFamily = DuricFont
                )
            }

        }
    }
}
