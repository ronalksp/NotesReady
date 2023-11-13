package com.apu.notesready.domain.uses_cases

import com.apu.notesready.domain.repository.Repository
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Long) = repository.delete(id)
}