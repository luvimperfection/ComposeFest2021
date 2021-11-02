package com.example.basicscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview6()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(
        color = MaterialTheme.colors.primary
    ){
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "hello")
            Text(text = name)
        }
    }
}

@Composable
fun GreetingHorizontal(name: String) {
    Surface(
        color = MaterialTheme.colors.primary
    ){
        Row(modifier = Modifier.padding(24.dp)) {
            Text(text = "hello")
            Text(text = name)
        }
    }
}

@Composable
fun GreetingWithPadding(name: String) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)) {
            Text(text = "hello")
            Text(text = name)
        }
    }
}

// button
@Composable
fun GreetingWithButton(name: String) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)){
                Text(text = "hello")
                Text(text = name)
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text("Show more")
            }
        }
    }
}

/**
 * Managing state
 * remember is used to guard against recomposition, so the state is not reset.
 * */
@Composable
fun GreetingWithState(name: String) {
    val expanded = remember{
        mutableStateOf(false)
    }
    val extraPadding = if(expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)){
                Text(text = "hello")
                Text(text = name)
            }
            OutlinedButton(onClick = {
                expanded.value = !expanded.value
            }) {
                Text(if(expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Composable
private fun MyApp(){
    Surface(color = MaterialTheme.colors.background){
        Greeting(name = "Jetpack Compose")
    }
}

@Composable
private fun MyAppList(names: List<String> = listOf("Jetpack", "Compose")){
    Column {
        names.forEach{
            Greeting(name = it)
        }
    }
}

@Composable
private fun MyAppListWithPadding(names: List<String> = listOf("Jetpack", "Compose")){
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        names.forEach{
            GreetingWithPadding(name = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview1() {
    BasicsCodelabTheme {
        MyAppList()
    }
}

@Preview(showBackground = true, name = "Text Preview")
@Composable
fun DefaultPreview2() {
    BasicsCodelabTheme {
        GreetingHorizontal("hello hello")
    }
}

// set background width
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview3() {
    BasicsCodelabTheme {
        MyApp()
    }
}

// set background width
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview4() {
    BasicsCodelabTheme {
        MyAppListWithPadding()
    }
}

// set background width
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview5() {
    BasicsCodelabTheme {
        GreetingWithButton("Jin")
    }
}

// set background width
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview6() {
    BasicsCodelabTheme {
        GreetingWithState("Jin")
    }
}