import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.move.R


/// API //////////////////////////////////////////////////////////////////////////////////////////////////////////
data class Exercise(
    val title :String,
    val imageUrl :String,
    val secs :Int,
    val reps :Int,
    val description :String
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
    val time :Int,
    val score :Float
)

val exercises :List<Exercise> = listOf(
    Exercise("Jump rope", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/jump-rope-hop.jpg", 5, 15, "This exercise is fun!"),
    Exercise("Switch Kick", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/switch-kick.jpg", 2, 0, "This exercise is fun!"),
    Exercise("Rest", "", 5, 0, "This exercise is fun!"),
    Exercise("Windmill", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/windmill.jpg", 0, 14, "This exercise is fun!"),
    Exercise("Rest", "", 5, 0, "This exercise is fun!"),
    Exercise("Squat Jacks", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/squat-jack.jpg", 0, 15, "This exercise is fun!"),

    )

val exercises1 :List<Exercise> = listOf(
    Exercise("Switch Kick", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/switch-kick.jpg", 3, 0, "This exercise is fun!"),
    Exercise("Windmill", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/windmill.jpg", 4, 0, "This exercise is fun!"),
    Exercise("Rest", "", 5, 0, "This exercise is fun!"),
    Exercise("Jump rope", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/jump-rope-hop.jpg", 5, 0, "This exercise is fun!"),
    Exercise("Pivoting", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/pivoting-upper-cut.jpg", 3, 15, "This exercise is fun!"),
)

val cycles :List<Cycle> = listOf(
    Cycle("Warm up", exercises, 1),
    Cycle("Cycle 1", exercises, 2),
    Cycle("Cycle 2", exercises1, 3),
    Cycle("Cooling", exercises, 1)
)

val routine :RoutineItem = RoutineItem("Senta-Senta", "https://s3.abcstatics.com/media/bienestar/2020/11/17/abdominales-kfHF--620x349@abc.jpeg",
    "Medium", listOf("Dumbells", "Rope"), listOf("Cardio"), "Ideal for reduced spaces", "Very fun exercise", cycles, 15, 3.4f)

/// API //////////////////////////////////////////////////////////////////////////////////////////////////////////







/// ROUTINE PAGE //////////////////////////////////////////////////////////////////////////////////////////////////////


@OptIn(ExperimentalCoilApi::class, ExperimentalAnimationApi::class)
@Composable
fun Routine() {

    val config = LocalConfiguration.current
    val orientation = config.orientation

    var showRate by remember { mutableStateOf(false) }
    var showDescription by remember { mutableStateOf(false) }
    var detailMode by remember { mutableStateOf(true) }
    var showModeDialog by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf (3) }
    var cycleIndex by remember { mutableStateOf(0) }

    @Composable
    fun RoutineDetail() {
        val descriptionIcon = if(showDescription) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
        val rateIcon = if(showRate) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

        data class FilterDetail (
            val title :String,
            val detail :String,
            val icon :Int,
        )

        val filters = arrayListOf(
            FilterDetail(stringResource(id = R.string.difficulty_filter), routine.difficulty + stringResource(id = R.string.difficulty_lower), R.drawable.difficulty),
            FilterDetail(stringResource(id = R.string.elements_required_filter), routine.elements.toString().substring(1, routine.elements.toString().length - 1), R.drawable.elements),
            FilterDetail(stringResource(id = R.string.approach_filter), routine.approach.toString().substring(1, routine.approach.toString().length - 1), R.drawable.approach),
            FilterDetail(stringResource(id = R.string.space_required_filter), routine.space, R.drawable.space)
        )

        val cyclesOptions = listOf(R.drawable.warm_up, R.drawable.exercise, R.drawable.cooling)

        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(40.dp)
                )
                .fillMaxSize()
                .padding(vertical = 25.dp, horizontal = 30.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = routine.title,
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                )
                Text(
                    text = routine.score.toString(),
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }

            Column(
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                for (filter in filters) {
                    Row(
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = filter.icon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(start = 5.dp, end = 10.dp)
                        )
                        Text(
                            text = filter.detail,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                    Divider(
                        color = MaterialTheme.colorScheme.tertiary,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    )
                }
            }

            /////////////////// Rate routine ///////////////////////

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.rate_name),
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = { showRate = !showRate },
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.surface,
                    ),
                    modifier = Modifier
                        .width(40.dp)
                        .height(25.dp)
                ) {
                    Icon(
                        rateIcon,
                        contentDescription = stringResource(R.string.rate_name),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (showRate) {
                Row(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    for (i in 1..5) {
                        IconButton(
                            onClick = { score = i },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                painter = if (i <= score) painterResource(id = R.drawable.star) else painterResource(
                                    id = R.drawable.empty_star
                                ),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            /////////////////// Routine description ///////////////////////

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.description_name),
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = {showDescription = !showDescription},
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.surface,
                    ),
                    modifier = Modifier.width(40.dp)
                ) {
                    Icon(
                        descriptionIcon,
                        contentDescription = stringResource(R.string.description_name),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            if (showDescription) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = routine.description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            /////////////////// Cycles ///////////////////////
            Text(
                text = stringResource(R.string.cycles_name),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(30.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        for ((index, option) in cyclesOptions.withIndex()) {
                            Button(
                                onClick = { cycleIndex = index },
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (cycleIndex == index) MaterialTheme.colorScheme.inversePrimary else Color.Transparent
                                ),
                                modifier = Modifier
                                    .height(30.dp)
                                    .weight(1f)
                            ) {
                                Icon(
                                    painter = painterResource(id = option),
                                    contentDescription = null,
                                    tint = if (cycleIndex == index) MaterialTheme.colorScheme.surfaceTint else MaterialTheme.colorScheme.primary
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
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Surface(
                            color = MaterialTheme.colorScheme.inversePrimary,
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = stringResource(id = R.string.repetitions_upper) + cycle.reps,
                                color = MaterialTheme.colorScheme.surfaceTint,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }

                    for (exercise in cycle.exercises) {
                        if (exercise.title == stringResource(id = R.string.rest_name)) {
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



    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.inversePrimary)
    ) {
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }

        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            Column(
                modifier = Modifier
                    .verticalScroll(state)
                    .padding(top = 60.dp)
                    .fillMaxWidth()
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
                        contentDescription = routine.imageUrl,
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
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.inversePrimary
                                    )
                                ),
                            )
                            .align(Alignment.BottomCenter)
                            .height(50.dp)
                    )
                }
                RoutineDetail()
            }
        } else {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.weight(0.4f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .padding(bottom = 20.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = routine.imageUrl
                            ),
                            contentDescription = routine.imageUrl,
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
                    }
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        RoutineExecutionMenu(
                            detailMode = detailMode,
                            showModeDialog = showModeDialog,
                            onShowMode = { showModeDialog = !showModeDialog },
                            onChangeMode = { detailMode = !detailMode },
                            onStayMode = {})
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(horizontal = 20.dp)
                        .verticalScroll(state)
                ) {
                    Spacer(modifier = Modifier.height(90.dp))
                    RoutineDetail()
                }
            }
        }

        /////////////////// Routine header ///////////////////////
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.inversePrimary,
                            MaterialTheme.colorScheme.inversePrimary,
                            Color.Transparent
                        )
                    ),
                )
                .height(80.dp)
        ) {
            RoutineMenu(routine.time)
        }

        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                RoutineExecutionMenu(
                    detailMode = detailMode,
                    showModeDialog = showModeDialog,
                    onShowMode = { showModeDialog = !showModeDialog },
                    onChangeMode = { detailMode = !detailMode },
                    onStayMode = {})
            }
        }
    }

}


