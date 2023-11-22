package com.example.move.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.move.R
import com.example.move.util.getViewModelFactory


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    windowSizeClass: WindowSizeClass ) {

    var setValues by remember { mutableStateOf(true) }

    if(setValues) {
        setValues = false
        viewModel.getCurrentUser()
        viewModel.setSound()
    }

    val currentUser = viewModel.uiState.currentUser

    var setListMode by remember { mutableStateOf(true) }
    var showModeDialog by remember { mutableStateOf(false) }
    var showWarningDialog by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }
    var soundOn by remember { mutableStateOf(viewModel.uiState.sound) }

    if(setListMode) {
        setListMode = false
        viewModel.setIsListMode()
    }

    if(showModeDialog) {
        ModeDialog(onShowMode = { showModeDialog = !showModeDialog })
    }

    if(showWarningDialog) {
        WarningDialog(onCancel = { showWarningDialog = false },
            onDo = { viewModel.logout() },
            title = stringResource(id = R.string.logout_warning_title),
            message = stringResource(id = R.string.logout_warning_message))
    }

    if(showShareDialog) {
        ShareDialog(
            onCancel = { showShareDialog = false },
            isWeb = true
       )
    }

    if(!viewModel.uiState.isAuthenticated) {
        navController.navigate(Screen.SignInScreen.route)
    }

    val icon: (@Composable () -> Unit)? = if (soundOn) {
        {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        null
    }
    val isHorizontalPhone = isHorizontalPhone(windowSizeClass)
    val isCompact = isHorizontalPhone || windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val fontSize = if (isCompact) 14.sp else if ( windowSizeClass.heightSizeClass < WindowHeightSizeClass.Expanded ) 16.sp else 18.sp
    val horizontalBorder = 25.dp
    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier.padding( if (isCompact) 10.dp else 15.dp)
    ) {
        Icon(
            Icons.Filled.Close,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
    }

    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo( 0 ) }

    Box( contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize().padding(20.dp)) {
    Column(
        modifier = Modifier.widthIn(max = 600.dp).verticalScroll(state),
        ) {
        /*
        if (isHorizontalPhone)
            Spacer(modifier = Modifier.padding(20.dp))
*/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalBorder).padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = (currentUser?.firstName ?: "") + " " + (currentUser?.lastName ?: ""),
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp, start = 10.dp, end = 16.dp)
                    .then(Modifier.padding(0.dp)),
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Image(
                painter = rememberImagePainter(
                    data = "https://profilemagazine.com/wp-content/uploads/2020/04/Ajmere-Dale-Square-thumbnail.jpg"
                ),
                contentDescription = stringResource(id = R.string.profile_image),
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = RoundedCornerShape(100.dp))
            )
        }
        Text(
            text = stringResource(id = R.string.profile_information),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 20.dp),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalBorder)
                ) {
                    Text(
                        text = stringResource(id = R.string.p_username),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = fontSize
                    )
                    Text(
                        text = "@" + currentUser?.username,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = fontSize
                    )
                    Text(
                        text = stringResource(id = R.string.p_gender),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 190.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = fontSize
                    )
                    Text(
                        text = currentUser?.gender ?: "",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp)
                            .padding(start = 190.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = fontSize
                    )

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalBorder + 5.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.p_first_name),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = fontSize
                    )
                    Text(
                        text = currentUser?.firstName ?: "",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = fontSize
                    )
                    Text(
                        text = stringResource(id = R.string.p_last_name),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 190.dp)
                            .align(Alignment.CenterStart),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = fontSize
                    )
                    Text(
                        text = currentUser?.lastName ?: "",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 190.dp)
                            .padding(top = 42.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = fontSize
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 120.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalBorder)
                ) {
                    Text(
                        text = stringResource(id = R.string.p_email),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = fontSize
                    )
                    Text(
                        text = currentUser?.email ?: "",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = fontSize
                    )
                }
            }
        }

        Text(
            text = stringResource(id = R.string.profile_settings),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = horizontalBorder)
                .padding(bottom = 8.dp, top = 30.dp),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(bottom = 5.dp),
        ) {
            Text(
                text = stringResource(id = R.string.sound_title),
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = soundOn,
                onCheckedChange = {
                    soundOn = it
                    viewModel.changeSound()
                },
                thumbContent = icon,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.inversePrimary,
                    checkedTrackColor = MaterialTheme.colorScheme.surfaceTint,
                    checkedBorderColor = Color.Transparent,
                    uncheckedTrackColor = Color.Transparent,
                    uncheckedBorderColor = MaterialTheme.colorScheme.surfaceTint,
                    uncheckedThumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Button(
            onClick = { showModeDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalBorder),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val modeIcon: Painter =
                    if (viewModel.uiState.listMode) painterResource(id = R.drawable.list_mode) else painterResource(
                        id = R.drawable.detail_mode
                    )
                Icon(modeIcon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(
                    text = stringResource(id = R.string.mode_dialog_title) +
                            if (viewModel.uiState.listMode) stringResource(id = R.string.list_mode) else stringResource(
                                id = R.string.detail_mode
                            ),
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        Button(
            onClick = { showShareDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalBorder),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(id = R.string.check_website_name),
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        OutlinedButton(
            onClick = { showWarningDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = horizontalBorder),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = Color.Red
                )
                Text(
                    text = stringResource(id = R.string.logout_name),
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
        Spacer(modifier = Modifier.padding(20.dp))
    }
}