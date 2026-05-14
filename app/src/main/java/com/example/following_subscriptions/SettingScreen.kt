package com.example.following_subscriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.following_subscriptions.ui.theme.CreamWhite
import com.example.following_subscriptions.ui.theme.DeepBlue
import com.example.following_subscriptions.ui.theme.DuricFont
import com.example.following_subscriptions.ui.theme.LletreFont
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource



@OptIn(ExperimentalStdlibApi:: class)
@Composable
fun SettingScreen(){
    var userName by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val userEmail = auth.currentUser?.email ?: "example@gmail.com"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue)
            .padding(horizontal = 24.dp)
    ){
        Text(
            text = "НАСТРОЙКИ",
            color = CreamWhite,
            fontSize = 40.sp,
            fontFamily =  LletreFont,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 30.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Row ( modifier = Modifier.fillMaxWidth(),
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
                onValueChange = { userName = it},
                label = { Text("Имя",
                    color = CreamWhite.copy(alpha = 0.5f),
                    fontFamily = DuricFont
                )},
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = CreamWhite,
                    unfocusedIndicatorColor = CreamWhite.copy(alpha = 0.5f),
                    focusedTextColor = CreamWhite,
                    unfocusedTextColor = CreamWhite
                ),
                textStyle = LocalTextStyle.current.copy(fontFamily = DuricFont, fontSize = 18.sp)
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

        Icon(
            painter = painterResource(id = R.drawable.ic_mail),
            contentDescription = null,
            tint = CreamWhite,
            modifier = Modifier.padding(40.dp)
        )
    }
    Spacer(modifier = Modifier.height(40.dp))



}

@Composable
fun SettingSectionTitle(title:String){
    Text(
        text = title,
        color = CreamWhite,
        fontSize = 20.sp,
        fontFamily = DuricFont,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical =12.dp)
    )
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingScreen()
    }
}