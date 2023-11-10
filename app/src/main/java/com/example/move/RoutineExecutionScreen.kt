package com.example.move

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay


@Composable
fun RoutineExecutionScreen() {

    val isDetailedMode = false

    var cycleIndex by remember { mutableIntStateOf(0) }

    var exerciseIndex by remember { mutableIntStateOf(0) }

    var currentExercise by remember { mutableStateOf<Exercise>(routine.cycles[cycleIndex].exercises[exerciseIndex]) }

    val exerciseCount = routine.cycles[cycleIndex].exercises.size

    var nextExercise by remember { mutableStateOf(false) }

    val initialValue = 1f

    var totalTime by remember { mutableLongStateOf(currentExercise.secs * 1000L) }

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }

    val cycleIcon = if(cycleIndex == 0) painterResource(id = R.drawable.warm_up) else if (cycleIndex == routine.cycles.size-1) painterResource(id = R.drawable.cooling) else painterResource(id = R.drawable.exercise)

    val isRestExercise = currentExercise.title == stringResource(id = R.string.rest_name)

    val textColor = if(isRestExercise && isDetailedMode) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary

    var newCycle by remember { mutableStateOf(true) }

    val hasOnlyReps = currentExercise.secs == 0

    var showExitPopUp by remember { mutableStateOf(false) }

    val config = LocalConfiguration.current

    val orientation = config.orientation

    if (showExitPopUp) {
        isTimerRunning = false
        ExitPopUp(onCancel = {
            showExitPopUp = false
            isTimerRunning = true
        })
    }

    if(!hasOnlyReps) {
        LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
            if (currentTime > 0 && isTimerRunning) {
                delay(100L)
                currentTime -= 100L
                value = 1 - (currentTime / totalTime.toFloat())
            }
        }
    }

    if (currentTime <= 0L && currentExercise.secs != 0 || nextExercise) {
        exerciseIndex++
        nextExercise = false
        if (exerciseIndex < exerciseCount) {
            LaunchedEffect(key1 = exerciseIndex) {
                currentExercise = routine.cycles[cycleIndex].exercises[exerciseIndex]
                currentTime = currentExercise.secs * 1000L
                totalTime = currentExercise.secs * 1000L
                delay(5000L)
            }
            isTimerRunning = true
        } else {
            cycleIndex++
            if (cycleIndex < routine.cycles.size) {
                LaunchedEffect(key1 = exerciseIndex) {
                    exerciseIndex = 0
                    currentExercise = routine.cycles[cycleIndex].exercises[exerciseIndex]
                    currentTime = currentExercise.secs * 1000L
                    totalTime = currentExercise.secs * 1000L
                    delay(5000L)
                }
                isTimerRunning = false
                newCycle = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isRestExercise && isDetailedMode) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.inversePrimary)
    ) {
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(state)
                .padding(20.dp)
        ) {

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {

                ExecutionHeader(
                    showExitPopUp = { showExitPopUp = true },
                    textColor = textColor,
                    newCycle = newCycle,
                    cycleIcon = cycleIcon,
                    isDetailedMode = isDetailedMode,
                    currentExercise = currentExercise
                )

                if (newCycle) {
                    NewCycleVerticalScreen(onStart = {
                        newCycle = false
                        isTimerRunning = true
                    }, cycleIcon = cycleIcon, cycleIndex = cycleIndex)
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(150.dp)
                            .width(400.dp)
                            .onSizeChanged { size = it }
                            .padding(top = if (isDetailedMode) 30.dp else 0.dp)
                    ) {
                        VerticalTimer(
                            totalTime = totalTime,
                            size = size,
                            currentExercise = currentExercise,
                            currentTime = currentTime,
                            value = value,
                            textColor = textColor
                        )
                    }

                    if (isDetailedMode)
                        VerticalDetailedMode(
                            currentExercise = currentExercise,
                            exerciseIndex = exerciseIndex,
                            exerciseCount = exerciseCount,
                            isRestExercise = isRestExercise,
                            textColor = textColor
                        )
                    else
                        VerticalListMode(
                            currentExercise = currentExercise,
                            exerciseIndex = exerciseIndex,
                            cycleIndex = cycleIndex,
                            isRestExercise = isRestExercise,
                            cycleIcon = cycleIcon
                        )

                    ExecutionMenu(
                        hasOnlyReps = hasOnlyReps,
                        onRefresh = {
                            currentTime = totalTime
                            isTimerRunning = true
                        },
                        onPlay = { isTimerRunning = !isTimerRunning },
                        onNext = { nextExercise = true },
                        isPaused = isTimerRunning && currentTime > 0L
                    )

                    if (isDetailedMode) NextExerciseBox(
                        exerciseIndex = exerciseIndex,
                        exerciseCount = exerciseCount,
                        cycleIndex = cycleIndex,
                        cycleIcon = cycleIcon,
                        isRestExercise = isRestExercise
                    )
                }
            } else {
                if(newCycle) {
                    NewCycleHorizontalScreen(onStart = {
                        newCycle = false
                        isTimerRunning = true
                    }, cycleIcon = cycleIcon, cycleIndex = cycleIndex,
                        textColor = textColor, onClose = { showExitPopUp = true })
                } else {
                    if (isDetailedMode)
                        HorizontalDetailedMode(
                            onClose = {
                                showExitPopUp = true
                                isTimerRunning = false
                            },
                            onRefresh = {
                                currentTime = totalTime
                                isTimerRunning = true
                            },
                            onPlay = { isTimerRunning = !isTimerRunning },
                            onNext = { nextExercise = true },
                            isPaused = isTimerRunning && currentTime > 0L,
                            textColor = textColor,
                            isRestExercise = isRestExercise,
                            currentExercise = currentExercise,
                            hasOnlyReps = hasOnlyReps,
                            totalTime = totalTime,
                            currentTime = currentTime,
                            value = value,
                            cycleIcon = cycleIcon,
                            exerciseCount = exerciseCount,
                            exerciseIndex = exerciseIndex,
                            cycleIndex = cycleIndex
                        )
                    else
                        HorizontalListMode(
                            onClose = {
                                showExitPopUp = true
                                isTimerRunning = false
                            },
                            onRefresh = {
                                currentTime = totalTime
                                isTimerRunning = true
                            },
                            onPlay = { isTimerRunning = !isTimerRunning },
                            onNext = { nextExercise = true },
                            isPaused = isTimerRunning && currentTime > 0L,
                            textColor = textColor,
                            isRestExercise = isRestExercise,
                            currentExercise = currentExercise,
                            hasOnlyReps = hasOnlyReps,
                            totalTime = totalTime,
                            currentTime = currentTime,
                            value = value,
                            cycleIcon = cycleIcon,
                            exerciseIndex = exerciseIndex,
                            cycleIndex = cycleIndex
                        )
                }
            }
        }

    }
}

