package com.example.basicscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview7()
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

// with onboarding screen
@Composable
fun GreetingWithOnboardingScreen(onContinueClicked: () -> Unit) {
    // `by` - no need to call .value
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface{
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "welcome to the basics codelab")
            Button(modifier = Modifier.padding(vertical = 24.dp) ,
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
fun Greetings(names: List<String> = listOf("hello", "compose")) {
    Column(modifier = Modifier.padding(vertical = 4.dp)){
        for(name in names){
            GreetingWithState(name=name)
        }
    }
}

/**
 * LazyColumn renders only the visible items on screen, allowing performance gains when rendering a big list.
 * LazyColumn and LazyRow are equivalent to RecyclerView in Android Views.
 * -> BUT! LazyColumn doesn't recycle its children like RecyclerView. It emits new Composables as you scroll through it and is still performant, as emitting Composables is relatively cheap compared to instantiating Android Views.
 * */
@Composable
fun GreetingsWithLargeList(names: List<String> = List(1000){"$it"}) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)){
        items(items = names){ name ->
            GreetingWithState(name=name)
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

@Composable
private fun MyAppWithOnboarding(){
    var shouldShowOnboarding by remember{
        mutableStateOf(true)
    }

    if(shouldShowOnboarding) GreetingWithOnboardingScreen(
        onContinueClicked = {shouldShowOnboarding = false}
    )
    else GreetingsWithLargeList()
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

// with expand button
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview6() {
    BasicsCodelabTheme {
        GreetingWithState("Jin")
    }
}

// with expand button
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun DefaultPreview7() {
    BasicsCodelabTheme {
        MyAppWithOnboarding()
    }
}