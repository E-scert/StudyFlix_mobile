package com.studyflix.android.di;

import com.studyflix.android.data.local.StudyFlixDatabase;
import com.studyflix.android.data.local.dao.StudentDao;
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
public final class DatabaseModule_ProvideStudentDaoFactory implements Factory<StudentDao> {
  private final Provider<StudyFlixDatabase> dbProvider;

  public DatabaseModule_ProvideStudentDaoFactory(Provider<StudyFlixDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public StudentDao get() {
    return provideStudentDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideStudentDaoFactory create(
      Provider<StudyFlixDatabase> dbProvider) {
    return new DatabaseModule_ProvideStudentDaoFactory(dbProvider);
  }

  public static StudentDao provideStudentDao(StudyFlixDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStudentDao(db));
  }
}
