package com.studyflix.android.ui.teacher.learners;

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

  public TeacherLearnerAssignmentsViewModel_Factory(
      Provider<GetLearnerAssignmentsUseCase> getLearnerAssignmentsUseCaseProvider) {
    this.getLearnerAssignmentsUseCaseProvider = getLearnerAssignmentsUseCaseProvider;
  }

  @Override
  public TeacherLearnerAssignmentsViewModel get() {
    return newInstance(getLearnerAssignmentsUseCaseProvider.get());
  }

  public static TeacherLearnerAssignmentsViewModel_Factory create(
      Provider<GetLearnerAssignmentsUseCase> getLearnerAssignmentsUseCaseProvider) {
    return new TeacherLearnerAssignmentsViewModel_Factory(getLearnerAssignmentsUseCaseProvider);
  }

  public static TeacherLearnerAssignmentsViewModel newInstance(
      GetLearnerAssignmentsUseCase getLearnerAssignmentsUseCase) {
    return new TeacherLearnerAssignmentsViewModel(getLearnerAssignmentsUseCase);
  }
}
