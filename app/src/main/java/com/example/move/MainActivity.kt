package com.example.move

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.move.ui.theme.MoveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoveTheme {
                // A surface container using the 'background' color from the theme
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Greeting("Karolo Dominguez")
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Row(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp)
                    .padding(start = 10.dp)
                    .padding(end = 16.dp)
                    .then(Modifier.padding(0.dp)),
                fontSize = 30.sp
            )
                Image(
                    painter = rememberImagePainter(
                        data = "https://profilemagazine.com/wp-content/uploads/2020/04/Ajmere-Dale-Square-thumbnail.jpg"
                    ),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(120.dp)
                        .clip(shape = RoundedCornerShape(100.dp))
                )
        }
        Text(
            text = "Information",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .then(modifier.align(Alignment.Start))
                .padding(top = 28.dp)
                .padding(horizontal = 28.dp),
            fontSize = 24.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 1.dp)
        ) {
            Row(
                modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp)
                ) {
                    Text(
                        text = "Username",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart),
                    )
                    Text(
                        text = "@KaroloDominguez",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp),
                    )
                    Text(
                        text = "Gender",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart)
                        .padding(start = 190.dp),
                    )
                    Text(
                        text = "Male",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp)
                            .padding(start = 190.dp),
                    )

                }
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp)
                ) {
                    Text(
                        text = "First Name",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart),
                    )
                    Text(
                        text = "Karolo",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp),
                    )
                    Text(
                        text = "Last Name",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 190.dp)
                            .align(Alignment.CenterStart),
                    )
                    Text(
                        text = "Dominguez",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 190.dp)
                            .padding(top = 42.dp),
                    )
                }
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(top = 120.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp)
                ) {
                    Text(
                        text = "Email",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart),
                    )
                    Text(
                        text = "kdomi@gmail.com",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp),
                    )
                }
            }
        }
        Button(
            onClick = {
                /* TODO */
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp, vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                Text(
                    text = "Edit Profile",
                    modifier = modifier.padding(start = 10.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Text(
            text = "Settings",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .then(modifier.align(Alignment.Start))
                .padding(horizontal = 28.dp)
                .padding(bottom = 8.dp),
            fontSize = 24.sp
        )
        Button(
            onClick = {
                /* TODO */
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Face, contentDescription = null, tint = Color.Black)
                Text(
                    text = "Change view mode",
                    modifier = modifier.padding(start = 10.dp),
                    color= Color.Black,
                )
            }
        }
        Button(
            onClick = {
                /* TODO */
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null, tint= Color.Black)
                Text(
                    text = "Check our website",
                    modifier = modifier.padding(start = 10.dp),
                    color = Color.Black,
                )
            }
        }
        OutlinedButton(
            onClick = {/* TODO */},
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp).padding(horizontal = 28.dp),
            border = BorderStroke(1.dp, Color.Red),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.ExitToApp , contentDescription = null, tint= Color.Red)
                Text(
                    text = "Log Out",
                    modifier = modifier.padding(start = 10.dp),
                    color = Color.Red,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoveTheme {
        MyApp()
    }
}