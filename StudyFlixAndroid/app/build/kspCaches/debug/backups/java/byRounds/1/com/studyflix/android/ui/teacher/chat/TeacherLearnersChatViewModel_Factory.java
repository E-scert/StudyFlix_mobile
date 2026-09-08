package com.studyflix.android.ui.teacher.chat;

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
public final class TeacherLearnersChatViewModel_Factory implements Factory<TeacherLearnersChatViewModel> {
  private final Provider<GetTeacherLearnersUseCase> getTeacherLearnersUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public TeacherLearnersChatViewModel_Factory(
      Provider<GetTeacherLearnersUseCase> getTeacherLearnersUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.getTeacherLearnersUseCaseProvider = getTeacherLearnersUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public TeacherLearnersChatViewModel get() {
    return newInstance(getTeacherLearnersUseCaseProvider.get(), firebaseAuthProvider.get());
  }

  public static TeacherLearnersChatViewModel_Factory create(
      Provider<GetTeacherLearnersUseCase> getTeacherLearnersUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new TeacherLearnersChatViewModel_Factory(getTeacherLearnersUseCaseProvider, firebaseAuthProvider);
  }

  public static TeacherLearnersChatViewModel newInstance(
      GetTeacherLearnersUseCase getTeacherLearnersUseCase, FirebaseAuth firebaseAuth) {
    return new TeacherLearnersChatViewModel(getTeacherLearnersUseCase, firebaseAuth);
  }
}
