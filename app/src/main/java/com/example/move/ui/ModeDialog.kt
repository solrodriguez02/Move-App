package com.example.move.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.move.R
import com.example.move.util.getViewModelFactory

@Composable
fun ModeDialog(onShowMode: () -> Unit, viewModel: MainViewModel ) {

    var setListMode by remember { mutableStateOf(true) }

    if(setListMode) {
        setListMode = false
        viewModel.setIsListMode()
    }

    data class ModeOption (
        val label :String,
        val icon : Painter
    )

    val modeOptions :List<ModeOption> = listOf(
        ModeOption(stringResource(id = R.string.detail_mode), painterResource(id = R.drawable.detail_mode)),
        ModeOption(stringResource(id = R.string.list_mode), painterResource(id = R.drawable.list_mode)),
    )

    Dialog(
        onDismissRequest = onShowMode,
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(20.dp)
                .shadow(elevation = 10.dp),
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
                            !viewModel.uiState.listMode && option.label == stringResource(id = R.string.detail_mode) || viewModel.uiState.listMode && option.label == stringResource(
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
                                    onClick = { if(modeMatches) { /* do nothing */ } else viewModel.changeMode() }
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