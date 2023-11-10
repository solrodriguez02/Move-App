package com.example.move

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Karolo Dominguez",
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp)
                    .padding(start = 10.dp)
                    .padding(end = 16.dp)
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
                .padding(horizontal = 1.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.p_username),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "@KaroloDominguez",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(id = R.string.p_gender),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 190.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Male",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp)
                            .padding(start = 190.dp),
                        color = MaterialTheme.colorScheme.primary
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
                        .padding(horizontal = 28.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.p_first_name),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Karolo",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(id = R.string.p_last_name),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 190.dp)
                            .align(Alignment.CenterStart),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Dominguez",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 190.dp)
                            .padding(top = 42.dp),
                        color = MaterialTheme.colorScheme.primary
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
                        .padding(horizontal = 28.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.p_email),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "kdomi@gmail.com",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 42.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Text(
            text = stringResource(id = R.string.profile_settings),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 28.dp)
                .padding(bottom = 8.dp, top = 30.dp),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Button(
            onClick = {
                /* TODO */
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Face, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(
                    text = stringResource(id = R.string.mode_dialog_title),
                    modifier = Modifier.padding(start = 10.dp),
                    color= MaterialTheme.colorScheme.primary,
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
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null, tint= MaterialTheme.colorScheme.primary)
                Text(
                    text = stringResource(id = R.string.check_website_name),
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        OutlinedButton(
            onClick = {/* TODO */},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 28.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.ExitToApp , contentDescription = null, tint= Color.Red)
                Text(
                    text = stringResource(id = R.string.logout_name),
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}