package com.studyflix.android.ui.teacher.learners;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.usecase.teacher.GetLearnerAssignmentsUseCase;
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
public final class TeacherLearnerAssignmentsViewModel_Factory implements Factory<TeacherLearnerAssignmentsViewModel> {
  private final Provider<GetLearnerAssignmentsUseCase> getLearnerAssignmentsUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public TeacherLearnerAssignmentsViewModel_Factory(
      Provider<GetLearnerAssignmentsUseCase> getLearnerAssignmentsUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.getLearnerAssignmentsUseCaseProvider = getLearnerAssignmentsUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public TeacherLearnerAssignmentsViewModel get() {
    return newInstance(getLearnerAssignmentsUseCaseProvider.get(), firebaseAuthProvider.get());
  }

  public static TeacherLearnerAssignmentsViewModel_Factory create(
      Provider<GetLearnerAssignmentsUseCase> getLearnerAssignmentsUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new TeacherLearnerAssignmentsViewModel_Factory(getLearnerAssignmentsUseCaseProvider, firebaseAuthProvider);
  }

  public static TeacherLearnerAssignmentsViewModel newInstance(
      GetLearnerAssignmentsUseCase getLearnerAssignmentsUseCase, FirebaseAuth firebaseAuth) {
    return new TeacherLearnerAssignmentsViewModel(getLearnerAssignmentsUseCase, firebaseAuth);
  }
}
