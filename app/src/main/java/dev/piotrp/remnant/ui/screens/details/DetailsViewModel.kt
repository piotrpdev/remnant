package dev.piotrp.remnant.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.piotrp.remnant.data.RemnantModel
import dev.piotrp.remnant.data.room.RoomRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject
constructor(private val repository: RoomRepository,
            savedStateHandle: SavedStateHandle
) : ViewModel() {

    var remnant = mutableStateOf(RemnantModel())
    val id: Int = checkNotNull(savedStateHandle["id"])

    init {
        viewModelScope.launch {
            repository.get(id).collect { objRemnant ->
                remnant.value = objRemnant
            }
        }
    }

    fun updateRemnant(remnant: RemnantModel) {
        viewModelScope.launch { repository.update(remnant) }
    }
}