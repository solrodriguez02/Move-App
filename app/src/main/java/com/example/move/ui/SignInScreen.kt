package com.example.move.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.move.R
import com.example.move.util.getViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Snackbar
import androidx.compose.ui.text.style.TextAlign
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController, viewModel: MainViewModel = viewModel(factory = getViewModelFactory())) {

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    if(viewModel.uiState.isAuthenticated) {
        NavBar(navController = navController)
        navController.navigate(Screen.ExploreScreen.route)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(state)
    ) {

        Image(
            painter =  painterResource(id = R.drawable.move_backgroundless),
            contentDescription = stringResource(id = R.string.logo_img),
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
        )

        Text(
            text = stringResource(id = R.string.enter_to_account),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 30.dp)
        )

        Column {
            Text(
                text = stringResource(id = R.string.p_username),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 30.dp, bottom = 10.dp, start = 5.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text(stringResource(id = R.string.sign_in_username)) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.primary,
                    containerColor = Color.Transparent
                )
            )

            Text(
                text = stringResource(id = R.string.p_password),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp, start = 5.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(stringResource(id = R.string.sign_in_password)) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.primary,
                    containerColor = Color.Transparent
                ),
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Button(
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint,
            ),
            onClick = {
                viewModel.login(username, password)
            },
            modifier = Modifier.padding(top = 30.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sign_in_title),
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = 18.sp,
                modifier = Modifier.padding(5.dp)
            )
        }

        if (viewModel.uiState.error != null) {
            Snackbar(
                modifier = Modifier
                    .padding(15.dp),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Text(
                    text = "Error: " + (viewModel.uiState.error?.message ?: ""),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }


        }
    }
}