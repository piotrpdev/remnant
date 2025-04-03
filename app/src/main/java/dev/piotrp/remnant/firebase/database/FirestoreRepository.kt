package dev.piotrp.remnant.firebase.database

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import dev.piotrp.remnant.data.rules.Constants.REMNANT_COLLECTION
import dev.piotrp.remnant.data.rules.Constants.USER_EMAIL
import dev.piotrp.remnant.firebase.services.AuthService
import dev.piotrp.remnant.firebase.services.Remnant
import dev.piotrp.remnant.firebase.services.Remnants
import dev.piotrp.remnant.firebase.services.FirestoreService
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.Date
import javax.inject.Inject


class FirestoreRepository
@Inject constructor(private val auth: AuthService,
                    private val firestore: FirebaseFirestore
) : FirestoreService {

    override suspend fun getAll(email: String): Remnants {
        return firestore.collection(REMNANT_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .dataObjects()
    }
    override suspend fun get(
        email: String,
        remnantId: String): Remnant? {
        return firestore.collection(REMNANT_COLLECTION)
                .document(remnantId).get().await().toObject()
    }

    override suspend fun insert(email: String, remnant: Remnant) {
        val remnantWithEmailAndImage =
            remnant.copy(
                email = email,
                imageUri = auth.customPhotoUri!!.toString()
            )

        firestore.collection(REMNANT_COLLECTION)
            .add(remnantWithEmailAndImage)
            .await()
    }

    override suspend fun update(email: String,
                                remnant: Remnant) {
        val remnantWithModifiedDate =
                    remnant.copy(dateModified = Date())

        firestore.collection(REMNANT_COLLECTION)
            .document(remnant._id)
            .set(remnantWithModifiedDate).await()
    }

    override suspend fun delete(
        email: String,
        remnantId: String) {
            firestore.collection(REMNANT_COLLECTION)
                .document(remnantId)
                .delete().await()
    }

    override suspend fun updatePhotoUris(email: String, uri: Uri) {

        firestore.collection(REMNANT_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Timber.i("FSR Updating ID ${document.id}")
                    firestore.collection(REMNANT_COLLECTION)
                        .document(document.id)
                        .update("imageUri", uri.toString())
                }
            }
            .addOnFailureListener { exception ->
                Timber.i("Error $exception")
            }
    }
}