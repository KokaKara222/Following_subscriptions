package com.example.following_subscriptions

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import java.util.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.following_subscriptions.ui.theme.CreamWhite
import com.example.following_subscriptions.ui.theme.DarkBlue
import com.example.following_subscriptions.ui.theme.DeepBlue
import com.example.following_subscriptions.ui.theme.DuricFont
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class, ExperimentalStdlibApi::class)
@Composable
fun AddSubscriptionCard(
    onAddClick: (String, String, String, String, String, Int, String?, Color) -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    var showDatePicker by remember {mutableStateOf(false)}

    val periods = listOf("Месяц", "3 месяца", "Год")
    var selectedPeriodIndex by remember { mutableStateOf(0) }

    val brandIcons =
        listOf(R.drawable.netflix, R.drawable.youtube, R.drawable.spotify, R.drawable.iroke)
    var selectedIcon by remember { mutableStateOf(brandIcons[0]) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    val cardcolors = listOf(Color(0xFF2C3E50), Color(0xFF6497B1), Color(0xFF435D6B), Color(0xFF1B2735))
    var selectedColor by remember { mutableStateOf(cardcolors[0]) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                try {
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    selectedUri = uri
                } catch (e: Exception) {
                    e.printStackTrace()
                    selectedUri = uri
                }
            }
        }

    if (showDatePicker){
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = {showDatePicker = false},
            confirmButton = {
                TextButton(onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    if (selectedDateMillis != null){
                        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
                        date =sdf.format(Date(selectedDateMillis))
                    }
                    showDatePicker = false
                }) {
                    Text("OK", color = CreamWhite)
                }
            },
            dismissButton = {
                TextButton(onClick = {showDatePicker = false}) {
                    Text("Отмена", color = CreamWhite)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DeepBlue)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Новая подписка", color = CreamWhite,
            fontSize = 24.sp,
            fontFamily = DuricFont
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Выберите логотип:", color = CreamWhite,
            modifier = Modifier.align(Alignment.Start),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.1f))
                    .border(
                        if (selectedUri != null) 3.dp else 0.dp,
                        CreamWhite,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable { galleryLauncher.launch("image/*") },
                contentAlignment = Alignment.Center

            ) {
                if (selectedUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(selectedUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(Icons.Default.Add, contentDescription = "Галерея", tint = CreamWhite)
                }
            }

            brandIcons.forEach { icon ->
                val isSelected = selectedIcon == icon && selectedUri == null
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            if (isSelected) 3.dp else 0.dp,
                            CreamWhite,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            selectedIcon = icon
                            selectedUri = null
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }

        Text(
            "Выберите цвет:", color = CreamWhite,
            modifier = Modifier.align(Alignment.Start),
            fontSize = 14.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            cardcolors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(if (selectedColor == color) 3.dp else 0.dp, CreamWhite, CircleShape)
                        .clickable { selectedColor = color }
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Период оплаты:", color = CreamWhite,
            modifier = Modifier.align(Alignment.Start),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            periods.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = periods.size),
                    onClick = { selectedPeriodIndex = index },
                    selected = index == selectedPeriodIndex,
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = CreamWhite,
                        activeContentColor = DeepBlue,
                        inactiveContainerColor = DarkBlue,
                        inactiveContentColor = CreamWhite
                    )
                ) {
                    Text(label, fontSize = 12.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(value = name, onValueChange = { name = it }, label = "Название")
        CustomTextField(value = category, onValueChange = { category = it }, label = "Категория")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable{showDatePicker = true}
        ){
            CustomTextField(
                value =date,
                onValueChange = {},
                label = "Дата списания",
                enabled = false
            )
        }

        CustomTextField(
            value = price,
            onValueChange = { price = it },
            label = "Цена",
            isNumeric = true
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (name.isNotBlank() && price.isNotBlank() && date.isNotBlank()) {
                    onAddClick(
                        name,
                        category,
                        price,
                        date,
                        periods[selectedPeriodIndex],
                        selectedIcon,
                        selectedUri?.toString(),
                        selectedColor
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CreamWhite),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Добавить", color = DeepBlue, fontFamily = DuricFont, fontSize = 18.sp)
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isNumeric: Boolean = false,
    enabled: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = { input ->
            if (isNumeric) {
                val filtered = input.filter { it.isDigit() || it == '.' || it == ',' }
                if (filtered.count { it == '.' || it == ',' } <= 1) {
                    onValueChange(filtered)
                }
            } else {
                onValueChange(input)
            }
        },
        label = {
            Text(
                label,
                color = CreamWhite.copy(alpha = 0.5f)
            )
        },
        enabled = enabled,
        textStyle = LocalTextStyle.current.copy(
            color = CreamWhite,
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
            disabledContainerColor = Color.Transparent,
            focusedTextColor = CreamWhite,
            unfocusedTextColor = CreamWhite,
            focusedIndicatorColor = CreamWhite,
            unfocusedIndicatorColor = CreamWhite.copy(alpha = 0.3f)
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
}
