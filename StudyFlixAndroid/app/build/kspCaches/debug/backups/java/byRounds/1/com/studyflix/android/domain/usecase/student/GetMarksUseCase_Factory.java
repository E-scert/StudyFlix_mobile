package com.studyflix.android.domain.usecase.student;

import com.studyflix.android.domain.repository.MarksRepository;
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
public final class GetMarksUseCase_Factory implements Factory<GetMarksUseCase> {
  private final Provider<MarksRepository> marksRepositoryProvider;

  public GetMarksUseCase_Factory(Provider<MarksRepository> marksRepositoryProvider) {
    this.marksRepositoryProvider = marksRepositoryProvider;
  }

  @Override
  public GetMarksUseCase get() {
    return newInstance(marksRepositoryProvider.get());
  }

  public static GetMarksUseCase_Factory create(Provider<MarksRepository> marksRepositoryProvider) {
    return new GetMarksUseCase_Factory(marksRepositoryProvider);
  }

  public static GetMarksUseCase newInstance(MarksRepository marksRepository) {
    return new GetMarksUseCase(marksRepository);
  }
}
