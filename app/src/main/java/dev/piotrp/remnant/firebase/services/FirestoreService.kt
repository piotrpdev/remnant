package dev.piotrp.remnant.firebase.services

import android.net.Uri
import dev.piotrp.remnant.data.model.RemnantModel
import kotlinx.coroutines.flow.Flow

typealias Remnant = RemnantModel
typealias Remnants = Flow<List<Remnant>>

interface FirestoreService {

    suspend fun getAll(): Remnants
    suspend fun get(email: String, remnantId: String) : Remnant?
    suspend fun insert(email: String, remnant: Remnant)
    suspend fun update(email: String, remnant: Remnant)
    suspend fun delete(email: String, remnantId: String)
    suspend fun updatePhotoUris(email: String, uri: Uri)
    suspend fun getAllFromCurrentUser(email: String): Remnants
}