package com.gorkem.whatzzup.presentation.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorkem.whatzzup.domain.model.Note
import com.gorkem.whatzzup.domain.use_case.note_use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.gorkem.whatzzup.util.Result
import kotlinx.coroutines.launch

@HiltViewModel
class EditNoteViewModel @Inject constructor(private val noteUseCases: NoteUseCases) :
    ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading
    var result = MutableLiveData<Result>()
    var message = String()

    fun resetResult() {
        result = MutableLiveData<Result>()
    }

    fun updateNote(note: Note): LiveData<Result> {
        viewModelScope.launch {
            _isLoading.postValue(true)
            result.value = noteUseCases.editNoteUseCase(note)
            _isLoading.postValue(false)
        }
        return result
    }

    fun getFormattedDate(note: Note): String = noteUseCases.editNoteUseCase.getFormattedDate(note)

}