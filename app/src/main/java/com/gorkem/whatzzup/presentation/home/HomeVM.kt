package com.gorkem.whatzzup.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorkem.whatzzup.domain.model.Note
import com.gorkem.whatzzup.domain.use_case.note_use_case.NoteUseCases
import com.gorkem.whatzzup.domain.use_case.user_use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.gorkem.whatzzup.util.Result
import kotlinx.coroutines.launch

@HiltViewModel
class HomeVM @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val userUseCases: UserUseCases
): ViewModel(){
    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading
    var result = MutableLiveData<Result>()

    fun resetResult() {
        result = MutableLiveData<Result>()
    }

    fun signOut() = userUseCases.logoutUserUseCase()

    fun loadNotes() = noteUseCases.loadNotesListUseCase()

    fun deleteNote(note:Note) : LiveData<Result>{
        viewModelScope.launch {
            result.value = noteUseCases.deleteNoteUseCase(note)
        }
        return result
    }

    fun startLoading(){
        _isLoading.postValue(true)
    }

    fun stopLoading(){
        _isLoading.postValue(false)
    }
}