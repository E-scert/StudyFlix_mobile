package com.studyflix.android.ui.student.assignments;

import com.studyflix.android.domain.repository.AssignmentRepository;
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
public final class AssignmentsViewModel_Factory implements Factory<AssignmentsViewModel> {
  private final Provider<AssignmentRepository> repositoryProvider;

  public AssignmentsViewModel_Factory(Provider<AssignmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AssignmentsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static AssignmentsViewModel_Factory create(
      Provider<AssignmentRepository> repositoryProvider) {
    return new AssignmentsViewModel_Factory(repositoryProvider);
  }

  public static AssignmentsViewModel newInstance(AssignmentRepository repository) {
    return new AssignmentsViewModel(repository);
  }
}
