package com.studyflix.android.ui.teacher.assignments;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.usecase.teacher.CreateAssignmentUseCase;
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
public final class TeacherCreateAssignmentViewModel_Factory implements Factory<TeacherCreateAssignmentViewModel> {
  private final Provider<CreateAssignmentUseCase> createAssignmentUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public TeacherCreateAssignmentViewModel_Factory(
      Provider<CreateAssignmentUseCase> createAssignmentUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.createAssignmentUseCaseProvider = createAssignmentUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public TeacherCreateAssignmentViewModel get() {
    return newInstance(createAssignmentUseCaseProvider.get(), firebaseAuthProvider.get());
  }

  public static TeacherCreateAssignmentViewModel_Factory create(
      Provider<CreateAssignmentUseCase> createAssignmentUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new TeacherCreateAssignmentViewModel_Factory(createAssignmentUseCaseProvider, firebaseAuthProvider);
  }

  public static TeacherCreateAssignmentViewModel newInstance(
      CreateAssignmentUseCase createAssignmentUseCase, FirebaseAuth firebaseAuth) {
    return new TeacherCreateAssignmentViewModel(createAssignmentUseCase, firebaseAuth);
  }
}
