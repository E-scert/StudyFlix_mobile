package com.studyflix.android.ui.student.videos;

import com.studyflix.android.domain.usecase.student.GetVideosUseCase;
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
public final class VideosViewModel_Factory implements Factory<VideosViewModel> {
  private final Provider<GetVideosUseCase> getVideosUseCaseProvider;

  public VideosViewModel_Factory(Provider<GetVideosUseCase> getVideosUseCaseProvider) {
    this.getVideosUseCaseProvider = getVideosUseCaseProvider;
  }

  @Override
  public VideosViewModel get() {
    return newInstance(getVideosUseCaseProvider.get());
  }

  public static VideosViewModel_Factory create(
      Provider<GetVideosUseCase> getVideosUseCaseProvider) {
    return new VideosViewModel_Factory(getVideosUseCaseProvider);
  }

  public static VideosViewModel newInstance(GetVideosUseCase getVideosUseCase) {
    return new VideosViewModel(getVideosUseCase);
  }
}
