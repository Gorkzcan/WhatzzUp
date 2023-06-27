package com.gorkem.whatzzup.domain.use_case.note_use_case.load_notes_list

import com.gorkem.whatzzup.domain.repository.FirebaseRepository
import com.gorkem.whatzzup.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadNotesListUseCase @Inject constructor(private val repository: FirebaseRepository){

    operator fun invoke(): Flow<Result> {
        return repository.getNotebookData()
    }
}