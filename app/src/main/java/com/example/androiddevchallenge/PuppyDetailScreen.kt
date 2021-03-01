package com.example.androiddevchallenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        ),
        horizontalAlignment = Alignment.CenterHorizontally
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
private fun DetailsCard(animal: Animal) {
    val sexIcon = if (animal.sex == Sex.FEMALE) R.drawable.gender_female else R.drawable.gender_male
    val childOrAdult = if (animal.age < 3) "Child" else "Adult"

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .offset(y = (-16).dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Text(text = animal.name)
                Icon(painter = painterResource(id = sexIcon), contentDescription = "")
            }
            Text(text = animal.species)
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = ""
                )
                Text(text = animal.location)
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 4.dp)
                )
                Text(text = quantityStringResource(id = R.plurals.months, quantity = animal.age, animal.age))
            }
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.background(
                        color = Color(
                            red = 253,
                            green = 218,
                            blue = 213
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ).padding(8.dp),
                    text = childOrAdult
                )
                Text(
                    modifier = Modifier.background(
                        color = Color(
                            red = 225,
                            green = 241,
                            blue = 253
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ).padding(8.dp),
                    text = animal.sex.name
                        .toLowerCase(Locale.getDefault())
                        .capitalize(Locale.getDefault())
                )
            }
        }
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