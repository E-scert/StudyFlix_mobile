package com.studyflix.android.ui.auth;

import com.studyflix.android.domain.usecase.auth.SignUpStudentUseCase;
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
public final class SignUpViewModel_Factory implements Factory<SignUpViewModel> {
  private final Provider<SignUpStudentUseCase> signUpStudentUseCaseProvider;

  public SignUpViewModel_Factory(Provider<SignUpStudentUseCase> signUpStudentUseCaseProvider) {
    this.signUpStudentUseCaseProvider = signUpStudentUseCaseProvider;
  }

  @Override
  public SignUpViewModel get() {
    return newInstance(signUpStudentUseCaseProvider.get());
  }

  public static SignUpViewModel_Factory create(
      Provider<SignUpStudentUseCase> signUpStudentUseCaseProvider) {
    return new SignUpViewModel_Factory(signUpStudentUseCaseProvider);
  }

  public static SignUpViewModel newInstance(SignUpStudentUseCase signUpStudentUseCase) {
    return new SignUpViewModel(signUpStudentUseCase);
  }
}
