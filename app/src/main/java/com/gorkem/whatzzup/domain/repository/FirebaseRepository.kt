package com.gorkem.whatzzup.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.gorkem.whatzzup.domain.model.Note
import com.gorkem.whatzzup.domain.model.User
import com.gorkem.whatzzup.util.Result
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    val userData: LiveData<User>

    suspend fun registerUser(email: String, password: String, username:String): Result

    suspend fun createAccount(email:String, password: String): AuthResult

    suspend fun saveUserToCollection(username:String): DocumentReference

    suspend fun saveNote(title: String, content: String, imageUri: Uri, username:String): Result

    suspend fun loginUser(email:String, password: String): Result

    fun loadUserData()

    fun getNotebookData(): Flow<Result>

    suspend fun updateNote(note: Note): Result

    suspend fun deleteNote(note:Note): Result

    fun signOut()

    fun getCurrentUser(): FirebaseUser?

    fun getCurrentUserId(): String?
}