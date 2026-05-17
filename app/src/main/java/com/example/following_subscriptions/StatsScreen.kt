package com.example.following_subscriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.following_subscriptions.ui.theme.CreamWhite
import com.example.following_subscriptions.ui.theme.DarkBlue
import com.example.following_subscriptions.ui.theme.DeepBlue
import com.example.following_subscriptions.ui.theme.DuricFont


@Composable
fun StatsScreen (viewModel: MainViewModel){
    val subscriptionsState = viewModel.subscriptions.observeAsState(emptyList())
    val subscriptionsCount = subscriptionsState.value.size
    val totalAmount = viewModel.totalExpenses

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Статистика расходов",
            color = CreamWhite,
            fontSize = 24.sp,
            fontFamily = DuricFont,
            modifier = Modifier.padding(top =12.dp, bottom= 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkBlue),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Общие расходы в месяц",
                    color = CreamWhite.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    fontFamily = DuricFont
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${String.format("%.2f", totalAmount)} Р",
                    color = CreamWhite,
                    fontSize = 36.sp,
                    fontFamily = DuricFont,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkBlue.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(16.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Активных подписок:",
                    color = CreamWhite,
                    fontSize = 16.sp,
                    fontFamily = DuricFont
                )
                Text(
                    text = "$subscriptionsCount",
                    color = CreamWhite,
                    fontSize = 18.sp,
                    fontFamily = DuricFont,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}