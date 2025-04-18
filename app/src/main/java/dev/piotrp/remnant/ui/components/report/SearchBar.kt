package dev.piotrp.remnant.ui.components.report

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.piotrp.remnant.R
import dev.piotrp.remnant.ui.theme.RemnantTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchChange: (String) -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        maxLines = 2,
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onSearchChange(searchQuery)
        },
        modifier = modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.enter_search)) }
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchBarPreview() {
    RemnantTheme {
        SearchBar(
            Modifier,
            onSearchChange = {}
        )
    }
}