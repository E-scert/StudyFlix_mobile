package com.studyflix.android.ui.student.notes;

import com.studyflix.android.domain.repository.NotesRepository;
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
public final class NotesViewModel_Factory implements Factory<NotesViewModel> {
  private final Provider<NotesRepository> repositoryProvider;

  public NotesViewModel_Factory(Provider<NotesRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public NotesViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static NotesViewModel_Factory create(Provider<NotesRepository> repositoryProvider) {
    return new NotesViewModel_Factory(repositoryProvider);
  }

  public static NotesViewModel newInstance(NotesRepository repository) {
    return new NotesViewModel(repository);
  }
}
