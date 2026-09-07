package com.studyflix.android.domain.usecase.student;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.repository.StudentRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class GetCurrentStudentUseCase_Factory implements Factory<GetCurrentStudentUseCase> {
  private final Provider<StudentRepository> repositoryProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public GetCurrentStudentUseCase_Factory(Provider<StudentRepository> repositoryProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.repositoryProvider = repositoryProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public GetCurrentStudentUseCase get() {
    return newInstance(repositoryProvider.get(), firebaseAuthProvider.get());
  }

  public static GetCurrentStudentUseCase_Factory create(
      Provider<StudentRepository> repositoryProvider, Provider<FirebaseAuth> firebaseAuthProvider) {
    return new GetCurrentStudentUseCase_Factory(repositoryProvider, firebaseAuthProvider);
  }

  public static GetCurrentStudentUseCase newInstance(StudentRepository repository,
      FirebaseAuth firebaseAuth) {
    return new GetCurrentStudentUseCase(repository, firebaseAuth);
  }
}
