package com.example.following_subscriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.following_subscriptions.ui.theme.CreamWhite
import com.example.following_subscriptions.ui.theme.DeepBlue
import com.example.following_subscriptions.ui.theme.DuricFont
import com.example.following_subscriptions.ui.theme.LletreFont
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalStdlibApi::class)
@Composable
fun SettingScreen() {
    var userName by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val userEmail = auth.currentUser?.email ?: "example@gmail.com"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "НАСТРОЙКИ",
            color = CreamWhite,
            fontSize = 40.sp,
            fontFamily = LletreFont,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 30.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(100.dp),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF1B2735)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profil),
                    contentDescription = null,
                    tint = CreamWhite,
                    modifier = Modifier.padding(20.dp)
                )
            }
            TextField(
                value = userName,
                onValueChange = { userName = it },
                label = {
                    Text(
                        "Имя",
                        color = CreamWhite.copy(alpha = 0.5f),
                        fontFamily = DuricFont
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = CreamWhite,
                    unfocusedIndicatorColor = CreamWhite.copy(alpha = 0.5f),
                    focusedTextColor = CreamWhite,
                    unfocusedTextColor = CreamWhite
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = DuricFont,
                    fontSize = 18.sp
                )
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Аккаунт",
            color = CreamWhite,
            fontSize = 25.sp,
            fontFamily = LletreFont,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        SettingItem(icon = Icons.Default.Email, title = "Email", value = userEmail)
        SettingItem(icon = Icons.Default.Lock, title = "Пароль", value = "********")
        Text(
            text = "Дополнительное",
            color = CreamWhite,
            fontSize = 25.sp,
            fontFamily = LletreFont,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
        )
        SettingItem(icon = Icons.Default.Notifications, title = "Уведомления", value = "Вкл")
    }
}



@Composable
fun SettingItem(icon: ImageVector, title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = CreamWhite,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(13.dp))
        Text(
            text = title,
            color = CreamWhite,
            fontSize = 18.sp,
            fontFamily = DuricFont,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = CreamWhite.copy(alpha = 0.6f),
            fontSize = 16.sp,
            fontFamily = DuricFont
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = CreamWhite.copy(alpha = 0.6f)
        )
    }
}


@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingScreen(
        )
    }
}