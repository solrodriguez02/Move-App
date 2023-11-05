package com.example.move

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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

@Preview()
@Composable
fun MyPreview(modifier: Modifier = Modifier) {
    //Menu()
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ){
        MyApp()
    }

}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RoutinePreview(imageUrl: String, title: String, time: Int, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 30.dp)
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

    data class FilterDetail (
        val title :String,
        val options :List<String>,
        val icon :Int,
    )

    val difficultyOptions = listOf(stringResource(id = R.string.d_easy), stringResource(id = R.string.d_medium), stringResource(id = R.string.d_difficult))

    val approachOptions = listOf(
        stringResource(id = R.string.a_aerobic), stringResource(id = R.string.a_calisthenics), stringResource(id = R.string.a_cardio),
        stringResource(id = R.string.a_flex), stringResource(id = R.string.a_crossfit), stringResource(id = R.string.a_functional),
        stringResource(id = R.string.a_hiit), stringResource(id = R.string.a_pilates), stringResource(id = R.string.a_resistance),
        stringResource(id = R.string.a_streching), stringResource(id = R.string.a_strength), stringResource(id = R.string.a_weight), stringResource(id = R.string.a_yoga)
    )

    val elementsOptions = listOf(
        stringResource(id = R.string.e_none), stringResource(id = R.string.e_ankle), stringResource(id = R.string.e_band),
        stringResource(id = R.string.e_dumbell), stringResource(id = R.string.e_kettlebell), stringResource(id = R.string.e_mat),
        stringResource(id = R.string.e_roller), stringResource(id = R.string.e_rope), stringResource(id = R.string.e_step)
    )

    val spaceOptions = listOf(stringResource(id = R.string.s_reduced), stringResource(id = R.string.s_some), stringResource(id = R.string.s_much))

    var difficultyExpanded by remember { mutableStateOf(false) }
    var elementsExpanded by remember { mutableStateOf(false) }
    var approachExpanded by remember { mutableStateOf(false) }
    var spaceExpanded by remember { mutableStateOf(false) }

    var filtersSelected = remember { mutableListOf<String>() }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        LazyColumn {

            item {
                Spacer(modifier = Modifier.height(230.dp))
            }

            if(filtersSelected.isNotEmpty()) {
                item {
                    Column {
                        Text(
                            text = stringResource(id = R.string.filters_selected),
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Row {
                        for (option in filtersSelected) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color.LightGray,
                                modifier = Modifier.height(30.dp).padding(10.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = option
                                    )
                                    IconButton(onClick = { filtersSelected.remove(option) }) {
                                        Icon (
                                            Icons.Filled.Clear,
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            /////////////////// Explore Routines ///////////////////////

            val routineCount = routineData.size
            if(routineCount != 0) {
                for ((index, routine) in routineData.withIndex()) {
                    if(index % 2 == 0) {
                        if(index == routineCount-1) {
                            item {
                                RoutinePreview(
                                    imageUrl = routine.imageUrl,
                                    title = routine.title,
                                    time = routine.time
                                )
                            }
                        }
                    }
                    else {
                        item {
                            Row {
                                RoutinePreview(
                                    imageUrl = routineData[index-1].imageUrl,
                                    title = routineData[index-1].title,
                                    time = routineData[index-1].time
                                )
                                Spacer(modifier = Modifier.width(30.dp))
                                RoutinePreview(
                                    imageUrl = routine.imageUrl,
                                    title = routine.title,
                                    time = routine.time
                                )
                            }
                        }
                    }
                }
            } else {
                item {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = 150.dp)
                            .fillMaxWidth()
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.not_found),
                                contentDescription = null,
                                tint = Color.Gray
                            )
                            Text(
                                text = stringResource(id = R.string.no_results),
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color.White,
                        Color.White,
                        Color.White,
                        Color.White,
                        Color.White,
                        Color.White,
                        Color.Transparent
                    )
                ),
            )
    ) {
        Column {
            Header(title = stringResource(R.string.explore_name))

            /////////////////// Filters ///////////////////////

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.filters_title),
                    modifier = Modifier.padding(bottom = 10.dp),
                )

                Row {
                    Column {
                        Button(
                            onClick = { difficultyExpanded = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF56C1FF),
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.difficulty_filter),
                                fontSize = 17.sp
                            )
                            Icon(
                                if (difficultyExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                        DropdownMenu(
                            expanded = difficultyExpanded,
                            onDismissRequest = { difficultyExpanded = false },
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            for (option in difficultyOptions) {
                                DropdownMenuItem(
                                    text = { Text(text = option, color = Color.Black) },
                                    onClick = {
                                        filtersSelected.add(option)
                                        difficultyExpanded = false
                                    },
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Column {
                        Button(
                            onClick = { elementsExpanded = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF56C1FF),
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.elements_filter),
                                fontSize = 17.sp
                            )
                            Icon(
                                if (elementsExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                        DropdownMenu(
                            expanded = elementsExpanded,
                            onDismissRequest = { elementsExpanded = false },
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            for (option in elementsOptions) {
                                DropdownMenuItem(
                                    text = { Text(text = option, color = Color.Black) },
                                    onClick = {
                                        filtersSelected.add(option)
                                        elementsExpanded = false
                                    },
                                )
                            }
                        }
                    }
                }


                Row {
                    Column {
                        Button(
                            onClick = { approachExpanded = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF56C1FF),
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.approach_filter),
                                fontSize = 17.sp
                            )
                            Icon(
                                if (approachExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                        DropdownMenu(
                            expanded = approachExpanded,
                            onDismissRequest = { approachExpanded = false },
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            for (option in approachOptions) {
                                DropdownMenuItem(
                                    text = { Text(text = option, color = Color.Black) },
                                    onClick = {
                                        filtersSelected.add(option)
                                        approachExpanded = false
                                    },
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Column {
                        Button(
                            onClick = { spaceExpanded = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF56C1FF),
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.width(150.dp)

                        ) {
                            Text(
                                text = stringResource(id = R.string.space_filter),
                                fontSize = 17.sp,
                            )
                            Icon(
                                if (spaceExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }

                        DropdownMenu(
                            expanded = spaceExpanded,
                            onDismissRequest = { spaceExpanded = false },
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            for (option in spaceOptions) {
                                DropdownMenuItem(
                                    text = { Text(text = option, color = Color.Black) },
                                    onClick = {
                                        filtersSelected.add(option)
                                        spaceExpanded = false
                                    },
                                )
                            }
                        }
                    }
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
            .padding(start = 20.dp, top = 30.dp, end = 20.dp)
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



/// ROUTINE PAGE //////////////////////////////////////////////////////////////////////////////////////////////////////


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
    var cycleIndex by remember { mutableStateOf(0) }
    var detailMode by remember { mutableStateOf(true) }
    var showModeDialog by remember { mutableStateOf(false) }

    var descriptionIcon = if(showDescription) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    var modeIcon = if(detailMode) painterResource(id = R.drawable.detail_mode) else painterResource(id = R.drawable.list_mode)

    data class ModeOption (
        val label :String,
        val icon :Painter
    )

    data class FilterDetail (
        val title :String,
        val detail :String,
        val icon :Int,
    )

    val modeOptions :List<ModeOption> = listOf(
        ModeOption(stringResource(id = R.string.detail_mode), painterResource(id = R.drawable.detail_mode)),
        ModeOption(stringResource(id = R.string.list_mode), painterResource(id = R.drawable.list_mode)),
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
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }

        Column(
            modifier = Modifier
                .verticalScroll(state)
                .padding(top = 60.dp)
        ) {

            /////////////////// Routine image ///////////////////////
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
            RoutineMenu(routine.time)
        }

        /////////////////// Start Routine Button ///////////////////////
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        ){
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5370F8),
                    contentColor = Color.Transparent,
                ),
                modifier = Modifier.height(50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.start_routine),
                    color = Color.Black,
                    fontSize = 18.sp,
                )
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = stringResource(id = R.string.start_routine),
                    tint = Color.Black,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }

        /////////////////// Mode Button ///////////////////////
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp, end = 40.dp)

        ){
            Button(
                onClick = { showModeDialog = true },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFACACAC),
                    contentColor = Color.Transparent,
                ),
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)

            ){
                Icon(
                    painter = modeIcon,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }

        /////////////////// Mode Dialog ///////////////////////
        if (showModeDialog) {
            Dialog(
                onDismissRequest = { showModeDialog = false },
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation,
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.mode_dialog_title),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            for(option in modeOptions) {
                                var modeMatches = detailMode && option.label == stringResource(id = R.string.detail_mode) || !detailMode && option.label == stringResource(id = R.string.list_mode)
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(20.dp),
                                        color = if (modeMatches) Color.LightGray else Color.Transparent,
                                        border = if (modeMatches) BorderStroke(1.dp, Color.LightGray) else BorderStroke(1.dp, Color(0xFFACACAC)),
                                        modifier = Modifier
                                            .height(100.dp)
                                            .width(100.dp)
                                            .padding(10.dp)
                                    ) {
                                        IconButton(
                                            onClick = { if(!modeMatches) detailMode = !detailMode }
                                        ) {
                                            Icon(
                                                painter = option.icon,
                                                contentDescription = option.label,
                                                modifier = Modifier.padding(25.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = option.label
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RoutineMenu(time :Int) {

    data class PopUpOption (
        val label :String,
        val icon :ImageVector
    )

    val popUpOptions :List<PopUpOption> = listOf(
        PopUpOption(stringResource(id = R.string.add_favourite), Icons.Default.FavoriteBorder),
        PopUpOption(stringResource(id = R.string.rate_routine), Icons.Default.Star),
        PopUpOption(stringResource(id = R.string.share), Icons.Default.Share),
    )

    var showPopUp by remember { mutableStateOf(false) }

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
                text = "$time min",
                color = Color(0xFF5370F8),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        IconButton (
            onClick = { showPopUp = true } ) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = stringResource(id = R.string.show_more),
                tint = Color.Gray,
                modifier = Modifier.size(25.dp)
            )
        }
    }

    /////////////////// Show More Pop Up ///////////////////////

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 1000.dp)
    ) {
        DropdownMenu(
            expanded = showPopUp,
            onDismissRequest = { showPopUp = false },
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            for (option in popUpOptions) {
                DropdownMenuItem(
                    text = { Text(text = option.label, color = Color.Black) },
                    onClick = { /* Handle edit! */ },
                    leadingIcon = {
                        Icon(
                            option.icon,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
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