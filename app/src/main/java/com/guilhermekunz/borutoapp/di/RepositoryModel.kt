package com.guilhermekunz.borutoapp.di

import android.content.Context
import com.guilhermekunz.borutoapp.data.repository.DataStoreOperationsImpl
import com.guilhermekunz.borutoapp.data.repository.Repository
import com.guilhermekunz.borutoapp.domain.repository.DataStoreOperations
import com.guilhermekunz.borutoapp.domain.use_cases.UseCases
import com.guilhermekunz.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.guilhermekunz.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModel {

    @Provides
    @Singleton
    fun provideDataStoreOperations(@ApplicationContext context: Context): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    @Provides
    @Singleton
    fun providesUseCases(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository)
        )
    }

}