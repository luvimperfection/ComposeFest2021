package com.example.layoutsbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.layoutsbasics.ui.theme.LayoutsBasicsTheme
import com.example.layoutsbasics.ui.theme.LayoutsCodelabTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            PhotographerCardPreview()
            LayoutPracticePreview()
        }
    }
}

@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ){ innerPadding ->
       BodyContent(Modifier
           .padding(innerPadding)
           .padding(8.dp))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
//    Column(modifier = modifier) {
    MyOwnColumn(modifier = modifier) {
        Text(text = "MyOwnColumn")
        Text(text = "places item")
        Text(text = "vertically")
        Text(text = "We've done it by hand")
    }
}

@Composable
fun SimpleList() {
    val scrollState = rememberLazyListState()
    val listSize = 100
    val coroutineScope = rememberCoroutineScope()

    Column{
        Row{
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }){
                Text("Scroll to the top")
            }
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize-1)
                }
            }){
                Text("Scroll to the end")
            }
        }
        LazyColumn(state = scrollState) {
            items(100){
                ImageListItem(it)
            }
        }
    }

}

@Composable
fun ImageListItem(index: Int){
    Row(verticalAlignment = Alignment.CenterVertically){
        Image(
            painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png"),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("Item $index", style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun PhotographerCard(modifier: Modifier = Modifier){
    // modifier order matters
    Row(modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.surface)
        .clickable(onClick = {})
        .padding(16.dp)
        ){
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
            ) {
                // image
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ){
                Text("Alfred Sisley", fontWeight = FontWeight.Bold)
                // LocalContentAlpha is defining opacity level of its children
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium ){
                    Text("3 minutes ago", style = MaterialTheme.typography.body2)
                }
            }
    }
}


fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    // measurable: child to be measured and placed
    // constraints: minimum and maximum for the width and height of the child
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of the composable with padding - first baseline
        val placebleY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placebleY
        layout(placeable.width, height) {
            // place the composable
            placeable.placeRelative(0, placebleY)
        }
    }
)

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Layout(
        modifier = modifier,
        content = content
    ){ measureables, constraints ->
        // list of measured children
        val placeables = measureables.map{ measurable ->
            // measure each child
            measurable.measure(constraints)
        }
        var yPosition = 0
        layout(constraints.maxWidth, constraints.maxHeight){
            // place children in the parent layout
            placeables.forEach{ placeable ->
                // position the item on the screen
                placeable.placeRelative(x=0, y=yPosition)
                // record the y coard placed up to
                yPosition += placeable.height
            }
        }
    }
}

//@Preview
@Composable
fun TextWithPaddingToBaselinePreview(){
    LayoutsBasicsTheme {
        Text("Hi there!",
        Modifier.firstBaselineToTop(32.dp))
    }
}

//@Preview
@Composable
fun TextWithNormalPaddingePreview(){
    LayoutsBasicsTheme {
        Text("Hi there!",
            Modifier.padding(top = 32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutPracticePreview() {
    LayoutsBasicsTheme {
//        SimpleList()
//        LayoutsCodelab()
        BodyContent()
    }
}

@Preview(showBackground = true)
@Composable
fun PhotographerCardPreview() {
    LayoutsCodelabTheme {
        PhotographerCard()
    }
}