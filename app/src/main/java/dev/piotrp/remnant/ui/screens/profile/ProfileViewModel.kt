package dev.piotrp.remnant.ui.screens.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.piotrp.remnant.firebase.services.AuthService
import dev.piotrp.remnant.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val firestoreService: FirestoreService,
) : ViewModel() {

    val displayName get() = authService.currentUser?.displayName.toString()
    val photoUri get() = authService.customPhotoUri
    val email get() = authService.email.toString()

    fun signOut() {
        viewModelScope.launch { authService.signOut() }
    }

    fun updatePhotoUri(uri: Uri) {
        viewModelScope.launch {
            authService.updatePhoto(uri)
            firestoreService.updatePhotoUris(email,photoUri!!)
        }
    }
}