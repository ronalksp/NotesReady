package com.apu.notesready.domain.uses_cases

import com.apu.notesready.data.local.model.Note
import com.apu.notesready.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: Repository
) {

     operator fun invoke(): Flow<List<Note>>{
        return repository.getAllNotes()
    }
}