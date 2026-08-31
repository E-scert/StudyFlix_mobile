package com.studyflix.android.ui.student.marks;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.usecase.student.GetMarksUseCase;
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
public final class MarksViewModel_Factory implements Factory<MarksViewModel> {
  private final Provider<GetMarksUseCase> getMarksUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public MarksViewModel_Factory(Provider<GetMarksUseCase> getMarksUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.getMarksUseCaseProvider = getMarksUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public MarksViewModel get() {
    return newInstance(getMarksUseCaseProvider.get(), firebaseAuthProvider.get());
  }

  public static MarksViewModel_Factory create(Provider<GetMarksUseCase> getMarksUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new MarksViewModel_Factory(getMarksUseCaseProvider, firebaseAuthProvider);
  }

  public static MarksViewModel newInstance(GetMarksUseCase getMarksUseCase,
      FirebaseAuth firebaseAuth) {
    return new MarksViewModel(getMarksUseCase, firebaseAuth);
  }
}
