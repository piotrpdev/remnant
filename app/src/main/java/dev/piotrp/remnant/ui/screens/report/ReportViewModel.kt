package dev.piotrp.remnant.ui.screens.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.piotrp.remnant.data.model.RemnantModel
import dev.piotrp.remnant.firebase.services.AuthService
import dev.piotrp.remnant.firebase.services.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject
constructor(private val repository: FirestoreService,
            private val authService: AuthService
) : ViewModel() {
    private val _remnants
            = MutableStateFlow<List<RemnantModel>>(emptyList())
    val uiRemnants: StateFlow<List<RemnantModel>>
            = _remnants.asStateFlow()
    var iserror = mutableStateOf(false)
    var isloading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

    init { getRemnants() }

    fun getRemnants() {
        viewModelScope.launch {
            try {
                isloading.value = true
                repository.getAll().collect{ items ->
                        _remnants.value = items
                        iserror.value = false
                        isloading.value = false
                    }
                Timber.i("DVM RVM = : ${_remnants.value}")
            }
            catch(e:Exception) {
                iserror.value = true
                isloading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

    fun deleteRemnant(remnant: RemnantModel)
        = viewModelScope.launch {
            repository.delete(authService.email!!, remnant._id)
        }
}

