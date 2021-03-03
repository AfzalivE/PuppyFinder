/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.toPaddingValues
import dev.chrisbanes.accompanist.picasso.PicassoImage

@Composable
fun PuppyListScreen(navController: NavController) {
    var topAppBarSize by remember { mutableStateOf(0) }

    val puppyList = DogsApi.fetchDogs()
    PuppyListContent(navController, puppyList, topAppBarSize)

    InsetAwareTopAppBar(
        title = { Text(stringResource(R.string.title)) },
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.9f),
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { topAppBarSize = it.height }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PuppyListContent(
    navController: NavController,
    puppyList: List<Animal>,
    topAppBarSize: Int
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(count = 2),
        content = {
            items(puppyList) { puppy ->
                PuppyItem(puppy) {
                    navController.navigate("puppydetail/${puppy.id}")
                }
            }
        },
        contentPadding = LocalWindowInsets.current.systemBars.toPaddingValues(
            top = false,
            additionalTop = with(LocalDensity.current) { topAppBarSize.toDp() }
        )
    )
}

@Composable
fun PuppyItem(puppy: Animal, onClick: () -> Unit) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 8.dp, bottomEnd = 8.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            PicassoImage(
                data = puppy.photo,
                contentDescription = "Photo of ${puppy.name}",
                fadeIn = true,
                contentScale = ContentScale.FillHeight,
                loading = {
                    Box(Modifier.matchParentSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                },
                modifier = Modifier.height(240.dp)
            )
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(
                    text = puppy.name,
                    style = MaterialTheme.typography.h6.copy(fontSize = 14.sp)
                )
                Text(
                    text = puppy.species,
                    style = MaterialTheme.typography.body2.copy(
                        color = LocalContentColor.current.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    ),
                )
                AgeSexRow(
                    age = puppy.age,
                    sex = puppy.sex.name,
                    fontSize = 13.sp,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
