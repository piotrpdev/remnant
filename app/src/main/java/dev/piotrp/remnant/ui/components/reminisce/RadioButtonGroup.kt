package dev.piotrp.remnant.ui.components.reminisce

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.piotrp.remnant.R
import dev.piotrp.remnant.ui.theme.RemnantTheme

@Composable
fun RadioButtonGroup(modifier: Modifier = Modifier,
                     onPaymentTypeChange: (String) -> Unit) {

    val radioOptions = listOf(
        stringResource(R.string.funny),
        stringResource(R.string.happy)
    )
    var paymentType by remember { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ){
        radioOptions.forEach { paymentText ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (paymentText == paymentType),
                    onClick = {
                        paymentType = paymentText
                        onPaymentTypeChange(paymentType)
                    }
                )
                Text(
                    text = paymentText,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RadioButtonPreview() {
    RemnantTheme {
        RadioButtonGroup(
            Modifier,
            onPaymentTypeChange = {}
        )
    }
}
