package dev.piotrp.remnant.ui.screens.reminisce

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.piotrp.remnant.data.model.RemnantModel
import dev.piotrp.remnant.firebase.services.AuthService
import dev.piotrp.remnant.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RemnantViewModel @Inject
constructor(private val repository: FirestoreService,
    private val authService: AuthService)
    : ViewModel() {
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    fun insert(remnant: RemnantModel) =
        viewModelScope.launch {
        try {
            isLoading.value = true
            repository.insert(authService.email!!,remnant)
            isErr.value = false
            isLoading.value = false
        } catch (e: Exception) {
            isErr.value = true
            error.value = e
            isLoading.value = false
        }
            Timber.i("DVM Insert Message = : ${error.value.message} and isError ${isErr.value}")
    }
}
