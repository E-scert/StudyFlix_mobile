package com.studyflix.android.domain.usecase.teacher;

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
public final class GetTeacherProfileUseCase_Factory implements Factory<GetTeacherProfileUseCase> {
  private final Provider<TeacherRepository> teacherRepositoryProvider;

  public GetTeacherProfileUseCase_Factory(Provider<TeacherRepository> teacherRepositoryProvider) {
    this.teacherRepositoryProvider = teacherRepositoryProvider;
  }

  @Override
  public GetTeacherProfileUseCase get() {
    return newInstance(teacherRepositoryProvider.get());
  }

  public static GetTeacherProfileUseCase_Factory create(
      Provider<TeacherRepository> teacherRepositoryProvider) {
    return new GetTeacherProfileUseCase_Factory(teacherRepositoryProvider);
  }

  public static GetTeacherProfileUseCase newInstance(TeacherRepository teacherRepository) {
    return new GetTeacherProfileUseCase(teacherRepository);
  }
}
