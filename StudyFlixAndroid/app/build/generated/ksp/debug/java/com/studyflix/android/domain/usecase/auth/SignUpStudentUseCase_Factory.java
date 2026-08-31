package com.studyflix.android.domain.usecase.auth;

import com.studyflix.android.domain.repository.AuthRepository;
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
public final class SignUpStudentUseCase_Factory implements Factory<SignUpStudentUseCase> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public SignUpStudentUseCase_Factory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public SignUpStudentUseCase get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static SignUpStudentUseCase_Factory create(
      Provider<AuthRepository> authRepositoryProvider) {
    return new SignUpStudentUseCase_Factory(authRepositoryProvider);
  }

  public static SignUpStudentUseCase newInstance(AuthRepository authRepository) {
    return new SignUpStudentUseCase(authRepository);
  }
}
