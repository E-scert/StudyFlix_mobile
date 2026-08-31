package com.studyflix.android.domain.usecase.student;

import com.studyflix.android.domain.repository.ContentRepository;
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
public final class GetVideosUseCase_Factory implements Factory<GetVideosUseCase> {
  private final Provider<ContentRepository> contentRepositoryProvider;

  public GetVideosUseCase_Factory(Provider<ContentRepository> contentRepositoryProvider) {
    this.contentRepositoryProvider = contentRepositoryProvider;
  }

  @Override
  public GetVideosUseCase get() {
    return newInstance(contentRepositoryProvider.get());
  }

  public static GetVideosUseCase_Factory create(
      Provider<ContentRepository> contentRepositoryProvider) {
    return new GetVideosUseCase_Factory(contentRepositoryProvider);
  }

  public static GetVideosUseCase newInstance(ContentRepository contentRepository) {
    return new GetVideosUseCase(contentRepository);
  }
}
