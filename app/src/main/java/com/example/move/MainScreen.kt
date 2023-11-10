package com.example.move

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter


///////////// API //////////////////////////////////////////////////////////////////////////////////////////////////////////

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

///////////// API //////////////////////////////////////////////////////////////////////////////////////////////////////////


@Composable
fun ExploreScreen(onNavigateToProfile :(userId:Int)->Unit) {

    val config = LocalConfiguration.current
    val orientation = config.orientation

    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(130.dp))

            /////////////////// Explore Routines ///////////////////////

            if (routineData.isNotEmpty()) {

                val isVertical = orientation == Configuration.ORIENTATION_PORTRAIT
                LazyVerticalGrid(
                    columns = GridCells.Fixed(if(isVertical) 2 else 4),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.padding(start = if(isVertical) 0.dp else 20.dp)
                ) {

                    for((index, routine) in routineData.withIndex()) {
                        item {
                            RoutinePreview(
                                imageUrl = routine.imageUrl,
                                title = routine.title,
                                time = routine.time,
                                leftSide = if (isVertical) index % 2 == 0 else false
                            )
                        }
                    }
                    for(i in 1..if(isVertical) 1 else 3) {
                        item {
                            /* empty item for spacer in odd routine count */
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            } else {
                Column(
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

        /////////////////// Header ///////////////////////

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Header(title = stringResource(R.string.explore_name), onNavigateToProfile = onNavigateToProfile)

                /////////////////// Filters ///////////////////////

                ExploreFilters()
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RoutinePreview(imageUrl: String, title: String, time: Int, leftSide: Boolean = false) {
    Column(
        horizontalAlignment = if(leftSide) Alignment.End else Alignment.Start,
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Surface(
            color = Color(0x00FFFFFF),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
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
                        contentDescription = stringResource(id = R.string.routine_image),
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
                    color = MaterialTheme.colorScheme.primary,
                )

                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = stringResource(R.string.time_icon),
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )

                    Text(
                        text = "$time'",
                        color = MaterialTheme.colorScheme.surfaceTint,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExploreFilters() {

    val config = LocalConfiguration.current
    val orientation = config.orientation

    data class SelectedFilter (
        val category :String,
        val filter :String
    )

    val difficultyOptions = listOf(stringResource(id = R.string.d_easy), stringResource(id = R.string.d_medium), stringResource(id = R.string.d_difficult))

    val approachOptions = listOf(
        stringResource(id = R.string.a_aerobic), stringResource(id = R.string.a_cardio),
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

    val scoreOptions = listOf(
        stringResource(id = R.string.sc_bad), stringResource(id = R.string.sc_fair), stringResource(id = R.string.sc_good),
        stringResource(id = R.string.sc_very_good), stringResource(id = R.string.sc_excelent)
    )

    val dateOptions = listOf(
        stringResource(id = R.string.da_ascending), stringResource(id = R.string.da_descending)
    )

    var difficultyExpanded by remember { mutableStateOf(false) }
    var elementsExpanded by remember { mutableStateOf(false) }
    var approachExpanded by remember { mutableStateOf(false) }
    var spaceExpanded by remember { mutableStateOf(false) }
    var scoreExpanded by remember { mutableStateOf(false) }
    var dateExpanded by remember { mutableStateOf(false) }

    val filtersSelected = remember { mutableStateListOf<SelectedFilter>() }

    var showFilters by remember { mutableStateOf(false) }

    @Composable
    fun Filter(category :String, isExpanded :Boolean, onExpanded :()->Unit, options :List<String>) {
        Column {
            Button(
                onClick = onExpanded,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = Color.Black
                ),
                modifier = Modifier.width(150.dp)
            ) {
                Text(
                    text = category,
                    fontSize = 16.sp
                )
                Icon(
                    if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null
                )
            }
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = onExpanded,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                for (option in options) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        onClick = {
                            if(!filtersSelected.contains(SelectedFilter(category, option))) {
                                filtersSelected.add(
                                    SelectedFilter(category, option)
                                )
                            }
                        },
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inversePrimary)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.filters_title),
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = { showFilters = !showFilters }) {
                    Icon(
                        if (showFilters) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (showFilters) {

                LazyVerticalGrid(columns = GridCells.Fixed(if(orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4), horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                    item {
                        Text(
                            text = stringResource(id = R.string.categories_name),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    for (i in 1..if(orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 3) {
                        item {/* Empty item */}
                    }
                    item {
                        Filter(category = stringResource(id = R.string.difficulty_filter), isExpanded = difficultyExpanded, onExpanded = {difficultyExpanded = !difficultyExpanded}, options = difficultyOptions)
                    }
                    item {
                        Filter(category = stringResource(id = R.string.elements_filter), isExpanded = elementsExpanded, onExpanded = {elementsExpanded = !elementsExpanded}, options = elementsOptions)
                    }
                    item {
                        Filter(category = stringResource(id = R.string.approach_filter), isExpanded = approachExpanded, onExpanded = {approachExpanded = !approachExpanded}, options = approachOptions)
                    }
                    item {
                        Filter(category = stringResource(id = R.string.space_filter), isExpanded = spaceExpanded, onExpanded = {spaceExpanded = !spaceExpanded}, options = spaceOptions)
                    }
                    item {
                        Text(
                            text = stringResource(id = R.string.order_by_name),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    for (i in 1..if(orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 3) {
                        item {/* Empty item */}
                    }
                    item {
                        Filter(category = stringResource(id = R.string.score_filter), isExpanded = scoreExpanded, onExpanded = {scoreExpanded = !scoreExpanded}, options = scoreOptions)
                    }
                    item {
                        Filter(category = stringResource(id = R.string.date_filter), isExpanded = dateExpanded, onExpanded = {dateExpanded = !dateExpanded}, options = dateOptions)
                    }
                    if(filtersSelected.isEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }

                }

                /////////////////// Selected Filters ///////////////////////

                if (filtersSelected.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.inversePrimary)
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.filters_selected),
                            modifier = Modifier.padding(vertical = 10.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            for (option in filtersSelected) {
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier
                                        .height(40.dp)
                                        .padding(bottom = 10.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = option.filter,
                                            modifier = Modifier.padding(start = 10.dp),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        IconButton(
                                            onClick = {
                                                filtersSelected.remove(option)
                                            }
                                        ) {
                                            Icon(
                                                Icons.Filled.Clear,
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp),
                                                tint = MaterialTheme.colorScheme.primary
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
    }
}

@Composable
fun HomeScreen(onNavigateToProfile :(userId:Int)->Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inversePrimary)
    ) {
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }

        Header(title = stringResource(R.string.home_name), isHome = true, onNavigateToProfile = onNavigateToProfile)
        Column(
            modifier = Modifier.verticalScroll(state)
        ) {
            RoutinesCarousel(title = stringResource(id = R.string.favourites_title), routineData)
            RoutinesCarousel(title = stringResource(id = R.string.your_routines_title), routineData)
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun RoutinesCarousel(title :String, routines :List<RoutineItemData>) {
    Text(
        text = title,
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 20.dp),
        color = MaterialTheme.colorScheme.primary
    )

    LazyRow {
        items(routines) { routine ->
            Box(modifier = Modifier.padding(start = 10.dp, top = 10.dp)) {
                RoutinePreview(
                    imageUrl = routine.imageUrl,
                    title = routine.title,
                    time = routine.time
                )
            }
        }
        item{
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Header(title: String, onNavigateToProfile :(userId:Int)->Unit, isHome :Boolean = false) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 20.dp, top = 15.dp, end = 15.dp, bottom = if (isHome) 10.dp else 0.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.primary
        )
        Button(
            onClick = { onNavigateToProfile(0) },
            contentPadding = PaddingValues(0.dp),
            colors =  ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent),
        ) {
            Image(
                painter = rememberImagePainter(
                    data = "https://profilemagazine.com/wp-content/uploads/2020/04/Ajmere-Dale-Square-thumbnail.jpg"
                ),
                contentDescription = stringResource(id = R.string.profile_image),
                modifier = Modifier
                    .size(60.dp)
                    .clip(shape = RoundedCornerShape(100.dp)),
            )
        }
    }
}