package com.example.move

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable

fun OtherScreen(id :Int?) {
    Column {
       Text(
           text = "Received id ${id?:"null"}"
       )
    }
}