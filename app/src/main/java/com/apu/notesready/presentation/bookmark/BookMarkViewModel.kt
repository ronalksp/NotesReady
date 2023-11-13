package com.apu.notesready.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apu.notesready.common.ScreenViewState
import com.apu.notesready.data.local.model.Note
import com.apu.notesready.domain.uses_cases.DeleteUseCase
import com.apu.notesready.domain.uses_cases.FilteredBookmarkNotes
import com.apu.notesready.domain.uses_cases.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val filteredBookmarkNotes: FilteredBookmarkNotes,
    private val deleteNoteUseCaase: DeleteUseCase
): ViewModel() {

    private val _state: MutableStateFlow<BookmarkState> = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()

    private fun getBookMarkedNotes(){
        filteredBookmarkNotes().onEach {
            _state.value = BookmarkState(
                notes = ScreenViewState.Success(it)
            )
        }
            .catch {
                _state.value = BookmarkState(notes = ScreenViewState.Error(it.message))
            }
            .launchIn(viewModelScope)
    }

    fun obBookmarkChange(note: Note){
        viewModelScope.launch {
            updateNoteUseCase(note.copy(
                isBookMarked = !note.isBookMarked
            ))
        }
    }

    fun deleteNote(noteId: Long){
        viewModelScope.launch {
            deleteNoteUseCaase(noteId)
        }
    }

}

data class BookmarkState(
    val notes: ScreenViewState<List<Note>> = ScreenViewState.Loading,
)