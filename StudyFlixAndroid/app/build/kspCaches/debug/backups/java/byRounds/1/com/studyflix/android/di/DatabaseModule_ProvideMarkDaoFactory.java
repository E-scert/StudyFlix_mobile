package com.studyflix.android.di;

import com.studyflix.android.data.local.StudyFlixDatabase;
import com.studyflix.android.data.local.dao.MarkDao;
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
public final class DatabaseModule_ProvideMarkDaoFactory implements Factory<MarkDao> {
  private final Provider<StudyFlixDatabase> dbProvider;

  public DatabaseModule_ProvideMarkDaoFactory(Provider<StudyFlixDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public MarkDao get() {
    return provideMarkDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideMarkDaoFactory create(
      Provider<StudyFlixDatabase> dbProvider) {
    return new DatabaseModule_ProvideMarkDaoFactory(dbProvider);
  }

  public static MarkDao provideMarkDao(StudyFlixDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMarkDao(db));
  }
}
