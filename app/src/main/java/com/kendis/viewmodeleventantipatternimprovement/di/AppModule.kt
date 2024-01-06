package com.kendis.viewmodeleventantipatternimprovement.di

import com.kendis.viewmodeleventantipatternimprovement.repository.PaymentsRepository
import com.kendis.viewmodeleventantipatternimprovement.repository.PaymentsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalDataSource(): PaymentsRepository {
        return PaymentsRepositoryImpl()
    }
}