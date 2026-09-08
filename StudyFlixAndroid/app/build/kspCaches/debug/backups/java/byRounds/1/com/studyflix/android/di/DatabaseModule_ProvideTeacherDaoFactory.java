package com.studyflix.android.di;

import com.studyflix.android.data.local.StudyFlixDatabase;
import com.studyflix.android.data.local.dao.TeacherDao;
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
public final class DatabaseModule_ProvideTeacherDaoFactory implements Factory<TeacherDao> {
  private final Provider<StudyFlixDatabase> dbProvider;

  public DatabaseModule_ProvideTeacherDaoFactory(Provider<StudyFlixDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TeacherDao get() {
    return provideTeacherDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTeacherDaoFactory create(
      Provider<StudyFlixDatabase> dbProvider) {
    return new DatabaseModule_ProvideTeacherDaoFactory(dbProvider);
  }

  public static TeacherDao provideTeacherDao(StudyFlixDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTeacherDao(db));
  }
}