@Composable
fun RoutineExecutionMenu(detailMode :Boolean, showModeDialog :Boolean, onShowMode :() -> Unit, onChangeMode :() -> Unit, onStayMode :() -> Unit) {
    data class ModeOption (
        val label :String,
        val icon : Painter
    )

    val modeIcon = if(detailMode) painterResource(id = R.drawable.detail_mode) else painterResource(id = R.drawable.list_mode)

    val modeOptions :List<ModeOption> = listOf(
        ModeOption(stringResource(id = R.string.detail_mode), painterResource(id = R.drawable.detail_mode)),
        ModeOption(stringResource(id = R.string.list_mode), painterResource(id = R.drawable.list_mode)),
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Spacer(modifier = Modifier.width(30.dp))

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint,
                contentColor = Color.Transparent,
            ),
            modifier = Modifier.height(50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.start_routine),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
            )
            Icon(
                Icons.Filled.PlayArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 5.dp)
            )
        }

        Button(
            onClick = onShowMode,
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.Transparent,
            ),
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)

        ) {
            Icon(
                painter = modeIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    /////////////////// Mode Dialog ///////////////////////
    if (showModeDialog) {
        Dialog(
            onDismissRequest = onShowMode,
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation,
                color = MaterialTheme.colorScheme.inversePrimary
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
                        for (option in modeOptions) {
                            val modeMatches =
                                detailMode && option.label == stringResource(id = R.string.detail_mode) || !detailMode && option.label == stringResource(
                                    id = R.string.list_mode
                                )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = if (modeMatches) MaterialTheme.colorScheme.secondary else Color.Transparent,
                                    border = if (modeMatches) BorderStroke(
                                        1.dp,
                                        MaterialTheme.colorScheme.secondary
                                    ) else BorderStroke(
                                        1.dp,
                                        MaterialTheme.colorScheme.secondary
                                    ),
                                    modifier = Modifier
                                        .height(100.dp)
                                        .width(100.dp)
                                        .padding(10.dp)
                                ) {
                                    IconButton(
                                        onClick = if(modeMatches) onStayMode else onChangeMode
                                    ) {
                                        Icon(
                                            painter = option.icon,
                                            contentDescription = option.label,
                                            modifier = Modifier.padding(25.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                Text(
                                    text = option.label,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


data class PopUpOption (
    val label :String,
    val icon : ImageVector
)

@Composable
fun getOptions(): List<PopUpOption> {

    return listOf(
        PopUpOption(stringResource(id = R.string.add_favourite), Icons.Default.FavoriteBorder),
        PopUpOption(stringResource(id = R.string.share), Icons.Default.Share),
    )
}

@Composable
fun RoutineMenu(time :Int) {

    val popUpOptions = getOptions()

    var showPopUp by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ) {
        Icon(
            Icons.Filled.KeyboardArrowLeft,
            contentDescription = null,
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
                tint = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 5.dp)
            )
            Text(
                text = "$time min",
                color = MaterialTheme.colorScheme.surfaceTint,
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
                    text = { Text(text = option.label, color = MaterialTheme.colorScheme.primary) },
                    onClick = { /* Handle edit! */ },
                    leadingIcon = {
                        Icon(
                            option.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
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
        color = Color(0x6FF5F5F5),
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
                            tint = MaterialTheme.colorScheme.surfaceTint,
                            modifier = Modifier
                                .padding(end = 3.dp)
                                .size(20.dp)
                        )
                        Text(
                            text = secs.toString() + stringResource(id = R.string.seconds),
                            color = MaterialTheme.colorScheme.surfaceTint
                        )
                    }
                }
            }
            if(reps != 0) {
                Text(
                    text = stringResource(id = R.string.repetitions) + reps,
                    color = MaterialTheme.colorScheme.surfaceTint,
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
        color = MaterialTheme.colorScheme.tertiary,
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
                tint = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(start = 30.dp, end = 50.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.time),
                contentDescription = stringResource(R.string.time_icon),
                tint = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier
                    .padding(end = 3.dp)
                    .size(20.dp)
            )
            Text(
                text = secs.toString() + stringResource(id = R.string.seconds),
                color = MaterialTheme.colorScheme.inversePrimary
            )
        }
    }
}