package dev.piotrp.remnant.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context):
            AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "remnant_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideRemnantDAO(appDatabase: AppDatabase):
            RemnantDAO = appDatabase.getRemnantDAO()

    @Provides
    fun provideRoomRepository(remnantDAO: RemnantDAO):
            RoomRepository = RoomRepository(remnantDAO)
}