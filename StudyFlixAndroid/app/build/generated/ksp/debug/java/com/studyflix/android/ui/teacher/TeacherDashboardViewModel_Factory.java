package com.studyflix.android.ui.teacher;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.usecase.teacher.GetTeacherProfileUseCase;
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
public final class TeacherDashboardViewModel_Factory implements Factory<TeacherDashboardViewModel> {
  private final Provider<GetTeacherProfileUseCase> getTeacherProfileUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public TeacherDashboardViewModel_Factory(
      Provider<GetTeacherProfileUseCase> getTeacherProfileUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.getTeacherProfileUseCaseProvider = getTeacherProfileUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public TeacherDashboardViewModel get() {
    return newInstance(getTeacherProfileUseCaseProvider.get(), firebaseAuthProvider.get());
  }

  public static TeacherDashboardViewModel_Factory create(
      Provider<GetTeacherProfileUseCase> getTeacherProfileUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new TeacherDashboardViewModel_Factory(getTeacherProfileUseCaseProvider, firebaseAuthProvider);
  }

  public static TeacherDashboardViewModel newInstance(
      GetTeacherProfileUseCase getTeacherProfileUseCase, FirebaseAuth firebaseAuth) {
    return new TeacherDashboardViewModel(getTeacherProfileUseCase, firebaseAuth);
  }
}
