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
public final class IncrementPaperUsageUseCase_Factory implements Factory<IncrementPaperUsageUseCase> {
  private final Provider<StudentRepository> repositoryProvider;

  public IncrementPaperUsageUseCase_Factory(Provider<StudentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public IncrementPaperUsageUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static IncrementPaperUsageUseCase_Factory create(
      Provider<StudentRepository> repositoryProvider) {
    return new IncrementPaperUsageUseCase_Factory(repositoryProvider);
  }

  public static IncrementPaperUsageUseCase newInstance(StudentRepository repository) {
    return new IncrementPaperUsageUseCase(repository);
  }
}
