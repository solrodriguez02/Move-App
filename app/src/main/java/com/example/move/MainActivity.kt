package com.example.move

import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.move.ui.theme.MoveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoveTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Menu()
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RoutinePreview(imageUrl: String, title: String, time: Int, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 30.dp)
    ) {
        Surface(
            color = Color(0x00FFFFFF),
            modifier = Modifier
                .background(
                    color = Color(0xFFE4E4E4),
                    shape = RoundedCornerShape(40.dp)
                )
                .padding(8.dp)
                .width(140.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .padding(bottom = 5.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = imageUrl
                        ),
                        contentDescription = "routine image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(
                                    topStart = 30.dp,
                                    topEnd = 30.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            ),
                        contentScale = ContentScale.Crop,
                    )
                }

                Text(
                    text = title,
                    color = Color.Black,
                )

                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = "time icon",
                        tint = Color(0xFF5370F8)
                    )

                    Text(
                        text = "$time'",
                        color = Color(0xFF5370F8),
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ExploreScreen() {
    data class RoutineItemData(
        val imageUrl: String,
        val title: String,
        val time: Int
    )

    val routineData:  List<RoutineItemData> = listOf(
        RoutineItemData("https://hips.hearstapps.com/hmg-prod/images/12-ejercicios-para-abdominales-elle-1632239590.jpg", "Abs routine 1", 15),
        RoutineItemData("https://hips.hearstapps.com/hmg-prod/images/12-ejercicios-para-abdominales-elle-1632239590.jpg", "Abs routine 2", 60),
        RoutineItemData("https://hips.hearstapps.com/hmg-prod/images/12-ejercicios-para-abdominales-elle-1632239590.jpg", "Abs routine 3", 25),
        RoutineItemData("https://hips.hearstapps.com/hmg-prod/images/12-ejercicios-para-abdominales-elle-1632239590.jpg", "Abs routine 4", 40),
        RoutineItemData("https://hips.hearstapps.com/hmg-prod/images/12-ejercicios-para-abdominales-elle-1632239590.jpg", "Abs routine 5", 60),

        )

    Column {
        Header(title = stringResource(R.string.explore_name))
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(routineData) { routine ->
                    RoutinePreview(
                        imageUrl = routine.imageUrl,
                        title = routine.title,
                        time = routine.time
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column {
        Header(title = stringResource(R.string.home_name))
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Header(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 30.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = rememberImagePainter(
                data = "https://profilemagazine.com/wp-content/uploads/2020/04/Ajmere-Dale-Square-thumbnail.jpg"
            ),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(60.dp)
                .clip(shape = RoundedCornerShape(100.dp)),
        )
    }
}

@Composable
fun Menu() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(Icons.Filled.Search, Icons.Filled.Home)
    if(selectedItem == 0) 
        ExploreScreen()
    else HomeScreen()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 25.dp, horizontal = 100.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(shape = RoundedCornerShape(100.dp))
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF2D2C32))
                .padding(horizontal = 30.dp)
        ) {
            items.forEachIndexed { index, item ->

                val selectedPosition =  if (selectedItem == index) Modifier.padding(15.dp) else Modifier.padding(16.dp)
                val selectedColor = if (selectedItem == index) Color(0xFF5370F8) else Color.White

                Button(
                    onClick = { selectedItem = index },
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    ),
                    modifier = Modifier.width(60.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = selectedPosition.then(Modifier.fillMaxSize())
                    ) {
                        Icon(
                            item,
                            contentDescription = "item $index",
                            tint = selectedColor,

                        )
                        if (selectedItem == index) {
                            Divider(
                                color = Color(0xFF5370F8),
                                thickness = 3.dp,
                                modifier = Modifier
                                    .width(25.dp)
                                    .padding(top = 1.dp)
                                    .clip(shape = RoundedCornerShape(20.dp))
                            )
                        }
                    }
                }
            }
        }
    }

}