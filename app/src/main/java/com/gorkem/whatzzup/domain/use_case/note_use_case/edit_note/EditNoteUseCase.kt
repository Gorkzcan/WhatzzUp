package com.gorkem.whatzzup.domain.use_case.note_use_case.edit_note


import com.gorkem.whatzzup.domain.model.Note
import com.gorkem.whatzzup.domain.repository.FirebaseRepository
import com.gorkem.whatzzup.util.Result
import java.text.SimpleDateFormat
import javax.inject.Inject

class EditNoteUseCase @Inject constructor(val repository: FirebaseRepository) {

    suspend operator fun invoke(note: Note): Result {
        return when {
            isDataValid(note.title!!, note.content!!) -> {
                repository.updateNote(note)
            }

            else -> {
                Result.Error("Fields cannot be empty")
            }
        }
    }


    fun getFormattedDate(note: Note): String {
        val date = note.timeAdded!!.toDate()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        return sdf.format(date)
    }


    private fun isDataValid(title: String, content: String) =
        title.trim().isNotEmpty() && content.trim().isNotEmpty()

}
