package com.example.move.ui

import android.content.res.Configuration
import android.media.AudioManager
import android.media.ToneGenerator
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
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay
import com.example.move.R
import com.example.move.data.model.Cycle
import com.example.move.data.model.CycleExercise
import com.example.move.util.getViewModelFactory


//var cycles :MutableList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>> = emptyList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>>().toMutableList()

@Composable
fun RoutineExecutionScreen(
    onNavigateToFinish :(routineId:Int)->Unit,
    navController: NavController,
    routineId: Int,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    routineViewModel: RoutineViewModel = viewModel(factory = getViewModelFactory())
) {

    var setSettings by remember { mutableStateOf(true) }

    if(setSettings) {
        setSettings = false
        viewModel.setIsListMode()
        viewModel.setSound()
        routineViewModel.getRoutine(routineId)
    }

    val cycles = routineViewModel.uiState.currentRoutine?.cycles?.entries?.toMutableList() ?: emptyList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>>().toMutableList()

    if(cycles.isNotEmpty()) {

        var uiState = routineViewModel.uiState

        val isDetailedMode = !viewModel.uiState.listMode

        val isSoundOn = viewModel.uiState.sound

        var cycleIndex by remember { mutableIntStateOf(uiState.cycleIndex) }

        var exerciseIndex by remember { mutableIntStateOf(uiState.exerciseIndex) }

        var currentExercise by remember { mutableStateOf<CycleExercise>(cycles[cycleIndex].value[exerciseIndex]) }

        val exerciseCount = cycles[cycleIndex].value.size

        var nextExercise by remember { mutableStateOf(false) }

        val initialValue = 1f

        var totalTime by remember { mutableLongStateOf(currentExercise.duration * 1000L) }

        var size by remember {
            mutableStateOf(IntSize.Zero)
        }
        var value by remember {
            mutableFloatStateOf(initialValue)
        }
        var currentTime by remember {
            mutableLongStateOf(totalTime)
        }
        var isTimerRunning by remember {
            mutableStateOf(false)
        }

        val cycleIcon =
            if (cycleIndex == 0) painterResource(id = R.drawable.warm_up) else if (cycleIndex == cycles.size.minus(
                    1
                )
            ) painterResource(id = R.drawable.cooling) else painterResource(id = R.drawable.exercise)

        val isRestExercise =
            currentExercise.exercise?.name == stringResource(id = R.string.rest_compare)

        val textColor =
            if (isRestExercise && isDetailedMode) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary

        var newCycle by remember { mutableStateOf(true) }

        val hasOnlyReps = currentExercise.duration == 0

        var showExitPopUp by remember { mutableStateOf(false) }

        val config = LocalConfiguration.current

        val orientation = config.orientation

        if (isSoundOn && (currentTime == 1 * 1000L || currentTime == 2 * 1000L || currentTime == 3 * 1000L)) {
            ToneGenerator(AudioManager.STREAM_MUSIC, 100).startTone(
                ToneGenerator.TONE_PROP_BEEP,
                200
            )
        }

        if (showExitPopUp) {
            isTimerRunning = false
            WarningDialog(
                onCancel = {
                    showExitPopUp = false
                    isTimerRunning = true
                },
                onDo = { navController.popBackStack() },
                title = stringResource(id = R.string.quit_routine_title),
                message = stringResource(id = R.string.quit_routine_label)
            )
        }

        if (!hasOnlyReps) {
            LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
                if (currentTime > 0 && isTimerRunning) {
                    delay(100L)
                    currentTime -= 100L
                    uiState = uiState.copy(currentTime = currentTime)
                    value = 1 - (currentTime / totalTime.toFloat())
                }
            }
        }

        if (cycleIndex == cycles.size.minus(1) && (exerciseIndex == exerciseCount || exerciseIndex == exerciseCount.minus(1)) && (currentTime.toInt() == 0 && !hasOnlyReps || nextExercise)) {
            onNavigateToFinish(routineViewModel.uiState.currentRoutine?.id ?: 0)
        } else {
            if (currentTime <= 0L && currentExercise.duration != 0 || nextExercise) {
                exerciseIndex++
                uiState = uiState.copy(exerciseIndex = exerciseIndex)
                nextExercise = false
                if (exerciseIndex < (exerciseCount)) {
                    LaunchedEffect(key1 = exerciseIndex) {
                        currentExercise = cycles[cycleIndex].value[exerciseIndex]
                        currentTime = currentExercise.duration * 1000L
                        uiState = uiState.copy(currentTime = currentTime)
                        totalTime = currentExercise.duration * 1000L
                        delay(5000L)
                    }
                    isTimerRunning = true
                } else {
                    cycleIndex++
                    uiState = uiState.copy(cycleIndex = cycleIndex)
                    if (cycleIndex < (cycles.size)) {
                        LaunchedEffect(key1 = exerciseIndex) {
                            exerciseIndex = 0
                            uiState = uiState.copy(exerciseIndex = exerciseIndex)
                            currentExercise = cycles[cycleIndex].value[exerciseIndex]
                            currentTime = currentExercise.duration * 1000L
                            uiState = uiState.copy(currentTime = currentTime)
                            totalTime = currentExercise.duration * 1000L
                            delay(5000L)
                        }
                        isTimerRunning = false
                        newCycle = true
                    }
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
                        }, cycleIcon = cycleIcon, cycleIndex = cycleIndex, cycles = cycles)
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
                                cycleIcon = cycleIcon,
                                cycles = cycles
                            )

                        ExecutionMenu(
                            hasOnlyReps = hasOnlyReps,
                            onRefresh = {
                                currentTime = totalTime
                                isTimerRunning = true
                            },
                            onPlay = { isTimerRunning = !isTimerRunning },
                            onNext = { nextExercise = true },
                            isPaused = isTimerRunning && currentTime > 0L,
                            isSoundOn = isSoundOn
                        )

                        if (isDetailedMode) NextExerciseBox(
                            exerciseIndex = exerciseIndex,
                            exerciseCount = exerciseCount,
                            cycleIndex = cycleIndex,
                            cycleIcon = cycleIcon,
                            isRestExercise = isRestExercise,
                            cycles = cycles
                        )
                    }
                } else {
                    if (newCycle) {
                        NewCycleHorizontalScreen(onStart = {
                            newCycle = false
                            isTimerRunning = true
                        },
                            cycleIcon = cycleIcon,
                            cycleIndex = cycleIndex,
                            textColor = textColor,
                            onClose = { showExitPopUp = true },
                            cycles = cycles
                        )
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
                                cycleIndex = cycleIndex,
                                isSoundOn = isSoundOn,
                                cycles = cycles
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
                                cycleIndex = cycleIndex,
                                isSoundOn = isSoundOn,
                                cycles = cycles
                            )
                    }
                }
            }
        }
    }
}

