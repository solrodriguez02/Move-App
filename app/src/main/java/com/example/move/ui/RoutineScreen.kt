package com.example.move.ui

import android.content.res.Configuration
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.move.R
import com.example.move.util.getViewModelFactory


@OptIn(ExperimentalCoilApi::class)
@Composable
fun RoutineScreen(onNavigateToExecute :(routineId:Int)->Unit, navController: NavController) {

    val config = LocalConfiguration.current
    val orientation = config.orientation

    var showRate by remember { mutableStateOf(false) }
    var showDescription by remember { mutableStateOf(false) }
    var showModeDialog by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf (3) }
    var cycleIndex by remember { mutableIntStateOf(0) }

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
                        if (exercise.title == stringResource(id = R.string.rest_compare)) {
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
                    }
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        RoutineExecutionMenu(
                            showModeDialog = showModeDialog,
                            onShowMode = { showModeDialog = !showModeDialog },
                            onNavigateToExecute = onNavigateToExecute)
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
            RoutineMenu(routine.time, navController)
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
                    showModeDialog = showModeDialog,
                    onShowMode = { showModeDialog = !showModeDialog },
                    onNavigateToExecute = onNavigateToExecute)
            }
        }
    }

}


@Composable
fun RoutineExecutionMenu(
    showModeDialog :Boolean,
    onShowMode :() -> Unit,
    onNavigateToExecute :(routineId:Int)->Unit,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
) {

    var setListMode by remember { mutableStateOf(true) }

    if(setListMode) {
        setListMode = false
        viewModel.setIsListMode()
    }

    val modeIcon: Painter = if(viewModel.uiState.listMode) painterResource(id = R.drawable.list_mode) else painterResource(id = R.drawable.detail_mode)

    Row(
        horizontalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Spacer(modifier = Modifier.width(30.dp))

        Button(
            onClick = { onNavigateToExecute(0) },
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

    if (showModeDialog) {
        ModeDialog(onShowMode)
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
        PopUpOption(stringResource(id = R.string.share), Icons.Default.Share)
    )
}

@Composable
fun RoutineMenu(time :Int, navController: NavController) {

    val popUpOptions = getOptions()
    var showPopUp by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }

    if(showShareDialog) {
        ShareDialog(onCancel = { showShareDialog = false }, id = 0)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(30.dp)
            )
        }
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
            DropdownMenuItem(
                text = { Text(text = popUpOptions[0].label, color = MaterialTheme.colorScheme.primary) },
                onClick = {  },
                leadingIcon = {
                    Icon(
                        popUpOptions[0].icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
            DropdownMenuItem(
                text = { Text(text = popUpOptions[1].label, color = MaterialTheme.colorScheme.primary) },
                onClick = { showShareDialog = true },
                leadingIcon = {
                    Icon(
                        popUpOptions[1].icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )

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