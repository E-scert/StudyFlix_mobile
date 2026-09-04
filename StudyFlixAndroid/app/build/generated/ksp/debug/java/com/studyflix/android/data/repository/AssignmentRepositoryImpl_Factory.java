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
public final class AssignmentRepositoryImpl_Factory implements Factory<AssignmentRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<StudentRepository> studentRepositoryProvider;

  private final Provider<FirebaseAuth> authProvider;

  public AssignmentRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<StudentRepository> studentRepositoryProvider, Provider<FirebaseAuth> authProvider) {
    this.firestoreProvider = firestoreProvider;
    this.studentRepositoryProvider = studentRepositoryProvider;
    this.authProvider = authProvider;
  }

  @Override
  public AssignmentRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), studentRepositoryProvider.get(), authProvider.get());
  }

  public static AssignmentRepositoryImpl_Factory create(
      Provider<FirebaseFirestore> firestoreProvider,
      Provider<StudentRepository> studentRepositoryProvider, Provider<FirebaseAuth> authProvider) {
    return new AssignmentRepositoryImpl_Factory(firestoreProvider, studentRepositoryProvider, authProvider);
  }

  public static AssignmentRepositoryImpl newInstance(FirebaseFirestore firestore,
      StudentRepository studentRepository, FirebaseAuth auth) {
    return new AssignmentRepositoryImpl(firestore, studentRepository, auth);
  }
}
