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
public final class PastPaperRepositoryImpl_Factory implements Factory<PastPaperRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<FirebaseAuth> authProvider;

  private final Provider<StudentRepository> studentRepositoryProvider;

  public PastPaperRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseAuth> authProvider, Provider<StudentRepository> studentRepositoryProvider) {
    this.firestoreProvider = firestoreProvider;
    this.authProvider = authProvider;
    this.studentRepositoryProvider = studentRepositoryProvider;
  }

  @Override
  public PastPaperRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), authProvider.get(), studentRepositoryProvider.get());
  }

  public static PastPaperRepositoryImpl_Factory create(
      Provider<FirebaseFirestore> firestoreProvider, Provider<FirebaseAuth> authProvider,
      Provider<StudentRepository> studentRepositoryProvider) {
    return new PastPaperRepositoryImpl_Factory(firestoreProvider, authProvider, studentRepositoryProvider);
  }

  public static PastPaperRepositoryImpl newInstance(FirebaseFirestore firestore, FirebaseAuth auth,
      StudentRepository studentRepository) {
    return new PastPaperRepositoryImpl(firestore, auth, studentRepository);
  }
}
