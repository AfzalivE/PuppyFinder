package com.example.androiddevchallenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun PuppyDetailScreen() {
    PuppyDetailContent(
        animal = Animal(
            name = "Emily",
            species = "Munchkin Species",
            location = "Pontianak, Indonesia",
            age = 3,
            sex = Sex.FEMALE
        )
    )
}

@Composable
fun PuppyDetailContent(animal: Animal) {
    Column(
        modifier = Modifier.scrollable(
            rememberScrollState(),
            orientation = Orientation.Vertical
        ).fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.cute_cat),
            contentDescription = "${animal.name} photo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.height(320.dp)
        )
        DetailsCard(animal)
    }
}

@Composable
private fun DetailsCard(
    animal: Animal
) {
    val sexIcon = if (animal.sex == Sex.FEMALE) R.drawable.gender_female else R.drawable.gender_male
    val childOrAdult = if (animal.age < 3) "Child" else "Adult"

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
                    .padding(bottom = 16.dp)
                ,
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
                color = LocalContentColor.current.copy(alpha = 0.7f),
                style = MaterialTheme.typography.body2,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    modifier = Modifier
                        .background(
                            color = Color(
                                red = 253,
                                green = 218,
                                blue = 213
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 32.dp),
                    text = childOrAdult,
                    color = Color(
                        red = 245,
                        green = 84,
                        blue = 105
                    ),
                )
                Text(
                    modifier = Modifier
                        .background(
                            color = Color(
                                red = 225,
                                green = 241,
                                blue = 253
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 32.dp),
                    text = animal.sex.name
                        .toLowerCase(Locale.getDefault())
                        .capitalize(Locale.getDefault()),
                    color = Color(
                        red = 97,
                        green = 156,
                        blue = 252
                    )
                )
            }
        }
    }
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

data class Animal(
    val name: String,
    val species: String,
    val location: String,
    val age: Int,
    val sex: Sex
)

enum class Sex {
    MALE, FEMALE
}