package com.example.move.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.move.R

private fun navigate(item : Screen, navController: NavController){
    navController.navigate(item.route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}


@Composable
fun NavigationIcon(item: Screen, isSelected: Boolean, selectedColor: Color ) {
    Icon(
        item.icon,
        contentDescription = "item ${item.title}",
        tint = selectedColor,
        )
    if (isSelected) {
        Divider(
            color = MaterialTheme.colorScheme.surfaceTint,
            thickness = 3.dp,
            modifier = Modifier
                .width(25.dp)
                .padding(top = 26.dp)
                .clip(shape = RoundedCornerShape(20.dp))
        )
    }
}

@Composable
fun NavBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if(currentRoute == Screen.ExploreScreen.route || currentRoute == Screen.HomeScreen.route) {
        var selectedItem by remember { mutableIntStateOf(0) }
        val items = listOf(Screen.ExploreScreen, Screen.HomeScreen)
        selectedItem =
            if (currentRoute == Screen.ExploreScreen.route) 0 else if (currentRoute == Screen.HomeScreen.route) 1 else -1

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

                    val selectedPosition =
                        if (selectedItem == index) Modifier.padding(15.dp) else Modifier.padding(16.dp)
                    val selectedColor =
                        if (selectedItem == index) MaterialTheme.colorScheme.surfaceTint else MaterialTheme.colorScheme.inversePrimary

                    Button(
                        onClick = { navigate(item, navController) },
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
}


@Composable
fun NavigationRailBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == Screen.ExploreScreen.route || currentRoute == Screen.HomeScreen.route) {
        var selectedItem by remember { mutableIntStateOf(0) }
        val items = listOf(Screen.ExploreScreen, Screen.HomeScreen)
        selectedItem =
            if (currentRoute == Screen.ExploreScreen.route) 0 else if (currentRoute == Screen.HomeScreen.route) 1 else -1

    NavigationRail(
         modifier = Modifier
             .width(80.dp)
             .padding(all = 12.dp)
             .clip(shape = RoundedCornerShape(100.dp))
             .fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.tertiary,
        header = {
            Image(painter = painterResource(R.drawable.logo_without_color)
                , contentDescription = "icon", modifier = Modifier.padding(top = 20.dp).size(25.dp))
        }
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiary)
                .fillMaxHeight()
                .padding(bottom = 15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom)
        ) {
            items.forEachIndexed { index, item ->
                val selectedColor =
                    if (selectedItem == index) MaterialTheme.colorScheme.surfaceTint else MaterialTheme.colorScheme.inversePrimary

                NavigationRailItem(
                    selected = selectedItem == index,
                    colors = NavigationRailItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.tertiary
                    ),
                    icon = {
                        NavigationIcon(
                            item = item, isSelected = selectedItem == index, selectedColor = selectedColor
                        )
                    },
                    onClick = {
                        navigate(item, navController)
                    },

                )
            }
        }

    }

    }
}
