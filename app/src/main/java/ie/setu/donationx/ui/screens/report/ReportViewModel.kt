package ie.setu.donationx.ui.screens.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.donationx.data.DonationModel
import ie.setu.donationx.data.room.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject
constructor(private val repository: RoomRepository) : ViewModel() {
    private val _donations
            = MutableStateFlow<List<DonationModel>>(emptyList())
    val uiDonations: StateFlow<List<DonationModel>>
            = _donations.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { listOfDonations ->
                _donations.value = listOfDonations
            }
        }
    }

    fun deleteDonation(donation: DonationModel) {
        viewModelScope.launch {
            repository.delete(donation)
        }
    }
}