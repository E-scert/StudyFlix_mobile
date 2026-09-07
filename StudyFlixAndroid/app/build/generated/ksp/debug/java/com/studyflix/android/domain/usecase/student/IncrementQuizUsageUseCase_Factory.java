package com.studyflix.android.domain.usecase.student;

import com.studyflix.android.domain.repository.StudentRepository;
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
public final class IncrementQuizUsageUseCase_Factory implements Factory<IncrementQuizUsageUseCase> {
  private final Provider<StudentRepository> repositoryProvider;

  public IncrementQuizUsageUseCase_Factory(Provider<StudentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public IncrementQuizUsageUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static IncrementQuizUsageUseCase_Factory create(
      Provider<StudentRepository> repositoryProvider) {
    return new IncrementQuizUsageUseCase_Factory(repositoryProvider);
  }

  public static IncrementQuizUsageUseCase newInstance(StudentRepository repository) {
    return new IncrementQuizUsageUseCase(repository);
  }
}
