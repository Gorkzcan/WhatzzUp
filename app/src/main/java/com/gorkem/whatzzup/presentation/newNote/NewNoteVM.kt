package com.gorkem.whatzzup.presentation.newNote

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorkem.whatzzup.domain.use_case.note_use_case.NoteUseCases
import com.gorkem.whatzzup.domain.use_case.user_use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.gorkem.whatzzup.util.Result
import kotlinx.coroutines.launch

@HiltViewModel
class NewNoteVM @Inject constructor(
    private val noteUseCases: NoteUseCases,
    userUseCases: UserUseCases
): ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading
    val userData = userUseCases.getUserDataUseCase()
    var result = MutableLiveData<Result>()

    // Prevent repeat of login btn snackbar message on config change
    fun resetResult() {
        result = MutableLiveData<Result>()
    }

    suspend fun saveNote(title: String, content: String, imageUri: Uri?, username: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            result.value =
                noteUseCases.postNoteUseCase(title, content, imageUri, username)
            _isLoading.postValue(false)
        }
    }

}