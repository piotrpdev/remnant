package dev.piotrp.remnant.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.piotrp.remnant.data.RemnantModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RemnantDAO {
    @Query("SELECT * FROM remnantmodel")
    fun getAll(): Flow<List<RemnantModel>>

    @Query("SELECT * FROM remnantmodel WHERE id=:id")
    fun get(id: Int): Flow<RemnantModel>

    @Insert
    suspend fun insert(remnant: RemnantModel)

    @Query("UPDATE remnantmodel SET message=:message WHERE id = :id")
    suspend fun update(id: Int, message:String)

    @Delete
    suspend fun delete(remnant: RemnantModel)
}