package com.studyflix.android.ui.student.home;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.usecase.auth.SignOutUseCase;
import com.studyflix.android.domain.usecase.student.GetStudentProfileUseCase;
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
public final class StudentHomeViewModel_Factory implements Factory<StudentHomeViewModel> {
  private final Provider<GetStudentProfileUseCase> getStudentProfileUseCaseProvider;

  private final Provider<SignOutUseCase> signOutUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public StudentHomeViewModel_Factory(
      Provider<GetStudentProfileUseCase> getStudentProfileUseCaseProvider,
      Provider<SignOutUseCase> signOutUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.getStudentProfileUseCaseProvider = getStudentProfileUseCaseProvider;
    this.signOutUseCaseProvider = signOutUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public StudentHomeViewModel get() {
    return newInstance(getStudentProfileUseCaseProvider.get(), signOutUseCaseProvider.get(), firebaseAuthProvider.get());
  }

  public static StudentHomeViewModel_Factory create(
      Provider<GetStudentProfileUseCase> getStudentProfileUseCaseProvider,
      Provider<SignOutUseCase> signOutUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new StudentHomeViewModel_Factory(getStudentProfileUseCaseProvider, signOutUseCaseProvider, firebaseAuthProvider);
  }

  public static StudentHomeViewModel newInstance(GetStudentProfileUseCase getStudentProfileUseCase,
      SignOutUseCase signOutUseCase, FirebaseAuth firebaseAuth) {
    return new StudentHomeViewModel(getStudentProfileUseCase, signOutUseCase, firebaseAuth);
  }
}
