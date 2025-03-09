package dev.piotrp.remnant.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.piotrp.remnant.data.DonationModel

@Database(entities = [DonationModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDonationDAO(): DonationDAO
}