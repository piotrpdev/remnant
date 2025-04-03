package dev.piotrp.remnant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import dev.piotrp.remnant.ui.screens.home.HomeScreen
import dev.piotrp.remnant.ui.theme.RemnantTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RemnantTheme { HomeScreen() }
        }
    }
}
