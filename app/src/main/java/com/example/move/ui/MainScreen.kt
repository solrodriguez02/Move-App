package com.example.move.ui

import android.annotation.SuppressLint
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import com.example.move.R
import com.example.move.util.getViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.move.data.model.RoutinePreview

@Composable
fun ExploreScreen(
    onNavigateToProfile :(userId:Int)->Unit,
    onNavigateToRoutine :(routineId:Int)->Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: RoutineViewModel = viewModel(factory = getViewModelFactory())
) {

    var count by remember {
        mutableStateOf(true)
    }
    if(count) {
        viewModel.getRoutinePreviews()
        count = false
    }
    var routineData = viewModel.uiState.routinePreviews

    val widthSizeClass = windowSizeClass.widthSizeClass

    var applyFilters by remember { mutableStateOf(false) }

    if(applyFilters) {
        routineData = viewModel.uiState.routinePreviews
        applyFilters = false
    }

    @Composable
    fun headerAndFilters() {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Header(
                    title = stringResource(R.string.explore_name),
                    onNavigateToProfile = onNavigateToProfile
                )

                /////////////////// Filters ///////////////////////

                ExploreFilters(windowSizeClass = windowSizeClass, onApplyFilters = { applyFilters = true})
            }
        }
    }


    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.inversePrimary)
            .padding(
                start = if (showNavRail(
                        windowSizeClass,
                        LocalConfiguration.current
                    )
                ) 65.dp else 0.dp
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if ( !isHorizontalTablet(windowSizeClass) )  //isCompact(windowSizeClass)
                Spacer(modifier = Modifier.height(130.dp))
            else {
                headerAndFilters()
            }

            /////////////////// Explore Routines ///////////////////////

            if (routineData?.isEmpty() == false) {

                val isVertical = widthSizeClass == WindowWidthSizeClass.Compact
                val columns = when (widthSizeClass) {
                    WindowWidthSizeClass.Compact -> 2
                    WindowWidthSizeClass.Medium -> 4
                    WindowWidthSizeClass.Expanded -> 6
                    else -> 1
                }
                var distanceRoutines = if (isCompact(widthSizeClass)) 20.dp else 25.dp
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.padding(start = if (isVertical) 0.dp else 20.dp)
                ) {

                    for ((index, routine) in routineData.withIndex()) {
                        item {
                            RoutinePreview(
                                imageUrl = routine.detail ?: "",
                                title = routine.name,
                                routineId = routine.id ?:0,
                                leftSide = if (isVertical) index % 2 == 0 else false,
                                onNavigateToRoutine = onNavigateToRoutine
                            )
                        }
                    }
                    for (i in 1..if (isVertical) 1 else 3) {
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
                Header(
                    title = stringResource(R.string.explore_name),
                    onNavigateToProfile = onNavigateToProfile
                )

                /////////////////// Filters ///////////////////////

                ExploreFilters(
                    windowSizeClass = windowSizeClass, onApplyFilters = { applyFilters = true }, viewModel)
            }
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExploreFilters(windowSizeClass: WindowSizeClass, onApplyFilters :() -> Unit, viewModel: RoutineViewModel = viewModel(factory = getViewModelFactory())) {

    var difficultyExpanded by remember { mutableStateOf(false) }
    var elementsExpanded by remember { mutableStateOf(false) }
    var approachExpanded by remember { mutableStateOf(false) }
    var spaceExpanded by remember { mutableStateOf(false) }
    var scoreExpanded by remember { mutableStateOf(false) }
    var dateExpanded by remember { mutableStateOf(false) }

    var isSpaceCategory by remember { mutableStateOf(false) }
    var isDifficultyCategory by remember { mutableStateOf(false) }
    var isOrderedByDate by remember { mutableStateOf(false) }
    var isOrderedByDateDesc by remember { mutableStateOf(false) }
    var isOrderedByScore by remember { mutableStateOf(false) }

    var getAllRoutines by remember { mutableStateOf(false) }
    val filtersSelected = remember { mutableStateListOf<SelectedFilter>() }

    val widthSizeClass = windowSizeClass.widthSizeClass
    val isPhone = isPhone(windowSizeClass)
    val isHorizontalTablet = isHorizontalTablet(windowSizeClass)

    var showFilters by remember { mutableStateOf(!isPhone) }

    val difficultyCategory = stringResource(id = R.string.difficulty_category)
    val spaceCategory = stringResource(id = R.string.space_category)
    val dateCategory = stringResource(id = R.string.date_filter)
    val scoreCategory = stringResource(id = R.string.score_filter)

    val dateOptions = getDateOptions()

    if(getAllRoutines) {
        viewModel.getRoutinePreviews()
        onApplyFilters()
        getAllRoutines = false
    }

    @Composable
    fun Filter(filterName: String, category :String, isExpanded :Boolean, onExpanded :()->Unit, options :List<CategoryOption>) {

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
                    text = filterName,
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
                                text = option.label,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        onClick = {
                            onExpanded()
                            if(!filtersSelected.contains(SelectedFilter(category, option.category, option.label))) {
                                if(category == dateCategory) {
                                    isOrderedByDateDesc = option.category == dateOptions[1].category
                                }
                                if((category != dateCategory && category != difficultyCategory && category != spaceCategory && category != scoreCategory) ||
                                    (category == dateCategory && !isOrderedByDate) ||
                                    (category == spaceCategory && !isSpaceCategory) ||
                                    (category == difficultyCategory && !isDifficultyCategory) ||
                                    (category == scoreCategory && !isOrderedByScore)
                                ) {
                                    filtersSelected.add(SelectedFilter(category, option.category, option.label))
                                    when (category) {
                                        dateCategory -> isOrderedByDate = true
                                        difficultyCategory -> isDifficultyCategory = true
                                        spaceCategory -> isSpaceCategory = true
                                        scoreCategory -> isOrderedByScore = true
                                    }
                                }
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
            .padding(bottom = if (isPhone) 0.dp else 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            if ( !isHorizontalTablet )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = if(isPhone) 0.dp else 10.dp)
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

                @Composable
                fun loadCategories(cols: Int){
                    Text(
                        text = stringResource(id = R.string.categories_name),
                        color = MaterialTheme.colorScheme.primary
                    )
                    LazyVerticalGrid(columns = GridCells.Fixed(cols), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        item {
                            Filter(
                                filterName = stringResource(id = R.string.difficulty_filter),
                                category = stringResource(id = R.string.difficulty_category),
                                isExpanded = difficultyExpanded,
                                onExpanded = { difficultyExpanded = !difficultyExpanded },
                                options = getDifficultyOptions()
                            )
                        }
                        item {
                            Filter(
                                filterName = stringResource(id = R.string.elements_filter),
                                category = stringResource(id = R.string.elements_category),
                                isExpanded = elementsExpanded,
                                onExpanded = { elementsExpanded = !elementsExpanded },
                                options = getElementsOptions()
                            )
                        }
                        item {
                            Filter(
                                filterName = stringResource(id = R.string.approach_filter),
                                category = stringResource(id = R.string.approach_category),
                                isExpanded = approachExpanded,
                                onExpanded = { approachExpanded = !approachExpanded },
                                options = getApproachOptions()
                            )
                        }
                        item {
                            Filter(
                                filterName = stringResource(id = R.string.space_filter),
                                category = stringResource(id = R.string.space_category),
                                isExpanded = spaceExpanded,
                                onExpanded = { spaceExpanded = !spaceExpanded },
                                options = getSpaceOptions()
                            )
                        }
                    }
                }
                @Composable
                fun loadOrder(cols: Int){
                    Text(
                        text = stringResource(id = R.string.order_by_name),
                        color = MaterialTheme.colorScheme.primary
                    )
                    LazyVerticalGrid(columns = GridCells.Fixed(cols), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        item {
                            Filter(
                                filterName = stringResource(id = R.string.score_filter),
                                category = stringResource(id = R.string.score_category),
                                isExpanded = scoreExpanded,
                                onExpanded = { scoreExpanded = !scoreExpanded },
                                options = getScoreOptions()
                            )
                        }
                        item {
                            Filter(
                                stringResource(id = R.string.date_filter),
                                category = stringResource(id = R.string.date_filter),
                                isExpanded = dateExpanded,
                                onExpanded = { dateExpanded = !dateExpanded },
                                options = getDateOptions()
                            )
                        }

                        /////////////////// Selected Filters ///////////////////////

                        if (filtersSelected.isEmpty()) {
                            item {
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                }

                if (isHorizontalTablet) {
                    Box {
                        Row {
                            Column(
                                modifier = Modifier
                                    .width(600.dp)
                            ) {
                                loadCategories(cols = 4)
                            }
                            Column(modifier = Modifier
                                .width(320.dp)
                                .padding(start = 25.dp)) {
                                loadOrder(cols = 2)
                            }
                        }
                    }

                } else {
                    if ( isCompact(widthSizeClass)) {
                        loadCategories(cols = 2)
                        loadOrder(cols = 2)
                    } else {
                        loadCategories(cols = 4)
                        loadOrder(cols = 4)

                    }
                }


                if (filtersSelected.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.inversePrimary)
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    ) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.filters_selected),
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .weight(1f),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Button(
                                onClick = {
                                    showFilters = false
                                    viewModel.getFilteredRoutinePreviews(filtersSelected = filtersSelected, isOrderedByDate = isOrderedByDate, direction = if(isOrderedByDateDesc) "desc" else "asc")
                                    onApplyFilters()
                                },
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.surfaceTint
                                )
                            ) {
                                Text(
                                    text = stringResource(id = R.string.apply_filters),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(end = 25.dp)
                                )
                            }
                        }

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
                                            text = option.label,
                                            modifier = Modifier.padding(start = 10.dp),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        IconButton(
                                            onClick = {
                                                filtersSelected.remove(option)
                                                when(option.category) {
                                                    dateCategory -> isOrderedByDate = false
                                                    difficultyCategory -> isDifficultyCategory = false
                                                    spaceCategory -> isSpaceCategory = false
                                                    scoreCategory -> isOrderedByScore = false
                                                }
                                                if(filtersSelected.isEmpty()) {
                                                    getAllRoutines = true
                                                }
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
fun HomeScreen(onNavigateToProfile :(userId:Int)->Unit, onNavigateToRoutine :(routineId:Int)->Unit,
               windowSizeClass: WindowSizeClass, viewModel: RoutineViewModel = viewModel(factory = getViewModelFactory())) {

    var count by remember {
        mutableStateOf(true)
    }

    if(count) {
        viewModel.getFavouriteRoutines()
        viewModel.getPersonalRoutinePreviews()
        count = false
    }

    val favRoutines = viewModel.uiState.favRoutinePreviews
    val personalRoutines = viewModel.uiState.personalRoutinePreviews

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(
                start = if (showNavRail(
                        windowSizeClass,
                        LocalConfiguration.current
                    )
                ) 65.dp else 0.dp
            )
    ) {
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }

        Header(
            title = stringResource(R.string.home_name),
            isHome = true,
            onNavigateToProfile = onNavigateToProfile
        )
        Column(
            modifier = Modifier.verticalScroll(state)
        ) {
                RoutinesCarousel(
                    title = stringResource(id = R.string.favourites_title),
                    routineData = favRoutines ?: emptyList(),
                    onNavigateToRoutine = onNavigateToRoutine,
                    isFavs = true
                )
                RoutinesCarousel(
                    title = stringResource(id = R.string.your_routines_title),
                    routineData = personalRoutines ?: emptyList(),
                    onNavigateToRoutine = onNavigateToRoutine
                )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

}

@Composable
fun RoutinesCarousel(title :String, routineData :List<RoutinePreview>, onNavigateToRoutine :(routineId:Int)->Unit, isFavs :Boolean = false) {
    Text(
        text = title,
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 20.dp),
        color = MaterialTheme.colorScheme.primary
    )

    if(routineData.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 60.dp).fillMaxWidth()
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.not_found),
                    contentDescription = null,
                    tint = Color.Gray
                )
                Text(
                    text = if(isFavs) stringResource(id = R.string.no_favourites) else stringResource(id = R.string.no_createdByYou),
                    color = Color.Gray
                )
            }
        }
    } else {
        LazyRow {
            items(routineData) { routine ->
                Box(modifier = Modifier.padding(start = 10.dp, top = 10.dp)) {
                    RoutinePreview(
                        imageUrl = routine.detail ?: "",
                        title = routine.name,
                        routineId = routine.id ?: 0,
                        onNavigateToRoutine = onNavigateToRoutine
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.width(20.dp))
            }
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


@OptIn(ExperimentalCoilApi::class)
@Composable
fun RoutinePreview(imageUrl: String, title: String, routineId: Int, leftSide: Boolean = false, onNavigateToRoutine :(routineId:Int)->Unit,
                   viewModel: RoutineViewModel = viewModel(factory = getViewModelFactory())) {    Column(
        horizontalAlignment = if(leftSide) Alignment.End else Alignment.Start,
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Button(
            shape = RoundedCornerShape(40.dp),
            onClick = { onNavigateToRoutine(routineId) },
            contentPadding = PaddingValues(0.dp),
            colors =  ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent),
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
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }
    }
}

