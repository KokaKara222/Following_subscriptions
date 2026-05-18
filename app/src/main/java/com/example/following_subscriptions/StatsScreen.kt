package com.example.following_subscriptions

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.following_subscriptions.ui.theme.CreamWhite
import com.example.following_subscriptions.ui.theme.DarkBlue
import com.example.following_subscriptions.ui.theme.DeepBlue
import com.example.following_subscriptions.ui.theme.DuricFont
import com.example.following_subscriptions.ui.theme.InactiveGray


@Composable
fun StatsScreen (viewModel: MainViewModel){

    val subscriptions by viewModel.subscriptions.observeAsState(emptyList())
    val totalAmount by viewModel.totalExpenses.observeAsState(initial = 0.0)

    val activeSubs = subscriptions.filter { it.isActive }
    val subscriptionsCount = activeSubs.size


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Статистика расходов",
            color = CreamWhite,
            fontSize = 24.sp,
            fontFamily = DuricFont,
            modifier = Modifier.padding(top =12.dp, bottom= 24.dp)
        )

        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.Center
        ){
            Canvas(modifier = Modifier.fillMaxSize()){
                val strokeWidth = 22.dp.toPx()

                if (totalAmount == 0.0 || activeSubs.isEmpty()){
                    drawArc(
                        color = InactiveGray.copy(alpha = 0.3f),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )
                } else{
                    var startAngle = -90f
                    activeSubs.forEach { sub  ->
                        val price = sub.price.replace(",",".").toDoubleOrNull() ?:0.0
                        val monthlyPrice = when (sub.period.lowercase().trim()){
                            "3 месяца" -> price/ 3.0
                            "год", "1 год" -> price /12.0
                            else -> price
                        }

                        val sweepAngle = ((monthlyPrice/totalAmount)*360f).toFloat()

                        drawArc(
                            color = Color(sub.colorInt),
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = Stroke(width = strokeWidth)

                        )
                        startAngle+=sweepAngle
                    }
                }
            }
            Column( horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "В месяц",
                    color = CreamWhite.copy(alpha = 0.5f),
                    fontSize = 14.sp,
                    fontFamily = DuricFont
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${String.format("%.0f", totalAmount)} Р",
                    color = CreamWhite,
                    fontSize= 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = DuricFont
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))
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
        Spacer(modifier = Modifier.height(16.dp))
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
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkBlue),
            shape = RoundedCornerShape(24.dp)
        ){
            Column(modifier = Modifier.padding(20.dp)){
                Text(
                    text = "Структура подписок",
                    color = CreamWhite,
                    fontSize = 16.sp,
                    fontFamily = DuricFont,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                if (activeSubs.isEmpty()){
                    Text(
                        text = "Нет активных подписок",
                        color = CreamWhite.copy(alpha = 0.5f),
                        fontSize = 14.sp,
                        fontFamily = DuricFont
                    )
                } else{
                    activeSubs.forEach { sub->
                        val price = sub.price.replace(",",".").toDoubleOrNull()?: 0.0
                        val monthlyPrice = when (sub.period.lowercase().trim()){
                            "3 месяца" -> price/ 3.0
                            "год", "1 год" -> price /12.0
                            else -> price
                        }
                        val percentage = if (totalAmount>0) (monthlyPrice/totalAmount)*100 else 0.0

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Row(verticalAlignment = Alignment.CenterVertically){
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(Color(sub.colorInt), RoundedCornerShape(3.dp))
                                )

                                Spacer (modifier=Modifier.width(10.dp))
                                Text(
                                    text = sub.name,
                                    color = CreamWhite,
                                    fontSize = 14.sp,
                                    fontFamily = DuricFont
                                )

                            }
                            Text(
                                text = "${String.format("%.1f", percentage)}% (${String.format("%.0f", monthlyPrice)} ₽/мес)",
                                color = CreamWhite.copy(alpha = 0.7f),
                                fontSize = 14.sp,
                                fontFamily = DuricFont
                            )
                        }
                    }
                }
            }

        }
    }
}