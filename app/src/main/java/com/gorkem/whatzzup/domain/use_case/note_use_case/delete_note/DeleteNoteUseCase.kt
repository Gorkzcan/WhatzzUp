package com.gorkem.whatzzup.domain.use_case.note_use_case.delete_note

import com.gorkem.whatzzup.domain.model.Note
import com.gorkem.whatzzup.domain.repository.FirebaseRepository
import com.gorkem.whatzzup.util.Result
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: FirebaseRepository) {

    suspend operator fun invoke(note: Note): Result {
        return repository.deleteNote(note)
    }
}