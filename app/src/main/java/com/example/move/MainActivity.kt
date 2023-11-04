package com.example.move

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
     //Menu()
    Routine()
}

@Preview()
@Composable
fun MyPreview(modifier: Modifier = Modifier) {
    //Menu()
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ){
        Routine()
    }

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
                        contentDescription = stringResource(R.string.time_icon),
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
            .padding(vertical = 25.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(shape = RoundedCornerShape(100.dp))
                .height(60.dp)
                .background(Color(0xFF2D2C32))
                .padding(horizontal = 30.dp)
                .width(130.dp)
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Routine() {

/// API //////////////////////////////////////////////////////////////////////////////////////////////////////////
    data class Exercise(
        val title :String,
        val imageUrl :String,
        val secs :Int,
        val reps :Int
    )

    data class Cycle(
        val name :String,
        val exercises :List<Exercise>,
        val reps :Int
    )

    data class RoutineItem(
        val title: String,
        val imageUrl :String,
        val difficulty :String,
        val elements :List<String>,
        val approach :List<String>,
        val space :String,
        val description :String,
        val cycles :List<Cycle>,
        val time :Int
    )

    val exercises :List<Exercise> = listOf(
        Exercise("Jump rope", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/jump-rope-hop.jpg", 15, 0),
        Exercise("Pivoting", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/pivoting-upper-cut.jpg", 30, 15),
        Exercise("Switch Kick", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/switch-kick.jpg", 30, 0),
        Exercise("Rest", "", 15, 0),
        Exercise("Windmill", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/windmill.jpg", 45, 0),
        Exercise("Squat Jacks", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/squat-jack.jpg", 0, 15)
    )

    val exercises1 :List<Exercise> = listOf(
        Exercise("Switch Kick", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/switch-kick.jpg", 30, 0),
        Exercise("Windmill", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/windmill.jpg", 45, 0),
        Exercise("Rest", "", 15, 0),
        Exercise("Jump rope", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/jump-rope-hop.jpg", 15, 0),
        Exercise("Pivoting", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/pivoting-upper-cut.jpg", 30, 15),
    )

    val cycles :List<Cycle> = listOf(
        Cycle("Warm up", exercises, 1),
        Cycle("Cycle 1", exercises1, 2),
        Cycle("Cycle 2", exercises, 3),
        Cycle("Cooling", exercises, 1)
    )

    val routine :RoutineItem = RoutineItem("Senta-Senta", "https://s3.abcstatics.com/media/bienestar/2020/11/17/abdominales-kfHF--620x349@abc.jpeg",
        "Medium", listOf("Dumbells", "Rope"), listOf("Cardio"), "Ideal for reduced spaces", "Very fun exercise", cycles, 15)

/// API //////////////////////////////////////////////////////////////////////////////////////////////////////////

    var showDescription by remember { mutableStateOf(false) }
    var descriptionIcon = if(showDescription) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    var cycleIndex by remember { mutableStateOf(0) }

    data class FilterDetail (
        val title :String,
        val detail :String,
        val icon :Int,
    )

    val filters = arrayListOf(
        FilterDetail("Difficulty", routine.difficulty + " difficulty", R.drawable.difficulty),
        FilterDetail("Elements required", routine.elements.toString().substring(1, routine.elements.toString().length - 1), R.drawable.elements),
        FilterDetail("Approach", routine.approach.toString().substring(1, routine.approach.toString().length - 1), R.drawable.approach),
        FilterDetail("Space required", routine.space, R.drawable.space)
    )

    val cyclesOptions = listOf(R.drawable.warm_up, R.drawable.exercise, R.drawable.cooling)


    Box(
        modifier = Modifier.background(Color.White)
    ) {

        /////////////////// Routine image ///////////////////////
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }

        Column(
            modifier = Modifier
                .verticalScroll(state)
                .padding(top = 60.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(bottom = 20.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = routine.imageUrl
                    ),
                    contentDescription = "routine image",
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 15.dp,
                                topEnd = 15.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.FillBounds,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(listOf(Color.Transparent, Color.White)),
                        )
                        .align(Alignment.BottomCenter)
                        .height(50.dp)
                )
            }

            /////////////////// Routine filters detail ///////////////////////
            Column(
                modifier = Modifier
                    .background(
                        color = Color(0xFFE4E4E4),
                        shape = RoundedCornerShape(40.dp)
                    )
                    .fillMaxSize()
                    .padding(vertical = 25.dp, horizontal = 30.dp)
            ) {
                Text(
                    text = routine.title,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                Column(
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    for (filter in filters) {
                        Row(
                            modifier = Modifier.padding(vertical = 5.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = filter.icon),
                                contentDescription = "item icon",
                                tint = Color.DarkGray,
                                modifier = Modifier.padding(start = 5.dp, end = 10.dp)
                            )
                            Text(
                                text = filter.detail,
                                color = Color.DarkGray
                            )
                        }
                        Divider(
                            color = Color(0x32777777),
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        )
                    }
                }

                /////////////////// Routine description ///////////////////////

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.description_name)
                    )

                    Button(
                        onClick = { showDescription = !showDescription },
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color(0xFFE4E4E4),
                        ),
                        modifier = Modifier.width(40.dp)
                    ) {
                        Icon(
                            descriptionIcon,
                            contentDescription = stringResource(R.string.description_name),
                            tint = Color.Black
                        )
                    }
                }
                if (showDescription) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = routine.description,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                /////////////////// Cycles ///////////////////////
                Text(
                    text = stringResource(R.string.cycles_name),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(30.dp),
                        color = Color.LightGray,
                        modifier = Modifier.width(400.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            for ((index, option) in cyclesOptions.withIndex()) {
                                Button(
                                    onClick = { cycleIndex = index },
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (cycleIndex == index) Color.White else Color.Transparent
                                    ),
                                    modifier = Modifier
                                        .width(110.dp)
                                        .height(30.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = option),
                                        contentDescription = null,
                                        tint = if (cycleIndex == index) Color(0xFF5370F8) else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }


                /////////////////// Cycle exercises ///////////////////////
                for ((index, cycle) in routine.cycles.withIndex()) {
                    if (cycleIndex == 0 && index == 0 ||
                        cycleIndex == 1 && index > 0 && index < routine.cycles.size - 1 ||
                        cycleIndex == 2 && index == routine.cycles.size - 1
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 15.dp)
                        ) {
                            Text(
                                text = cycle.name,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                            Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Text(
                                    text = "X " + cycle.reps,
                                    color = Color(0xFF5370F8),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }

                        for (exercise in cycle.exercises) {
                            if (exercise.title == "Rest") {
                                RestExercise(title = exercise.title, secs = exercise.secs)
                            } else {
                                ExerciseBox(
                                    title = exercise.title,
                                    secs = exercise.secs,
                                    reps = exercise.reps,
                                    imgUrl = exercise.imageUrl
                                )
                            }
                        }
                    }
                }
            }
        }

        /////////////////// Routine header ///////////////////////
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(listOf(Color.White, Color.White, Color.Transparent)),
                )
                .height(80.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 10.dp)
            ) {
                Icon(
                    Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "back icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = stringResource(R.string.time_icon),
                        tint = Color(0xFF5370F8),
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 5.dp)
                    )
                    Text(
                        text = routine.time.toString() + " min",
                        color = Color(0xFF5370F8),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "details icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ExerciseBox(title :String, secs :Int, reps :Int, imgUrl :String) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xC4FFFFFF),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(105.dp)
                    .height(75.dp)
                    .padding(10.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = imgUrl
                    ),
                    contentDescription = stringResource(R.string.exercise_image),
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = title
                )
                if(secs != 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.time),
                            contentDescription = stringResource(R.string.time_icon),
                            tint = Color(0xFF5370F8),
                            modifier = Modifier
                                .padding(end = 3.dp)
                                .size(20.dp)
                        )
                        Text(
                            text = secs.toString() + stringResource(id = R.string.seconds),
                            color = Color(0xFF5370F8)
                        )
                    }
                }
            }
            if(reps != 0) {
                Text(
                    text = stringResource(id = R.string.repetitions) + reps,
                    color = Color(0xFF5370F8),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
        }
    }
}

@Composable
fun RestExercise(title :String, secs :Int) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFF2D2C32),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.rest),
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.padding(start = 30.dp, end = 50.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.time),
                contentDescription = stringResource(R.string.time_icon),
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 3.dp)
                    .size(20.dp)
            )
            Text(
                text = secs.toString() + stringResource(id = R.string.seconds),
                color = Color.White
            )
        }
    }
}