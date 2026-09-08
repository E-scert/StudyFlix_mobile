package com.studyflix.android.ui.teacher.learners;

import com.studyflix.android.domain.repository.TeacherRepository;
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
public final class TeacherLearnerProfileViewModel_Factory implements Factory<TeacherLearnerProfileViewModel> {
  private final Provider<TeacherRepository> teacherRepositoryProvider;

  public TeacherLearnerProfileViewModel_Factory(
      Provider<TeacherRepository> teacherRepositoryProvider) {
    this.teacherRepositoryProvider = teacherRepositoryProvider;
  }

  @Override
  public TeacherLearnerProfileViewModel get() {
    return newInstance(teacherRepositoryProvider.get());
  }

  public static TeacherLearnerProfileViewModel_Factory create(
      Provider<TeacherRepository> teacherRepositoryProvider) {
    return new TeacherLearnerProfileViewModel_Factory(teacherRepositoryProvider);
  }

  public static TeacherLearnerProfileViewModel newInstance(TeacherRepository teacherRepository) {
    return new TeacherLearnerProfileViewModel(teacherRepository);
  }
}