@Composable
fun ExecutionMenu(hasOnlyReps :Boolean, onRefresh :() -> Unit, onPlay :() -> Unit, onNext :() -> Unit, isPaused :Boolean, isSoundOn: Boolean) {
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
            IconButton(
                onClick = {
                    onNext()
                    if(isSoundOn) {
                        ToneGenerator(AudioManager.STREAM_MUSIC, 100).startTone(ToneGenerator.TONE_PROP_BEEP, 200)
                    }
                }
            ) {
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
fun ExecutionHeader(showExitPopUp :() -> Unit, textColor : Color, newCycle :Boolean, cycleIcon: Painter, isDetailedMode: Boolean, currentExercise: CycleExercise) {
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
                text = currentExercise.exercise?.name ?: "",
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
fun HorizontalTimer(currentExercise :CycleExercise, value :Float, isList :Boolean = false) {
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
            end = Offset(if(isList) 1000 * if(currentExercise.duration == 0) 1f else (value) else size.width / 2, if(isList) 0f else if(currentExercise.duration == 0) 500f else value*500f ),
            strokeWidth = 30f,
            cap = StrokeCap.Round

        )
    }
}

@Composable
fun VerticalTimer(totalTime :Long, size : IntSize, currentExercise :CycleExercise, currentTime :Long, value :Float, textColor : Color) {
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
            sweepAngle = 140f * if(currentExercise.duration == 0) 1f else value,
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
fun Time(currentTime :Long, totalTime :Long, textColor: Color, currentExercise: CycleExercise, isHorizontalList :Boolean = false) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        val currentSecs = currentTime / 1000L
        val stringSecs = String.format("%02d:%02d", currentSecs / 60, currentSecs % 60)

        Text(
            text = if (totalTime.toInt() != 0) stringSecs else stringResource(id = R.string.repetitions) + currentExercise.repetitions.toString(),
            fontSize = if(isHorizontalList) 30.sp else 44.sp,
            color = textColor
        )

        if (totalTime.toInt() != 0 && currentExercise.repetitions != 0 && !isHorizontalList) {
            Text(
                text = stringResource(id = R.string.repetitions) + currentExercise.repetitions.toString(),
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
                       isPaused: Boolean, textColor: Color, isRestExercise: Boolean, currentExercise: CycleExercise, hasOnlyReps: Boolean,
                       totalTime: Long, currentTime: Long, value: Float, cycleIcon: Painter, exerciseIndex: Int, cycleIndex: Int, isSoundOn: Boolean,
                       cycles: MutableList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>>)
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
                        cycleIcon = cycleIcon,
                        cycles = cycles
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
                                    painter = rememberImagePainter(data = currentExercise.exercise?.metadata?.image), // IMAGEN !!!!
                                    contentDescription = currentExercise.exercise?.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                        Text(
                            text = currentExercise.exercise?.name ?: "",
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
                        cycleIcon = cycleIcon,
                        cycles = cycles
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
            ExecutionMenu(hasOnlyReps = hasOnlyReps, onRefresh = onRefresh, onPlay = onPlay, onNext = onNext, isPaused = isPaused, isSoundOn = isSoundOn)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun HorizontalExerciseListBox(exerciseIndex: Int, cycleIndex: Int, cycleIcon : Painter, cycles :MutableList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>>) {
    var type = -1

    if(exerciseIndex == -2) type = -1
    else if(exerciseIndex == -1) type = 0
    else if(exerciseIndex < cycles[cycleIndex].value.size) {
        type = if(cycles[cycleIndex].value[exerciseIndex].exercise?.name == stringResource(id = R.string.rest_compare)) 2 else 1
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
                    text = cycles[cycleIndex].key.name ?: "",
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontWeight = FontWeight.Bold
                )
            }

        } else if (type > 0) {
            val exercise = cycles[cycleIndex].value[exerciseIndex]

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
                            painter = rememberImagePainter(data = exercise.exercise?.metadata?.image), // IMAGE !!!
                            contentDescription = exercise.exercise?.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Text(
                        text = exercise.exercise?.name ?: "",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = exercise.duration.toString() + stringResource(id = R.string.seconds),
                    color = if(type == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun HorizontalDetailedMode(onClose :() -> Unit, onRefresh :() -> Unit, onPlay :() -> Unit, onNext: () -> Unit,
                           isPaused: Boolean, textColor: Color, isRestExercise: Boolean, currentExercise: CycleExercise,
                           hasOnlyReps: Boolean, totalTime: Long, currentTime: Long, value: Float, cycleIcon: Painter,
                           exerciseCount: Int, exerciseIndex: Int, cycleIndex: Int, isSoundOn: Boolean,
                           cycles :MutableList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>>) {
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
                        painter = rememberImagePainter(data = currentExercise.exercise?.metadata?.image), // IMAGE !!!
                        contentDescription = currentExercise.exercise?.name ?: "",
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
                isPaused = isPaused,
                isSoundOn = isSoundOn
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.2f)
        ) {
            Time(currentTime = currentTime, totalTime = totalTime, textColor = textColor, currentExercise = currentExercise)
            HorizontalTimer(currentExercise = currentExercise, value = value)
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
                        text = currentExercise.exercise?.name ?: "",
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = currentExercise.exercise?.detail ?: "",
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
                isHorizontal = true,
                cycles = cycles
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun VerticalListMode(currentExercise: CycleExercise, exerciseIndex: Int, cycleIndex: Int, isRestExercise: Boolean, cycleIcon: Painter, cycles :MutableList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>>) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ){
        VerticalExerciseListBox(exerciseIndex = exerciseIndex-1, cycleIndex = cycleIndex, cycleIcon = cycleIcon, cycles = cycles)
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
                    text = currentExercise.exercise?.name ?: "",
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
                            painter = rememberImagePainter(data = currentExercise.exercise?.metadata?.image), // IMAGE !!!
                            contentDescription = currentExercise.exercise?.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }

        for(i in 1..3) {
            VerticalExerciseListBox(exerciseIndex = exerciseIndex+i, cycleIndex = cycleIndex, cycleIcon = cycleIcon, cycles = cycles)
        }
    }
}

@Composable
fun VerticalExerciseListBox(exerciseIndex: Int, cycleIndex: Int, cycleIcon : Painter, cycles :MutableList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>>) {

    var type = -1

    if(exerciseIndex == -1) type = 0
    else if(exerciseIndex < cycles[cycleIndex].value.size) {
        type = if(cycles[cycleIndex].value[exerciseIndex].exercise?.name == stringResource(id = R.string.rest_compare)) 2 else 1
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
                    text = cycles[cycleIndex].key.name ?: "",
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontWeight = FontWeight.Bold
                )
            }

        } else if (type > 0) {
            val exercise = cycles[cycleIndex].value[exerciseIndex]

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
                        text = exercise.exercise?.name ?: "",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = exercise.duration.toString() + stringResource(id = R.string.seconds),
                    color = if(type == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun VerticalDetailedMode(currentExercise: CycleExercise, exerciseIndex :Int, exerciseCount :Int, isRestExercise :Boolean, textColor: Color) {

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
                painter = rememberImagePainter(data = currentExercise.exercise?.metadata?.image), // IMAGE !!!
                contentDescription = currentExercise.exercise?.name,
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
                text = currentExercise.exercise?.detail ?: "",
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
fun NextExerciseBox(exerciseIndex :Int, exerciseCount :Int, cycleIndex :Int, cycleIcon : Painter, isRestExercise: Boolean, isHorizontal :Boolean = false, cycles: MutableList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>>) {

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
                val lastCycle = cycleIndex == cycles.size - 1
                Row {
                    Icon(
                        if (lastCycle) painterResource(id = R.drawable.last_exercise) else cycleIcon,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.padding(horizontal = 30.dp)
                    )
                    Text(
                        text = if (lastCycle) stringResource(id = R.string.last_exercise) else cycles[cycleIndex+1].key.name ?: "",
                        color = textColor
                    )
                }
            } else {
                val followingExercise = cycles[cycleIndex].value[exerciseIndex + 1]
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(followingExercise.exercise?.name == stringResource(id = R.string.rest_compare)) {
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
                                painter = rememberImagePainter(data =  followingExercise.exercise?.metadata?.image), // IMAGE !!!
                                contentDescription = followingExercise.exercise?.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                    Column {
                        if(followingExercise.exercise?.name != stringResource(id = R.string.rest_compare)) {
                            Text(
                                text = if(isHorizontal) stringResource(id = R.string.next_name) else stringResource(id = R.string.next_exercise),
                                color = textColor
                            )
                        }
                        Text(
                            text = followingExercise.exercise?.name ?: "",
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewCycleHorizontalScreen(onStart :() -> Unit, cycleIcon: Painter, cycleIndex :Int, textColor: Color, onClose: () -> Unit, cycles :MutableList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>>) {
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
                    text = cycles[cycleIndex].key.name ?: "",
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
fun NewCycleVerticalScreen(onStart :() -> Unit, cycleIcon: Painter, cycleIndex :Int, cycles: MutableList<MutableMap.MutableEntry<Cycle, List<CycleExercise>>>) {
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
            text = cycles[cycleIndex].key.name ?: "",
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


