package com.studyflix.android.di;

import com.studyflix.android.data.local.StudyFlixDatabase;
import com.studyflix.android.data.local.dao.QuizDao;
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
public final class DatabaseModule_ProvideQuizDaoFactory implements Factory<QuizDao> {
  private final Provider<StudyFlixDatabase> dbProvider;

  public DatabaseModule_ProvideQuizDaoFactory(Provider<StudyFlixDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public QuizDao get() {
    return provideQuizDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideQuizDaoFactory create(
      Provider<StudyFlixDatabase> dbProvider) {
    return new DatabaseModule_ProvideQuizDaoFactory(dbProvider);
  }

  public static QuizDao provideQuizDao(StudyFlixDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideQuizDao(db));
  }
}
