package dev.piotrp.remnant.ui.components.reminisce

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

// https://developer.android.com/develop/ui/compose/components/slider#advanced-implementation
@Composable
fun RemnantTypeIntensitySlider(
    onRemnantTypeIntensityChange: (Float) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(1f) }

    Column {
        Text(
            text = "Intensity",
        )
        Spacer(modifier = Modifier.height(8.dp))
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onRemnantTypeIntensityChange(sliderPosition)
            },
            steps = 8,
            valueRange = 1f..10f
        )
        Text(text = sliderPosition.roundToInt().toString())
    }
}