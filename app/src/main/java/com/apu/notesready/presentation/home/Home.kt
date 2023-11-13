package com.apu.notesready.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apu.notesready.common.ScreenViewState
import com.apu.notesready.data.local.model.Note
import java.util.Date

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onBookMarkChange: (note: Note) -> Unit,
    onDelete: (noteId: Long) -> Unit,
    onNoteClicked: (noteId: Long) -> Unit
) {
    when (state.notes) {
        is ScreenViewState.Error -> {
            Text(
                text = state.notes.message ?: "Error",
                color = MaterialTheme.colorScheme.error
            )
        }

        ScreenViewState.Loading -> {
            CircularProgressIndicator()
        }

        is ScreenViewState.Success -> {
            val notes = state.notes.data
            HomeDetail(
                notes = notes,
                modifier = modifier,
                onBookMarkChange = onBookMarkChange,
                onDelete = onDelete,
                onNoteClicked = onNoteClicked
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeDetail(
    notes: List<Note>,
    modifier: androidx.compose.ui.Modifier,
    onBookMarkChange: (note: Note) -> Unit,
    onDelete: (noteId: Long) -> Unit,
    onNoteClicked: (noteId: Long) -> Unit
) {

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        content = {
            itemsIndexed(notes) { index, note ->
                NoteCard(
                    index = index,
                    note = note,
                    onBookMarkChange = onBookMarkChange,
                    onDelete = onDelete,
                    onNoteClicked = onNoteClicked
                )
            }
        },
        contentPadding = PaddingValues(4.dp),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    index: Int,
    note: Note,
    onBookMarkChange: (note: Note) -> Unit,
    onDelete: (noteId: Long) -> Unit,
    onNoteClicked: (noteId: Long) -> Unit
) {
    val isEvenIndex = index % 2 == 0
    val shape = when {
        isEvenIndex -> {
            RoundedCornerShape(
                topStart = 50f,
                bottomEnd = 50f
            )
        }

        else -> {
            RoundedCornerShape(
                topEnd = 50f,
                bottomStart = 50f
            )
        }
    }
    val icon = if (note.isBookMarked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = shape,
        onClick = { onNoteClicked(note.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.Companion.size(4.dp))
            Text(
                text = note.content,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { onDelete(note.id) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                    )
                }

                IconButton(onClick = { onBookMarkChange(note) }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Bookmark",
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevHome() {
    HomeScreen(
        state = HomeState(notes = ScreenViewState.Success(notes)),
        onBookMarkChange = {},
        onDelete = {},
        onNoteClicked = {}
    )
}

val placeHolderText =
    " is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"

val notes = listOf(
    Note(
        title = "Room Database",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "Jetpack Compose",
        content = "Testing",
        createdDate = Date(),
        isBookMarked = true,
    ),
    Note(
        title = "Room Database",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "Room Database",
        content = placeHolderText,
        createdDate = Date(),
        isBookMarked = true,
    ),
    Note(
        title = "Jetpack Compose",
        content = "Testing",
        createdDate = Date(),
        isBookMarked = true,
    ),
    Note(
        title = "Room Database",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
)