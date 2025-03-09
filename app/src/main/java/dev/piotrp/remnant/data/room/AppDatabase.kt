package dev.piotrp.remnant.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.piotrp.remnant.data.RemnantModel

@Database(entities = [RemnantModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRemnantDAO(): RemnantDAO
}