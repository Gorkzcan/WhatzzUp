package com.gorkem.whatzzup.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gorkem.whatzzup.domain.repository.FirebaseRepository
import com.gorkem.whatzzup.domain.use_case.note_use_case.NoteUseCases
import com.gorkem.whatzzup.domain.use_case.note_use_case.delete_note.DeleteNoteUseCase
import com.gorkem.whatzzup.domain.use_case.note_use_case.edit_note.EditNoteUseCase
import com.gorkem.whatzzup.domain.use_case.note_use_case.load_notes_list.LoadNotesListUseCase
import com.gorkem.whatzzup.domain.use_case.note_use_case.post_note.PostNoteUseCase
import com.gorkem.whatzzup.domain.use_case.user_use_case.UserUseCases
import com.gorkem.whatzzup.domain.use_case.user_use_case.get_user_data.GetUserDataUseCase
import com.gorkem.whatzzup.domain.use_case.user_use_case.is_user_logged_in.IsUserLoggedInUseCase
import com.gorkem.whatzzup.domain.use_case.user_use_case.login_user.LoginUserUseCase
import com.gorkem.whatzzup.domain.use_case.user_use_case.logout_user.LogoutUserUseCase
import com.gorkem.whatzzup.domain.use_case.user_use_case.register_user.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseDB() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: FirebaseRepository): NoteUseCases {
        return NoteUseCases(
            postNoteUseCase = PostNoteUseCase(repository),
            editNoteUseCase = EditNoteUseCase(repository),
            loadNotesListUseCase = LoadNotesListUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(repository: FirebaseRepository): UserUseCases {
        return UserUseCases(
            registerUserUseCase = RegisterUserUseCase(repository),
            loginUserUseCase = LoginUserUseCase(repository),
            isUserLoggedInUseCase = IsUserLoggedInUseCase(repository),
            getUserDataUseCase = GetUserDataUseCase(repository),
            logoutUserUseCase = LogoutUserUseCase(repository)
        )
    }

}