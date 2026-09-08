package com.studyflix.android.domain.usecase.teacher;

import com.studyflix.android.domain.repository.TeacherRepository;
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
public final class GetTeacherAssignmentsUseCase_Factory implements Factory<GetTeacherAssignmentsUseCase> {
  private final Provider<TeacherRepository> repositoryProvider;

  public GetTeacherAssignmentsUseCase_Factory(Provider<TeacherRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTeacherAssignmentsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTeacherAssignmentsUseCase_Factory create(
      Provider<TeacherRepository> repositoryProvider) {
    return new GetTeacherAssignmentsUseCase_Factory(repositoryProvider);
  }

  public static GetTeacherAssignmentsUseCase newInstance(TeacherRepository repository) {
    return new GetTeacherAssignmentsUseCase(repository);
  }
}
