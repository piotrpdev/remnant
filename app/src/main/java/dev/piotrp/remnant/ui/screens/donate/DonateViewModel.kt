package dev.piotrp.remnant.ui.screens.donate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.piotrp.remnant.data.RemnantModel
import dev.piotrp.remnant.data.room.RoomRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject
constructor(private val repository: RoomRepository) : ViewModel() {

    fun insert(remnants: RemnantModel)
            = viewModelScope.launch {
                repository.insert(remnants)
    }
}