package dev.piotrp.remnant.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.piotrp.remnant.data.DonationModel
import dev.piotrp.remnant.data.room.RoomRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject
constructor(private val repository: RoomRepository,
            savedStateHandle: SavedStateHandle
) : ViewModel() {

    var donation = mutableStateOf(DonationModel())
    val id: Int = checkNotNull(savedStateHandle["id"])

    init {
        viewModelScope.launch {
            repository.get(id).collect { objDonation ->
                donation.value = objDonation
            }
        }
    }

    fun updateDonation(donation: DonationModel) {
        viewModelScope.launch { repository.update(donation) }
    }
}