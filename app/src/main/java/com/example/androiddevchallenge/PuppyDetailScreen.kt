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

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import dev.chrisbanes.accompanist.picasso.PicassoImage

@Composable
fun PuppyDetailScreen(puppyId: Long = 0L) {
    val puppy = DogsApi.fetchDog(puppyId)
    PuppyDetailContent(puppy)
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsHeight()
            .background(MaterialTheme.colors.background.copy(alpha = 0.4f))
    )
}

@Composable
fun PuppyDetailContent(animal: Animal, topAppBarSize: Int = 0) {
    Column(
        modifier = Modifier
            .scrollable(
                rememberScrollState(),
                orientation = Orientation.Vertical
            )
            .fillMaxHeight()
            .padding(top = with(LocalDensity.current) { topAppBarSize.toDp() })
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PicassoImage(
            data = animal.photo,
            contentDescription = "Photo of ${animal.name}",
            contentScale = ContentScale.FillWidth,
            loading = {
                Box(Modifier.matchParentSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            },
            fadeIn = true,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 320.dp)
        )
        DetailsCard(animal)
    }
}

@Composable
private fun DetailsCard(
    animal: Animal
) {
    val sexIcon = if (animal.sex == Sex.Female) R.drawable.gender_female else R.drawable.gender_male

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(320.dp)
            .offset(y = (-84).dp),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = animal.name,
                    style = MaterialTheme.typography.h6
                )
                Icon(
                    painter = painterResource(id = sexIcon),
                    contentDescription = "",
                    tint = Color.Magenta,
                    modifier = Modifier.height(32.dp)
                )
            }
            Text(
                text = animal.species,
                style = MaterialTheme.typography.body2.copy(LocalContentColor.current.copy(alpha = 0.7f)),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            IconText(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = Icons.Outlined.LocationOn.name,
                color = Color(red = 68, green = 138, blue = 254),
                text = animal.location
            )
            IconText(
                imageVector = Icons.Outlined.Refresh,
                contentDescription = Icons.Outlined.Refresh.name,
                color = Color(red = 91, green = 221, blue = 127),
                text = quantityStringResource(
                    id = R.plurals.months,
                    quantity = animal.age,
                    animal.age
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            AgeSexRow(animal.age, animal.sex.name)
        }
    }
}

@Composable
fun AgeSexRow(
    age: Int,
    sex: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.body1.fontSize,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.SpaceEvenly
) {
    val childOrAdult = if (age < 3) "Child" else "Adult"

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
    ) {
        TextBox(
            text = childOrAdult,
            textColor = Color(red = 245, green = 84, blue = 105),
            backgroundColor = Color(red = 253, green = 218, blue = 213),
            style = MaterialTheme.typography.body1.copy(fontSize = fontSize)
        )
        TextBox(
            text = sex,
            textColor = Color(red = 97, green = 156, blue = 252),
            backgroundColor = Color(red = 225, green = 241, blue = 253),
            style = MaterialTheme.typography.body1.copy(fontSize = fontSize)
        )
    }
}

@Composable
private fun TextBox(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    style: TextStyle = MaterialTheme.typography.body1
) {
    Text(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 4.dp, horizontal = 16.dp),
        text = text,
        color = textColor,
        style = style
    )
}

@Composable
fun IconText(
    imageVector: ImageVector,
    contentDescription: String,
    color: Color,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = color,
            modifier = Modifier.offset(x = (-4).dp)
        )
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.body2
        )
    }
}
