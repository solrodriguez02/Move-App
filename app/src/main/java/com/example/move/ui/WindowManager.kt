package com.example.move.ui

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

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