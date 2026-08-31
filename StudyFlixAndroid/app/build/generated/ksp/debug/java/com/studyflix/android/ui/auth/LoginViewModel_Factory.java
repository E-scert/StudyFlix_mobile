package com.studyflix.android.ui.auth;

import com.studyflix.android.domain.usecase.auth.SignInUseCase;
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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<SignInUseCase> signInUseCaseProvider;

  public LoginViewModel_Factory(Provider<SignInUseCase> signInUseCaseProvider) {
    this.signInUseCaseProvider = signInUseCaseProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(signInUseCaseProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<SignInUseCase> signInUseCaseProvider) {
    return new LoginViewModel_Factory(signInUseCaseProvider);
  }

  public static LoginViewModel newInstance(SignInUseCase signInUseCase) {
    return new LoginViewModel(signInUseCase);
  }
}
