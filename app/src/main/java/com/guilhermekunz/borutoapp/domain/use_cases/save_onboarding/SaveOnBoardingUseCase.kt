package com.guilhermekunz.borutoapp.domain.use_cases.save_onboarding

import com.guilhermekunz.borutoapp.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(completed: Boolean){
        repository.saveOnBoardingState(completed = completed)
    }
}