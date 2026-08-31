package com.studyflix.android.domain.usecase.student;

import com.studyflix.android.domain.repository.StudentRepository;
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
public final class GetStudentProfileUseCase_Factory implements Factory<GetStudentProfileUseCase> {
  private final Provider<StudentRepository> studentRepositoryProvider;

  public GetStudentProfileUseCase_Factory(Provider<StudentRepository> studentRepositoryProvider) {
    this.studentRepositoryProvider = studentRepositoryProvider;
  }

  @Override
  public GetStudentProfileUseCase get() {
    return newInstance(studentRepositoryProvider.get());
  }

  public static GetStudentProfileUseCase_Factory create(
      Provider<StudentRepository> studentRepositoryProvider) {
    return new GetStudentProfileUseCase_Factory(studentRepositoryProvider);
  }

  public static GetStudentProfileUseCase newInstance(StudentRepository studentRepository) {
    return new GetStudentProfileUseCase(studentRepository);
  }
}
