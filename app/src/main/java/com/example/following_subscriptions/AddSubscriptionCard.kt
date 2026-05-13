package com.example.following_subscriptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.following_subscriptions.ui.theme.CreamWhite
import com.example.following_subscriptions.ui.theme.DeepBlue
import com.example.following_subscriptions.ui.theme.DuricFont

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun AddSubscriptionCard(onAddClick:(String,String,String, String, Int, Color)->Unit){
    var name by remember{mutableStateOf("")}
    var category by remember{mutableStateOf("")}
    var price by remember{mutableStateOf("")}
    var date by remember { mutableStateOf("") }

    val icons = listOf(R.drawable.netflix, R.drawable.youtube, R.drawable.spotify, R.drawable.iroke )
    val colors = listOf(Color(0xFF2C3E50), Color(0xFF6497B1), Color(0xFF435D6B), Color(0xFF1B2735))

    var selectedIcon by remember {mutableStateOf(icons[0])}
    var selectedColor by remember {mutableStateOf(colors[0])}
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DeepBlue)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Новая подписка", color = CreamWhite,
            fontSize = 24.sp,
            fontFamily = DuricFont
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text("Выберите логотип:", color = CreamWhite,
            modifier = Modifier.align(Alignment.Start),
            fontSize = 14.sp)

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            icons.forEach { icon ->
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(if (selectedIcon == icon) 3.dp else 0.dp,
                            CreamWhite,
                            RoundedCornerShape(8.dp))
                        .clickable{selectedIcon = icon},
                    contentScale = ContentScale.Crop
                )
            }
        }

        Text("Выберите цвет:", color = CreamWhite,
            modifier = Modifier.align(Alignment.Start),
            fontSize = 14.sp)
       Row(
           modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
           horizontalArrangement = Arrangement.spacedBy(12.dp)
       ){
           colors.forEach { color ->
               Box(
                   modifier = Modifier
                       .size(40.dp)
                       .clip(CircleShape)
                       .background(color)
                       .border(if (selectedColor == color) 3.dp else 0.dp, CreamWhite, CircleShape)
                       .clickable{selectedColor = color}
               )
           }
       }
        Spacer (modifier = Modifier.height(10.dp))

        CustomTextField(value = name, onValueChange = { name = it }, label = "Название")
        CustomTextField(value = category, onValueChange = { category = it }, label = "Категория")
        CustomTextField(value = date, onValueChange = { date = it }, label = "Дата (например: Май 30)")
        CustomTextField(value = price, onValueChange = { price = it }, label = "Цена", isNumeric = true)

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                if (name.isNotBlank() && price.isNotBlank() && date.isNotBlank()){
                    onAddClick(name, category, price, date, selectedIcon,selectedColor)
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CreamWhite),
            shape = RoundedCornerShape(16.dp)
        ){
            Text("Добавить", color = DeepBlue, fontFamily = DuricFont, fontSize = 18.sp)
        }
    }
}

@Composable
fun CustomTextField (value: String, onValueChange: (String)-> Unit, label: String, isNumeric: Boolean=false){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(label,
                color = CreamWhite.copy(alpha = 0.5f)
            ) },
        textStyle = LocalTextStyle.current.copy(
            color= CreamWhite,
            fontSize = 16.sp
        ),
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = if (isNumeric)
            KeyboardOptions(keyboardType = KeyboardType.Number)
        else
            KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = CreamWhite,
            unfocusedTextColor = CreamWhite,
            focusedIndicatorColor = CreamWhite,
            unfocusedIndicatorColor = CreamWhite.copy(alpha =  0.3f)
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
}
