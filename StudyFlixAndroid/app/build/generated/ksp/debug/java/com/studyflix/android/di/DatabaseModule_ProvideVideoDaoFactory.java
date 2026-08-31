package com.studyflix.android.di;

import com.studyflix.android.data.local.StudyFlixDatabase;
import com.studyflix.android.data.local.dao.VideoDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideVideoDaoFactory implements Factory<VideoDao> {
  private final Provider<StudyFlixDatabase> dbProvider;

  public DatabaseModule_ProvideVideoDaoFactory(Provider<StudyFlixDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public VideoDao get() {
    return provideVideoDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideVideoDaoFactory create(
      Provider<StudyFlixDatabase> dbProvider) {
    return new DatabaseModule_ProvideVideoDaoFactory(dbProvider);
  }

  public static VideoDao provideVideoDao(StudyFlixDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideVideoDao(db));
  }
}
