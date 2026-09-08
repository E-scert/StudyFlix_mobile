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
public final class GetTeacherLearnersUseCase_Factory implements Factory<GetTeacherLearnersUseCase> {
  private final Provider<TeacherRepository> repositoryProvider;

  public GetTeacherLearnersUseCase_Factory(Provider<TeacherRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTeacherLearnersUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTeacherLearnersUseCase_Factory create(
      Provider<TeacherRepository> repositoryProvider) {
    return new GetTeacherLearnersUseCase_Factory(repositoryProvider);
  }

  public static GetTeacherLearnersUseCase newInstance(TeacherRepository repository) {
    return new GetTeacherLearnersUseCase(repository);
  }
}
