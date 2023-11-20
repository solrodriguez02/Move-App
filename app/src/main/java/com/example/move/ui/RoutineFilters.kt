package com.example.move.ui
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.move.R

data class SelectedFilter (
    val category :String,
    val filter :String
)

data class CategoryOption (
    val category :String,
    val label :String
)

@Composable
fun getDifficultyOptions() :List<CategoryOption> {
    return listOf(
        CategoryOption("Easy", stringResource(id = R.string.d_easy)),
        CategoryOption("Medium", stringResource(id = R.string.d_medium)),
        CategoryOption("Difficult", stringResource(id = R.string.d_difficult))
    )
}

@Composable
fun getApproachOptions() :List<CategoryOption> {
    return listOf(
        CategoryOption("Aerobic", stringResource(id = R.string.a_aerobic)),
        CategoryOption("Cardio", stringResource(id = R.string.a_cardio)),
        CategoryOption("Flex", stringResource(id = R.string.a_flex)),
        CategoryOption("Crossfit", stringResource(id = R.string.a_crossfit)),
        CategoryOption("Functional", stringResource(id = R.string.a_functional)),
        CategoryOption("HIIT", stringResource(id = R.string.a_hiit)),
        CategoryOption("Pilates", stringResource(id = R.string.a_pilates)),
        CategoryOption("Resistance", stringResource(id = R.string.a_resistance)),
        CategoryOption("Stretching", stringResource(id = R.string.a_streching)),
        CategoryOption("Strength", stringResource(id = R.string.a_strength)),
        CategoryOption("Weight", stringResource(id = R.string.a_weight)),
        CategoryOption("Yoga", stringResource(id = R.string.a_yoga))
    )
}

@Composable
fun getElementsOptions() :List<CategoryOption> {
    return listOf(
        CategoryOption("None", stringResource(id = R.string.e_none)),
        CategoryOption("Ankle Weights", stringResource(id = R.string.e_ankle)),
        CategoryOption("Resistance band", stringResource(id = R.string.e_band)),
        CategoryOption("Dumbbell", stringResource(id = R.string.e_dumbell)),
        CategoryOption("Kettlebell", stringResource(id = R.string.e_kettlebell)),
        CategoryOption("Mat", stringResource(id = R.string.e_mat)),
        CategoryOption("Foam roller", stringResource(id = R.string.e_roller)),
        CategoryOption("Rope", stringResource(id = R.string.e_rope)),
        CategoryOption("Step", stringResource(id = R.string.e_step))
    )
}

@Composable
fun getSpaceOptions() :List<CategoryOption> {
    return listOf(
        CategoryOption("Ideal for reduced spaces", stringResource(id = R.string.s_reduced)),
        CategoryOption("Requires some space", stringResource(id = R.string.s_some)),
        CategoryOption("Much space is needed", stringResource(id = R.string.s_much))
    )
}

@Composable
fun getScoreOptions() :List<CategoryOption> {
    return listOf(
        CategoryOption("Bad", stringResource(id = R.string.sc_bad)),
        CategoryOption("Good", stringResource(id = R.string.sc_good)),
        CategoryOption("Excellent", stringResource(id = R.string.sc_excelent))
    )
}

@Composable
fun getDateOptions() :List<CategoryOption> {
    return listOf(
        CategoryOption("Ascending", stringResource(id = R.string.da_ascending)),
        CategoryOption("Descending", stringResource(id = R.string.da_descending))
    )
}
