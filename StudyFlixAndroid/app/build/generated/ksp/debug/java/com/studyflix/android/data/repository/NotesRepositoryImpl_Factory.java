package com.studyflix.android.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.studyflix.android.domain.repository.StudentRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class NotesRepositoryImpl_Factory implements Factory<NotesRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<FirebaseAuth> authProvider;

  private final Provider<StudentRepository> studentRepositoryProvider;

  public NotesRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseAuth> authProvider, Provider<StudentRepository> studentRepositoryProvider) {
    this.firestoreProvider = firestoreProvider;
    this.authProvider = authProvider;
    this.studentRepositoryProvider = studentRepositoryProvider;
  }

  @Override
  public NotesRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), authProvider.get(), studentRepositoryProvider.get());
  }

  public static NotesRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseAuth> authProvider, Provider<StudentRepository> studentRepositoryProvider) {
    return new NotesRepositoryImpl_Factory(firestoreProvider, authProvider, studentRepositoryProvider);
  }

  public static NotesRepositoryImpl newInstance(FirebaseFirestore firestore, FirebaseAuth auth,
      StudentRepository studentRepository) {
    return new NotesRepositoryImpl(firestore, auth, studentRepository);
  }
}
