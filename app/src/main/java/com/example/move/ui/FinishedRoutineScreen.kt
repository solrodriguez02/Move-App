package com.example.move.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.move.R

@Composable
fun FinishedRoutineScreen(onNavigateToHome :() -> Unit) {
    val options = getButtonsOptions()
    var score by remember { mutableIntStateOf (0) }
    var showShareDialog by remember { mutableStateOf (false) }
    val rated = false

    if(showShareDialog) {
        ShareDialog(onCancel = { showShareDialog = false }, id = 0)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inversePrimary)
    ) {
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }

        Column(
            modifier = Modifier
                .verticalScroll(state)
                .padding(20.dp)
                .fillMaxSize()
        ) {

            Text(
                text = stringResource(id = R.string.finish_title),
                modifier = Modifier.padding(top = 15.dp, bottom = 20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Button(
                onClick = { /* pending function */ },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        options[0].icon,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    Text(
                        text = options[0].label
                    )
                }
            }


            Button(
                onClick = { showShareDialog = true },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        options[1].icon,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    Text(
                        text = options[1].label
                    )
                }
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 100.dp)
            ) {
                if (rated) {
                    Text(
                        text = stringResource(id = R.string.already_scored),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.score_label),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

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
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Button(
                onClick = onNavigateToHome,
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceTint,
                    contentColor = MaterialTheme.colorScheme.inversePrimary
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.continue_home),
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color.White
                )
                Icon(
                    Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

data class ButtonOption (
    val label :String,
    val icon : ImageVector
)

@Composable
fun getButtonsOptions(): List<ButtonOption> {

    return listOf(
        ButtonOption(stringResource(id = R.string.add_favourite), Icons.Default.FavoriteBorder),
        ButtonOption(stringResource(id = R.string.share), Icons.Default.Share),
    )
}