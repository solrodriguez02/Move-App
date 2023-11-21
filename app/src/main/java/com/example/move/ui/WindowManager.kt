package com.example.move.ui

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.AndroidViewConfiguration
import androidx.compose.ui.platform.LocalConfiguration

// useful functions

public fun isCompact( windowSizeClass: WindowWidthSizeClass) : Boolean{
    return windowSizeClass == WindowWidthSizeClass.Compact
}

public fun isPhone( windowSizeClass: WindowSizeClass) :Boolean {
    return windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact || windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
}

public fun isHorizontalTablet( windowSizeClass: WindowSizeClass) : Boolean {
    return windowSizeClass.widthSizeClass== WindowWidthSizeClass.Expanded && windowSizeClass.heightSizeClass>= WindowHeightSizeClass.Medium
}

fun showNavRail( windowSizeClass: WindowSizeClass, configuration: Configuration) : Boolean {
    return windowSizeClass.widthSizeClass== WindowWidthSizeClass.Expanded && configuration.orientation ==  ORIENTATION_LANDSCAPE && windowSizeClass.heightSizeClass > WindowHeightSizeClass.Compact
}

fun isHorizontalPhone( windowSizeClass: WindowSizeClass) : Boolean {
    return windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
}

fun isVertical( configuration: Configuration ) : Boolean {
    return configuration.orientation ==  ORIENTATION_PORTRAIT
}