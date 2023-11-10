package com.example.move

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.move.ui.theme.MoveTheme
import kotlinx.coroutines.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoveTheme(dynamicColor = false) {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { NavBar(navController = navController) }
                ) {
                    MoveNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun NavBar(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(Screen.ExploreScreen, Screen.HomeScreen)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    selectedItem = if(currentRoute == Screen.ExploreScreen.route) 0 else 1

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 25.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(shape = RoundedCornerShape(100.dp))
                .height(60.dp)
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(horizontal = 30.dp)
                .width(130.dp)
        ) {
            items.forEachIndexed { index, item ->

                val selectedPosition =  if (selectedItem == index) Modifier.padding(15.dp) else Modifier.padding(16.dp)
                val selectedColor = if (selectedItem == index) MaterialTheme.colorScheme.surfaceTint else MaterialTheme.colorScheme.inversePrimary

                Button(
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { screenRoute ->
                                popUpTo(screenRoute) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                    ),
                    modifier = Modifier.width(60.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = selectedPosition.then(Modifier.fillMaxSize())
                    ) {
                        Icon(
                            item.icon,
                            contentDescription = "item $index",
                            tint = selectedColor,

                            )
                        if (selectedItem == index) {
                            Divider(
                                color = MaterialTheme.colorScheme.surfaceTint,
                                thickness = 3.dp,
                                modifier = Modifier
                                    .width(25.dp)
                                    .padding(top = 1.dp)
                                    .clip(shape = RoundedCornerShape(20.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}

