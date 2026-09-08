package com.studyflix.android.ui.teacher.overview;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.usecase.teacher.GetTeacherOverviewUseCase;
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
public final class TeacherOverviewViewModel_Factory implements Factory<TeacherOverviewViewModel> {
  private final Provider<GetTeacherProfileUseCase> getTeacherProfileUseCaseProvider;

  private final Provider<GetTeacherOverviewUseCase> getTeacherOverviewUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public TeacherOverviewViewModel_Factory(
      Provider<GetTeacherProfileUseCase> getTeacherProfileUseCaseProvider,
      Provider<GetTeacherOverviewUseCase> getTeacherOverviewUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.getTeacherProfileUseCaseProvider = getTeacherProfileUseCaseProvider;
    this.getTeacherOverviewUseCaseProvider = getTeacherOverviewUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public TeacherOverviewViewModel get() {
    return newInstance(getTeacherProfileUseCaseProvider.get(), getTeacherOverviewUseCaseProvider.get(), firebaseAuthProvider.get());
  }

  public static TeacherOverviewViewModel_Factory create(
      Provider<GetTeacherProfileUseCase> getTeacherProfileUseCaseProvider,
      Provider<GetTeacherOverviewUseCase> getTeacherOverviewUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new TeacherOverviewViewModel_Factory(getTeacherProfileUseCaseProvider, getTeacherOverviewUseCaseProvider, firebaseAuthProvider);
  }

  public static TeacherOverviewViewModel newInstance(
      GetTeacherProfileUseCase getTeacherProfileUseCase,
      GetTeacherOverviewUseCase getTeacherOverviewUseCase, FirebaseAuth firebaseAuth) {
    return new TeacherOverviewViewModel(getTeacherProfileUseCase, getTeacherOverviewUseCase, firebaseAuth);
  }
}
