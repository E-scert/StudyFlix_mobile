package com.studyflix.android.ui.teacher.assignments;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.usecase.teacher.GetTeacherAssignmentsUseCase;
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
public final class TeacherAssignmentsViewModel_Factory implements Factory<TeacherAssignmentsViewModel> {
  private final Provider<GetTeacherAssignmentsUseCase> getTeacherAssignmentsUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public TeacherAssignmentsViewModel_Factory(
      Provider<GetTeacherAssignmentsUseCase> getTeacherAssignmentsUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.getTeacherAssignmentsUseCaseProvider = getTeacherAssignmentsUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public TeacherAssignmentsViewModel get() {
    return newInstance(getTeacherAssignmentsUseCaseProvider.get(), firebaseAuthProvider.get());
  }

  public static TeacherAssignmentsViewModel_Factory create(
      Provider<GetTeacherAssignmentsUseCase> getTeacherAssignmentsUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new TeacherAssignmentsViewModel_Factory(getTeacherAssignmentsUseCaseProvider, firebaseAuthProvider);
  }

  public static TeacherAssignmentsViewModel newInstance(
      GetTeacherAssignmentsUseCase getTeacherAssignmentsUseCase, FirebaseAuth firebaseAuth) {
    return new TeacherAssignmentsViewModel(getTeacherAssignmentsUseCase, firebaseAuth);
  }
}
