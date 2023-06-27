package com.gorkem.whatzzup.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gorkem.whatzzup.di.data.FirebaseRepositoryImpl
import com.gorkem.whatzzup.domain.repository.FirebaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideFirebaseRepository(
        firebaseAuth: FirebaseAuth,
        db: FirebaseFirestore,
        storage: FirebaseStorage
    ): FirebaseRepository {
        return FirebaseRepositoryImpl(
            firebaseAuth, db, storage
        )
    }

}