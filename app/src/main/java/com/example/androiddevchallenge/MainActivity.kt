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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.afzaln.puppyfinder.InsetAwareTopAppBar
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Turn off the decor fitting system windows,
        // which means we need to through handling insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    ProvideWindowInsets {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            var topAppBarSize by remember { mutableStateOf(0) }
            val navController = rememberNavController()

            NavHost(navController, startDestination = "puppylist") {
                composable("puppylist") { PuppyListScreen(navController, topAppBarSize) }
                composable("puppydetail/{puppyId}") {
                    PuppyDetailScreen(navController, it.arguments?.getString("puppyId")?.toLong()!!)
                }
            }

            InsetAwareTopAppBar(
                title = { Text(stringResource(R.string.title)) },
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.9f),
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { topAppBarSize = it.height }
            )
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}