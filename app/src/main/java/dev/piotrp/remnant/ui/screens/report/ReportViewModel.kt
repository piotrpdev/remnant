package dev.piotrp.remnant.ui.screens.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.piotrp.remnant.data.RemnantModel
import dev.piotrp.remnant.data.room.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject
constructor(private val repository: RoomRepository) : ViewModel() {
    private val _remnants
            = MutableStateFlow<List<RemnantModel>>(emptyList())
    val uiRemnants: StateFlow<List<RemnantModel>>
            = _remnants.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { listOfRemnants ->
                _remnants.value = listOfRemnants
            }
        }
    }

    fun deleteRemnant(remnant: RemnantModel) {
        viewModelScope.launch {
            repository.delete(remnant)
        }
    }
}