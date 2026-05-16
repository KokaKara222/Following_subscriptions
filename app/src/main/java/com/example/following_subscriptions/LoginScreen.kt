package com.example.following_subscriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.following_subscriptions.ui.theme.CreamWhite
import com.example.following_subscriptions.ui.theme.DeepBlue
import com.example.following_subscriptions.ui.theme.DuricFont
import com.example.following_subscriptions.ui.theme.LletreFont
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  LoginScreen(
    onLoginClick: () ->Unit,
    onRegister: ()->Unit) {
    var email by remember{mutableStateOf("")}
    var password by remember{mutableStateOf("")}
    var errorMes by remember { mutableStateOf<String?>(null) }

    val auth = FirebaseAuth.getInstance()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        //Заголовок
        Text(
            text = "Авторизация",
            color = CreamWhite,
            fontSize = 40.sp,
            fontFamily = LletreFont,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier= Modifier.height(8.dp))

        Text(
            text = "Вход в аккаунт",
            color = CreamWhite,
            fontStyle = FontStyle.Italic,
            fontSize=16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Введите email и пароль",
            fontStyle = FontStyle.Italic,
            color = CreamWhite,
            fontSize=16.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        //Поле Логина
        TextField(
            value = email,
            onValueChange = {
                email = it
                errorMes = null },
            label = { Text("Email",
                color = CreamWhite.copy(alpha = 0.6f),
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
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = {
                password= it
                errorMes = null},
            label = {Text("Пароль",
                color = CreamWhite.copy(alpha = 0.6f),
                fontFamily = DuricFont
            )},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = CreamWhite,
                unfocusedIndicatorColor = CreamWhite.copy(alpha = 0.5f),
                focusedTextColor = CreamWhite,
                unfocusedTextColor = CreamWhite
            )
        )
        if (errorMes!=null){
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = errorMes!!,
                color =Color.Red,
                fontSize = 14.sp,
                fontFamily = DuricFont
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        //Кнопка входа
        Button(
            onClick ={
                if (email.isNotEmpty() && password.isNotEmpty()){
                    auth.signInWithEmailAndPassword(email.trim(),password.trim())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                onLoginClick()
                            }else{
                                errorMes = "Неверный email или пароль"
                            }
                        }
                } else{
                    errorMes = "Заполните все поля"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CreamWhite),
            shape = MaterialTheme.shapes.medium
        ){
            Text(
                text = "Войти",
                color = DeepBlue,
                fontSize = 23.sp,
                fontFamily = LletreFont
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        TextButton(onClick=onRegister){
            Text(
                text="Создайте аккаунт: Регистрация",
                color = CreamWhite.copy(alpha=0.8f),
                fontFamily = LletreFont,
                fontSize=20.sp
            )
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(onLoginClick = {})
//}