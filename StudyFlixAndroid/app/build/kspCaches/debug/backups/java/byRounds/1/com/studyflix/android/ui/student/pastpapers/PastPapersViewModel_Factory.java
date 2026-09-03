package com.studyflix.android.ui.student.pastpapers;

import com.studyflix.android.domain.repository.PastPaperRepository;
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
public final class PastPapersViewModel_Factory implements Factory<PastPapersViewModel> {
  private final Provider<PastPaperRepository> repositoryProvider;

  public PastPapersViewModel_Factory(Provider<PastPaperRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PastPapersViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static PastPapersViewModel_Factory create(
      Provider<PastPaperRepository> repositoryProvider) {
    return new PastPapersViewModel_Factory(repositoryProvider);
  }

  public static PastPapersViewModel newInstance(PastPaperRepository repository) {
    return new PastPapersViewModel(repository);
  }
}
