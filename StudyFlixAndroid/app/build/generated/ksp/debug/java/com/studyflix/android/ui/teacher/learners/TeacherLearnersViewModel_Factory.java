package com.studyflix.android.ui.teacher.learners;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.usecase.teacher.GetTeacherLearnersUseCase;
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
public final class TeacherLearnersViewModel_Factory implements Factory<TeacherLearnersViewModel> {
  private final Provider<GetTeacherLearnersUseCase> getTeacherLearnersUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public TeacherLearnersViewModel_Factory(
      Provider<GetTeacherLearnersUseCase> getTeacherLearnersUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.getTeacherLearnersUseCaseProvider = getTeacherLearnersUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public TeacherLearnersViewModel get() {
    return newInstance(getTeacherLearnersUseCaseProvider.get(), firebaseAuthProvider.get());
  }

  public static TeacherLearnersViewModel_Factory create(
      Provider<GetTeacherLearnersUseCase> getTeacherLearnersUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new TeacherLearnersViewModel_Factory(getTeacherLearnersUseCaseProvider, firebaseAuthProvider);
  }

  public static TeacherLearnersViewModel newInstance(
      GetTeacherLearnersUseCase getTeacherLearnersUseCase, FirebaseAuth firebaseAuth) {
    return new TeacherLearnersViewModel(getTeacherLearnersUseCase, firebaseAuth);
  }
}
