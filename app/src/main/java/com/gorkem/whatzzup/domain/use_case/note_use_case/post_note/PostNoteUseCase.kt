package com.gorkem.whatzzup.domain.use_case.note_use_case.post_note

import android.net.Uri
import com.gorkem.whatzzup.domain.repository.FirebaseRepository
import com.gorkem.whatzzup.util.Result
import javax.inject.Inject

class PostNoteUseCase  @Inject constructor(private val repository: FirebaseRepository) {

    suspend operator fun invoke(title: String, content: String, imageUri: Uri?, username:String ): Result {
        return when {
            isDataValid(title, content, imageUri) -> {
                repository.saveNote(title, content, imageUri!!, username)
            }

            imageUri == null -> {
                Result.Error("Please Select a Photo")
            }

            else -> {
                Result.Error("Fields Can't be Empty")
            }
        }
    }
    private fun isDataValid(title: String, content: String, imageUri: Uri?) = title.trim().isNotEmpty() && content.trim().isNotEmpty() && (imageUri != null)
}
