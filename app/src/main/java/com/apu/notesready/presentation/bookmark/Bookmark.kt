package com.apu.notesready.presentation.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apu.notesready.common.ScreenViewState
import com.apu.notesready.data.local.model.Note
import com.apu.notesready.presentation.home.HomeScreen
import com.apu.notesready.presentation.home.HomeState
import com.apu.notesready.presentation.home.NoteCard
import com.apu.notesready.presentation.home.notes

@Composable
fun BookmarkScreen(
    state: BookmarkState,
    modifier: Modifier = Modifier,
    onBookMarkChange: (note: Note) -> Unit,
    onDelete: (noteId: Long) -> Unit,
    onNoteClicked: (noteId: Long) -> Unit
) {

    when(state.notes){
        is ScreenViewState.Loading -> {
            CircularProgressIndicator()
        }
        is ScreenViewState.Success -> {
            val notes = state.notes.data
            LazyColumn(contentPadding = PaddingValues(4.dp), modifier = modifier){
                itemsIndexed(notes){index: Int, note: Note ->
                    NoteCard(
                        index = index ,
                        note = note,
                        onBookMarkChange =  onBookMarkChange,
                        onDelete = onDelete,
                        onNoteClicked = onNoteClicked
                    )
                }
            }
        }
        is ScreenViewState.Error -> {
            Text(
                text = state.notes.message ?: "Error",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
    
}

@Preview(showSystemUi = true)
@Composable
fun PrevBookMark() {
    BookmarkScreen(
        state = BookmarkState(notes = ScreenViewState.Success(notes)),
        onBookMarkChange = {},
        onDelete = {},
        onNoteClicked = {}
    )
}