package com.labedaj.smartalarm.di

import android.content.Context
import androidx.room.Room
import com.labedaj.smartalarm.data.AlarmDao
import com.labedaj.smartalarm.data.AlarmDatabase
import com.labedaj.smartalarm.data.AlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AlarmDatabase {
        return Room.databaseBuilder(
            context,
            AlarmDatabase::class.java,
            AlarmDatabase.DB_DATABASE
        ).build()
    }


    @Provides
    @Singleton
    fun provideAlarmDao(db: AlarmDatabase) = db.alarmDao()

    @Provides
    @Singleton
    fun provideAlarmRepository(dao: AlarmDao) = AlarmRepository(dao)

}