@Composable
fun ExitPopUp(onCancel: () -> Unit) {
    Dialog(
        onDismissRequest = onCancel,
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.quit_routine_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(id = R.string.quit_routine_label),
                    modifier = Modifier.padding(vertical = 10.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel_name)
                        )
                    }
                    Button(
                        onClick = { /* go to explore */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.error,
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                    ) {
                        Text(
                            text = stringResource(id = R.string.quit_name)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExecutionMenu(hasOnlyReps :Boolean, onRefresh :() -> Unit, onPlay :() -> Unit, onNext :() -> Unit, isPaused :Boolean) {
    Surface(
        shape = RoundedCornerShape(50.dp),
        color = MaterialTheme.colorScheme.surfaceTint,
        modifier = Modifier
            .padding(vertical = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
        ) {
            if (!hasOnlyReps) {
                IconButton(
                    onClick = onRefresh
                ) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = onPlay) {
                    Icon(
                        if (isPaused) painterResource(id = R.drawable.pause) else painterResource(
                            id = R.drawable.play
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            IconButton(onClick = onNext) {
                Icon(
                    painterResource(id = R.drawable.skip_next),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
fun ExecutionHeader(showExitPopUp :() -> Unit, textColor : Color, newCycle :Boolean, cycleIcon: Painter, isDetailedMode: Boolean, currentExercise: Exercise) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = showExitPopUp,
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = null,
                tint = textColor
            )
        }
        if (isDetailedMode && !newCycle) {
            Text(
                text = currentExercise.title,
                fontSize = 24.sp,
                color = textColor
            )
        }
        if(!newCycle) {
            Icon(
                painter = cycleIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
fun HorizontalTimer(currentExercise :Exercise, value :Float, isList :Boolean = false) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawLine(
            color = Color.LightGray,
            start = Offset(if(isList) 0f else size.width / 2, 0f ),
            end = Offset(if(isList) 1000f else size.width / 2, if(isList) 0f else 500f),
            strokeWidth = 30f,
            cap = StrokeCap.Round
        )
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawLine(
            color = Color(0xFF5370F8),
            start = Offset(if(isList) 0f else size.width / 2, if(isList) 0f else 0f),
            end = Offset(if(isList) 1000 * if(currentExercise.secs == 0) 1f else (value) else size.width / 2, if(isList) 0f else if(currentExercise.secs == 0) 500f else value*500f ),
            strokeWidth = 30f,
            cap = StrokeCap.Round

        )
    }
}

@Composable
fun VerticalTimer(totalTime :Long, size : IntSize, currentExercise :Exercise, currentTime :Long, value :Float, textColor : Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawArc(
            color = Color.LightGray,
            startAngle = -160f,
            sweepAngle = 140f,
            useCenter = false,
            size = Size(
                size.width.toFloat(),
                size.height.toFloat()
            ),
            style = Stroke(10.dp.toPx(), cap = StrokeCap.Round)
        )
        drawArc(
            color = Color(0xFF5370F8),
            startAngle = -160f,
            sweepAngle = 140f * if(currentExercise.secs == 0) 1f else value,
            useCenter = false,
            size = Size(
                size.width.toFloat(),
                size.height.toFloat()
            ),
            style = Stroke(10.dp.toPx(), cap = StrokeCap.Round)
        )
    }
    Time(currentTime = currentTime, totalTime = totalTime, textColor = textColor, currentExercise = currentExercise)
}

@Composable
fun Time(currentTime :Long, totalTime :Long, textColor: Color, currentExercise: Exercise, isHorizontalList :Boolean = false) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        val currentSecs = currentTime / 1000L
        val stringSecs = String.format("%02d:%02d", currentSecs / 60, currentSecs % 60)

        Text(
            text = if (totalTime.toInt() != 0) stringSecs else stringResource(id = R.string.repetitions) + currentExercise.reps.toString(),
            fontSize = if(isHorizontalList) 30.sp else 44.sp,
            color = textColor
        )

        if (totalTime.toInt() != 0 && currentExercise.reps != 0 && !isHorizontalList) {
            Text(
                text = stringResource(id = R.string.repetitions) + currentExercise.reps.toString(),
                fontSize = 20.sp,
                color = textColor
            )
        }
        else {
            if(!isHorizontalList) {
                Spacer(Modifier.height(30.dp))
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun HorizontalListMode(onClose :() -> Unit, onRefresh :() -> Unit, onPlay :() -> Unit, onNext: () -> Unit,
                       isPaused: Boolean, textColor: Color, isRestExercise: Boolean, currentExercise: Exercise, hasOnlyReps: Boolean,
                       totalTime: Long, currentTime: Long, value: Float, cycleIcon: Painter, exerciseIndex: Int, cycleIndex: Int)
{
    Row {
        IconButton(
            onClick = onClose
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = null,
                tint = textColor
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 2 downTo 1) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                ) {
                    HorizontalExerciseListBox(
                        exerciseIndex = exerciseIndex - i,
                        cycleIndex = cycleIndex,
                        cycleIcon = cycleIcon
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.35f)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isRestExercise) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .padding(end = 20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        if (isRestExercise) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.height(150.dp)
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.rest),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.inversePrimary,
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .height(150.dp)
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp)),
                            ) {
                                Image(
                                    painter = rememberImagePainter(data = currentExercise.imageUrl),
                                    contentDescription = currentExercise.title,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                        Text(
                            text = currentExercise.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = if (isRestExercise) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            for (i in 1..2) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                ) {
                    HorizontalExerciseListBox(
                        exerciseIndex = exerciseIndex + i,
                        cycleIndex = cycleIndex,
                        cycleIcon = cycleIcon
                    )
                }
            }
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier.weight(0.6f),
            verticalArrangement = Arrangement.Top
        ) {
            Column {
                Time(currentTime = currentTime, totalTime = totalTime, textColor = textColor, currentExercise = currentExercise, isHorizontalList = true)
                HorizontalTimer(currentExercise = currentExercise, value = value, isList = true)
            }
        }
        Column(
            modifier = Modifier.weight(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ExecutionMenu(hasOnlyReps = hasOnlyReps, onRefresh = onRefresh, onPlay = onPlay, onNext = onNext, isPaused = isPaused)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun HorizontalExerciseListBox(exerciseIndex: Int, cycleIndex: Int, cycleIcon : Painter) {
    var type = -1

    if(exerciseIndex == -2) type = -1
    else if(exerciseIndex == -1) type = 0
    else if(exerciseIndex < routine.cycles[cycleIndex].exercises.size) {
        type = if(routine.cycles[cycleIndex].exercises[exerciseIndex].title == stringResource(id = R.string.rest_name)) 2 else 1
    }

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if(type == 2) MaterialTheme.colorScheme.tertiary else if(type == -1) Color.Transparent else MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .height(180.dp)
            .padding(end = 15.dp)
            .fillMaxWidth()
    ) {
        if (type == 0) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    cycleIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Text(
                    text = routine.cycles[cycleIndex].name,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontWeight = FontWeight.Bold
                )
            }

        } else if (type > 0) {
            val exercise = routine.cycles[cycleIndex].exercises[exerciseIndex]

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                if (type == 2) {
                    Icon(
                        painterResource(id = R.drawable.rest),
                        contentDescription = null,
                        modifier = Modifier.padding(bottom = 25.dp),
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                    ) {
                        Image(
                            painter = rememberImagePainter(data = exercise.imageUrl),
                            contentDescription = exercise.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Text(
                        text = exercise.title,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = exercise.secs.toString() + stringResource(id = R.string.seconds),
                    color = if(type == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun HorizontalDetailedMode(onClose :() -> Unit, onRefresh :() -> Unit, onPlay :() -> Unit, onNext: () -> Unit,
                           isPaused: Boolean, textColor: Color, isRestExercise: Boolean, currentExercise: Exercise,
                           hasOnlyReps: Boolean, totalTime: Long, currentTime: Long, value: Float, cycleIcon: Painter,
                           exerciseCount: Int, exerciseIndex: Int, cycleIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = onClose
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = null,
                tint = textColor
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.3f)
        ) {
            if (isRestExercise) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(230.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.rest),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.inversePrimary,
                        modifier = Modifier.size(50.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .height(230.dp)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(10.dp)),
                ) {
                    Image(
                        painter = rememberImagePainter(data = currentExercise.imageUrl),
                        contentDescription = currentExercise.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            ExecutionMenu(
                hasOnlyReps = hasOnlyReps,
                onRefresh = onRefresh,
                onPlay = onPlay,
                onNext = onNext,
                isPaused = isPaused
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.2f)
        ) {
            Time(currentTime = currentTime, totalTime = totalTime, textColor = textColor, currentExercise = currentExercise)
            HorizontalTimer(currentExercise = currentExercise, value = value,)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.25f)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .padding(bottom = 15.dp, top = 20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = currentExercise.title,
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = currentExercise.description,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            NextExerciseBox(
                exerciseIndex = exerciseIndex,
                exerciseCount = exerciseCount,
                cycleIndex = cycleIndex,
                cycleIcon = cycleIcon,
                isRestExercise = isRestExercise,
                isHorizontal = true
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun VerticalListMode(currentExercise: Exercise, exerciseIndex: Int, cycleIndex: Int, isRestExercise: Boolean, cycleIcon: Painter) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ){
        VerticalExerciseListBox(exerciseIndex = exerciseIndex-1, cycleIndex = cycleIndex, cycleIcon = cycleIcon)
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = if(isRestExercise) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 20.dp)
            ) {
                Text(
                    text = currentExercise.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = if(isRestExercise) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary
                )
                if(isRestExercise) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(150.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.rest),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.inversePrimary,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                    ) {
                        Image(
                            painter = rememberImagePainter(data = currentExercise.imageUrl),
                            contentDescription = currentExercise.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }

        for(i in 1..3) {
            VerticalExerciseListBox(exerciseIndex = exerciseIndex+i, cycleIndex = cycleIndex, cycleIcon = cycleIcon)
        }
    }
}

@Composable
fun VerticalExerciseListBox(exerciseIndex: Int, cycleIndex: Int, cycleIcon : Painter) {

    var type = -1

    if(exerciseIndex == -1) type = 0
    else if(exerciseIndex < routine.cycles[cycleIndex].exercises.size) {
        type = if(routine.cycles[cycleIndex].exercises[exerciseIndex].title == stringResource(id = R.string.rest_name)) 2 else 1
    }

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if(type == 2) MaterialTheme.colorScheme.tertiary else if(type == -1) Color.Transparent else MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(bottom = 10.dp)
            .padding(horizontal = 15.dp)
    ) {
        if (type == 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Icon(
                    cycleIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier.padding(end = 20.dp)
                )
                Text(
                    text = routine.cycles[cycleIndex].name,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontWeight = FontWeight.Bold
                )
            }

        } else if (type > 0) {
            val exercise = routine.cycles[cycleIndex].exercises[exerciseIndex]

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                if (type == 2) {
                    Icon(
                        painterResource(id = R.drawable.rest),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 25.dp),
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                } else {
                    Text(
                        text = exercise.title,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = exercise.secs.toString() + stringResource(id = R.string.seconds),
                    color = if(type == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun VerticalDetailedMode(currentExercise: Exercise, exerciseIndex :Int, exerciseCount :Int, isRestExercise :Boolean, textColor: Color) {

    if(isRestExercise) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(180.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.rest),
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(60.dp)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(50.dp)),
        ) {
            Image(
                painter = rememberImagePainter(data = currentExercise.imageUrl),
                contentDescription = currentExercise.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
    }
    Text(
        text = (exerciseIndex + 1).toString() + stringResource(id = R.string.dash) + exerciseCount,
        fontSize = 18.sp,
        color = textColor,
        modifier = Modifier.padding(10.dp)
    )

    if(!isRestExercise) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(top = 10.dp)
        ) {
            Text(
                text = currentExercise.description,
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    } else {
        Spacer(modifier = Modifier
            .height(90.dp)
            .padding(top = 10.dp))
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NextExerciseBox(exerciseIndex :Int, exerciseCount :Int, cycleIndex :Int, cycleIcon : Painter, isRestExercise: Boolean, isHorizontal :Boolean = false) {

    val textColor = if(isRestExercise) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if(isRestExercise) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 15.dp),

            ) {
            if (exerciseIndex == exerciseCount - 1) {
                val lastCycle = cycleIndex == routine.cycles.size - 1
                Row {
                    Icon(
                        if (lastCycle) painterResource(id = R.drawable.last_exercise) else cycleIcon,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.padding(horizontal = 30.dp)
                    )
                    Text(
                        text = if (lastCycle) stringResource(id = R.string.last_exercise) else routine.cycles[cycleIndex+1].name,
                        color = textColor
                    )
                }
            } else {
                val followingExercise = routine.cycles[cycleIndex].exercises[exerciseIndex + 1]
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(followingExercise.title == stringResource(id = R.string.rest_name)) {
                        Icon(
                            painterResource(id = R.drawable.rest),
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.padding(horizontal = 30.dp)
                        )
                    }
                    else {
                        Box(
                            modifier = Modifier
                                .height(60.dp)
                                .width(90.dp)
                                .padding(end = 15.dp)
                                .clip(RoundedCornerShape(10.dp)),
                        ) {
                            Image(
                                painter = rememberImagePainter(data = followingExercise.imageUrl),
                                contentDescription = followingExercise.title,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                    Column {
                        if(followingExercise.title != stringResource(id = R.string.rest_name)) {
                            Text(
                                text = if(isHorizontal) stringResource(id = R.string.next_name) else stringResource(id = R.string.next_exercise),
                                color = textColor
                            )
                        }
                        Text(
                            text = followingExercise.title,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewCycleHorizontalScreen(onStart :() -> Unit, cycleIcon: Painter, cycleIndex :Int, textColor: Color, onClose: () -> Unit) {
    Row {
        IconButton(
            onClick = onClose
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = null,
                tint = textColor
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 150.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.next_cycle),
                    modifier = Modifier.padding(top = 50.dp, bottom = 15.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = routine.cycles[cycleIndex].name,
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Icon(
                    cycleIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .size(50.dp),
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
            }

            Button(
                onClick = onStart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceTint,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(horizontal = 30.dp, vertical = 15.dp)
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}


@Composable
fun NewCycleVerticalScreen(onStart :() -> Unit, cycleIcon: Painter, cycleIndex :Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = stringResource(id = R.string.next_cycle),
            modifier = Modifier.padding(top = 50.dp, bottom = 15.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = routine.cycles[cycleIndex].name,
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Icon(
            cycleIcon,
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 100.dp)
                .size(50.dp),
            tint = MaterialTheme.colorScheme.surfaceTint
        )

        Button(
            onClick = onStart,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 15.dp)
        ) {
            Icon(
                Icons.Filled.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}


