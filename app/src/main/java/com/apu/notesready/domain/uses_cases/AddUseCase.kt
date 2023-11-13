package com.apu.notesready.domain.uses_cases

import com.apu.notesready.data.local.model.Note
import com.apu.notesready.domain.repository.Repository
import javax.inject.Inject

class AddUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(note: Note) = repository.insert(note)
}