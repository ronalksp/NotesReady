package com.apu.notesready.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    noteId: Long,
    assistedFactory: DetailAssistedFactory,
    navigateUp: () -> Unit
) {

    val viewModel = viewModel(
        modelClass = DetailViewModel::class.java,
        factory = DetailViewModelFactory(
            noteId = noteId,
            assistedFactory = assistedFactory
        )
    )

    val state = viewModel.state

    DetailScreen(
        modifier = modifier,
        isUpdatingNote = state.isUpdatingNote,
        isFormNotBlank = state.isUpdatingNote,
        title = state.title,
        content = state.content,
        isBookMark = state.isBookmark,
        onBookmarkChange = viewModel::onBookMarkChange,
        onContentChange = viewModel::onContentChange,
        onTitleChange = viewModel::onTitleChange,
        onBtnClick = {
            if (state.isUpdatingNote) {
                viewModel.addOrUpdateNote()
                navigateUp()
            }
        },
        onNavigate = navigateUp
    )

}

@Composable
private fun DetailScreen(
    modifier: Modifier,
    isUpdatingNote: Boolean,
    title: String,
    content: String,
    isBookMark: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    isFormNotBlank: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onBtnClick: () -> Unit,
    onNavigate: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        TopSection(
            title = title,
            onTitleChange = onTitleChange,
            isBookMark = isBookMark,
            onBookmarkChange = onBookmarkChange,
            onNavigate = onNavigate
        )

        Spacer(modifier = Modifier.Companion.size(12.dp))
        AnimatedVisibility(visible = isFormNotBlank) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onBtnClick) {
                    val icon = if (isUpdatingNote) Icons.Default.Update else Icons.Outlined.Check
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                    )
                }

            }
        }

        Spacer(modifier = Modifier.Companion.size(12.dp))

        NotesTextField(
            modifier = Modifier.weight(1f),
            value = content,
            label = "Content",
            onValueChange = onContentChange
        )

    }

}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
    isBookMark: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    onNavigate: () -> Unit
) {

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onNavigate) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }

        val icon = if (isBookMark) Icons.Default.BookmarkRemove else Icons.Outlined.BookmarkAdd

        NotesTextField(
            modifier = Modifier.weight(1f),
            value = title,
            label = "Title",
            labelAlignment = TextAlign.Center,
            onValueChange = onTitleChange
        )

        IconButton(onClick = { onBookmarkChange(!isBookMark) }) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotesTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    labelAlignment: TextAlign? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedLabelColor = Color.Transparent,
            unfocusedLeadingIconColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = "Insert $label",
                textAlign = labelAlignment,
                modifier = modifier.fillMaxWidth(),
            )
        }
    )
}