package com.example.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview9()
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
    val expanded = remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue = if(expanded.value) 48.dp else 0.dp,
        animationSpec =
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))){
                Text(text = "hello")
                Text(text = name, style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
            }

            OutlinedButton(onClick = {
                expanded.value = !expanded.value
            }) {
                Text(if(expanded.value) "Show less" else "Show more")
            }
        }
    }
}


/**
 * Managing state
 * remember is used to guard against recomposition, so the state is not reset.
 * */
@Composable
fun GreetingFinal(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        CardContent(name)
    }
}

@Composable
private fun CardContent(name:String){
    var expanded by remember{ mutableStateOf(false)}
    Row(modifier = Modifier
        .padding(24.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )) {
            Column(
                modifier = Modifier
                .weight(1f)
                .padding(12.dp)
            ){
                Text(text = "hello")
                Text(text = name, style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
                if (expanded) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }
        }

        IconButton(
            onClick = {
                expanded = !expanded
            }
        ){
            Icon(
                imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription =
                if(expanded) stringResource(id = R.string.show_less)
                else stringResource(id = R.string.show_more)
            )
        }
    }
}

// with onboarding screen
@Composable
fun GreetingWithOnboardingScreen(onContinueClicked: () -> Unit) {
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
            GreetingFinal(name=name)
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

/**
 *  `remember` works only as long as the composable is kept in the Composition
 *   `rememberSaveable` saves each state surviving configuration changes (such as rotations) and process death.
 * */
@Composable
private fun MyAppWithOnboarding(){
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

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
@Preview(showBackground = true, widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES, name = "DefaultPreviewDark")
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

// darkmode
@Preview(showBackground = true, widthDp = 320, heightDp = 320,
uiMode = UI_MODE_NIGHT_YES, name = "DefaultPreviewDark")
@Composable
fun DefaultPreview8() {
    BasicsCodelabTheme {
        MyAppWithOnboarding()
    }
}

// with expand button
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun DefaultPreview9() {
    BasicsCodelabTheme {
        MyAppWithOnboarding()
    }
}

// with expand button
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun DefaultPreview10() {
    BasicsCodelabTheme {
        GreetingFinal("Jin")
    }
}