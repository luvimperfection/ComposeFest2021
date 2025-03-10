package com.example.basicstutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.basicstutorial.ui.theme.BasicsTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard("Jin")
        }
    }
}

@Composable
fun MessageCard(name: String){
    Text("Hello $name!")
}

@Preview
@Composable
fun DefaultPreview(){
    BasicsTutorialTheme {
        MessageCard("Jin")
    }
}