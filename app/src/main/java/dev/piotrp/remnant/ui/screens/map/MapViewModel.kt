package dev.piotrp.remnant.ui.screens.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.piotrp.remnant.firebase.services.AuthService
import dev.piotrp.remnant.location.LocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationTracker: LocationService,
    private val authService: AuthService
) : ViewModel() {

    private val _currentLatLng = MutableStateFlow(LatLng(0.0, 0.0))
    val currentLatLng: StateFlow<LatLng> get() = _currentLatLng

    private val _hasPermissions = MutableStateFlow(false)
    val hasPermissions: StateFlow<Boolean> get() = _hasPermissions

    val email: String? get() = authService.email

    fun setPermissions(permissions: Boolean) {
            _hasPermissions.value = permissions
        }

    private fun setCurrentLatLng(latLng: LatLng) {
            _currentLatLng.value = latLng
        }

    fun getLocationUpdates() {
            viewModelScope.launch(Dispatchers.IO) {
                    locationTracker.getLocationFlow()
                        .collect {
                                it?.let { location ->
                                        setCurrentLatLng(
                                                LatLng(location.latitude,
                                                           location.longitude)
                                                    )
                                    }
                            }
                }
        }
}