package dev.piotrp.remnant.data.room

import dev.piotrp.remnant.data.RemnantModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject
constructor(private val remnantDAO: RemnantDAO) {
    fun getAll(): Flow<List<RemnantModel>>
            = remnantDAO.getAll()

    fun get(id: Int) = remnantDAO.get(id)

    suspend fun insert(remnant: RemnantModel)
            { remnantDAO.insert(remnant) }

    suspend fun update(remnant: RemnantModel)
            { remnantDAO.update(remnant.id,remnant.message) }

    suspend fun delete(remnant: RemnantModel)
            { remnantDAO.delete(remnant) }
}